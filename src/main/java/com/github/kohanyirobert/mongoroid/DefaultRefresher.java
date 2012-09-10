package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;
import com.github.kohanyirobert.ebson.BsonDocuments;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.Closeables;
import com.google.common.net.HostAndPort;
import com.google.common.util.concurrent.Atomics;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.common.util.concurrent.Uninterruptibles;

import java.lang.Thread.UncaughtExceptionHandler;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

// @do-not-check-next-line Class(FanOutComplexity|DataAbstractionCoupling)
final class DefaultRefresher implements MongoRefresher {

  private final DefaultConnection connection;

  private final Refresher refresher = new Refresher();
  private final UncaughtExceptionHandler handler = new UncaughtExceptionHandler() {

    @Override
    public void uncaughtException(Thread thread, Throwable exception) {
      System.out.format("thread: %s, exception: %s\n", thread, exception);
    }
  };
  private final ThreadFactory factory = new ThreadFactoryBuilder()
      .setNameFormat("MongoRefresher-%s")
      .setUncaughtExceptionHandler(handler)
      .setDaemon(true)
      .build();
  private final ExecutorService executor = Executors.newSingleThreadExecutor(factory);

  private final List<InetSocketAddress> addresses = Lists.newArrayList();
  private final List<Socket> sockets = Lists.newArrayList();
  private final ByteBuffer buffer;

  private final AtomicBoolean closed = new AtomicBoolean();
  private final AtomicReference<InetSocketAddress> sayAddress = Atomics.newReference();
  private final AtomicReference<InetSocketAddress> tellAddress = Atomics.newReference();
  private final CountDownLatch refreshedOrTimedOutAtLeastOnce = new CountDownLatch(1);
  private final AtomicReference<MongoException> refreshedException = Atomics.newReference();
  private final AtomicBoolean sayAddressFound = new AtomicBoolean();
  private final AtomicBoolean tellAddressFound = new AtomicBoolean();

  DefaultRefresher(DefaultConnection connection) {
    this.connection = connection;
    addresses.addAll(connection.seed().addresses());
    Collections.shuffle(addresses);
    buffer = MongoByteBuffers.get(connection.config());
    executor.submit(refresher);
    // must come *after* submitting a runnable or the executor
    // stops right away - at least it seemed to at one point
    MoreExecutors.addDelayedShutdownHook(executor, 0, TimeUnit.MILLISECONDS);
  }

  @Override
  public MongoConnection connection() {
    return connection;
  }

  @Override
  public InetSocketAddress address(MongoSendType type) throws MongoException {
    Uninterruptibles.awaitUninterruptibly(refreshedOrTimedOutAtLeastOnce);

    if (refreshedException.get() != null)
      throw refreshedException.get();

    switch (type) {
      case SAY:
        return sayAddress.get();
      case TELL:
        return tellAddress.get();
      default:
        throw new IllegalStateException();
    }
  }

  @Override
  public void close() throws MongoException {
    if (closed.compareAndSet(false, true)) {
      executor.shutdownNow();
      for (Socket socket : sockets)
        Closeables.closeQuietly(socket);
    } else
      throw new MongoClosedException();
  }

  private void refreshSayAndTellAddresses() throws MongoException {
    removeClosedSockets();
    assert !addresses.isEmpty();
    updateAllKnownAddresses();
    sayAddressFound.set(false);
    tellAddressFound.set(false);
    determineSayAndTellAddresses();
    if (!sayAddressFound.get() || !tellAddressFound.get())
      throw new MongoRefreshException();
    // at this point sayAddress and tellAddress was found at least once
    // so any thread waiting inside address(MongoSendType) can go on
    refreshedOrTimedOutAtLeastOnce.countDown();
  }

  private void removeClosedSockets() {
    Iterables.removeIf(sockets, new Predicate<Socket>() {

      @Override
      public boolean apply(Socket input) {
        return input.isClosed();
      }
    });
  }

  // queries a socket for visible nodes and upon success it replaces the
  // current addresses with those found (including the seed list)
  private void updateAllKnownAddresses() throws MongoException {
    Set<InetSocketAddress> allKnownAddress = Sets.newHashSet();
    for (InetSocketAddress address : addresses) {
      Socket socket = null;
      try {
        socket = getConnectedSocket(address);
        allKnownAddress.addAll(getAllKnownAddressThrough(socket));
        break;
      } catch (MongoIOException ex) {
        Closeables.closeQuietly(socket);
        continue;
      }
    }
    // if it's empty then the refresher couldn't find anything (yet)
    // so it has to try again, from square one, without clearning addresses
    if (!allKnownAddress.isEmpty()) {
      addresses.clear();
      addresses.addAll(allKnownAddress);
      Collections.shuffle(addresses);
    }
  }

  // queries the socket for visible addresses (addresses in
  // the seed list are always considered to be *visible*)
  private Set<InetSocketAddress> getAllKnownAddressThrough(Socket socket) throws MongoException {
    Set<InetSocketAddress> allKnownAddress = Sets.newHashSet(connection().seed().addresses());
    for (InetSocketAddress visibleAddress : getKnownAddressesThrough(socket))
      if (!Iterables.contains(allKnownAddress, visibleAddress))
        allKnownAddress.add(visibleAddress);
    return allKnownAddress;
  }

  // sends a request through the socket for the list of visible addresses
  private Iterable<InetSocketAddress> getKnownAddressesThrough(Socket socket) throws MongoException {
    List<InetSocketAddress> knownAddresses = Lists.newArrayList();
    BsonDocument isMaster = connection.command(socket, buffer, "admin", "isMaster");
    BsonDocument hosts = BsonDocuments.of();
    if (isMaster.containsKey("hosts"))
      hosts = isMaster.get("hosts", BsonDocument.class);
    for (String index : hosts.keySet()) {
      HostAndPort hostAndPort = HostAndPort.fromString(hosts.get(index, String.class));
      knownAddresses.add(new InetSocketAddress(hostAndPort.getHostText(), hostAndPort.getPort()));
    }
    return knownAddresses;
  }

  // @do-not-check-next-line CyclomaticComplexity
  private void determineSayAndTellAddresses() throws MongoException {
    for (InetSocketAddress address : addresses) {
      Socket socket = null;
      try {
        socket = getConnectedSocket(address);
        determinReadAndWriteAddressesThrough(socket, address);
        if (sayAddressFound.get() && tellAddressFound.get())
          break;
      } catch (MongoIOException ex) {
        Closeables.closeQuietly(socket);
        continue;
      }
    }
  }

  // @do-not-check-next-line CyclomaticComplexity
  private void determinReadAndWriteAddressesThrough(
      Socket socket,
      InetSocketAddress address) throws MongoException {
    BsonDocument isMaster = connection.command(socket, buffer, "admin", "isMaster");
    boolean primary = isMaster.get("ismaster", Boolean.class).booleanValue();
    boolean secondary = false;
    if (isMaster.containsKey("secondary"))
      secondary = isMaster.get("secondary", Boolean.class).booleanValue();
    BsonDocument tag = BsonDocuments.of();
    if (isMaster.containsKey("tags"))
      tag = isMaster.get("tags", BsonDocument.class);

    boolean readByTags = determineReadByTags(tag);
    boolean readBySecondary = determineReadBySecondary(secondary);
    boolean readByPrimary = determinaReadByPrimary(primary);

    if (readByTags || readBySecondary || readByPrimary) {
      sayAddress.set(address);
      sayAddressFound.set(true);
    }

    if (primary) {
      tellAddress.set(address);
      tellAddressFound.set(true);
    }
  }

  // @do-not-check-next-line CyclomaticComplexity
  private boolean determineReadByTags(BsonDocument actualTag) {
    boolean readByTags = false;

    for (BsonDocument preferredTag : connection.readPreference().tags()) {
      if (actualTag.equals(preferredTag))
        readByTags = true;

      for (String actualKey : actualTag.keySet())
        if (preferredTag.containsKey(actualKey)
            && Objects.equal(actualTag.get(actualKey), preferredTag.get(actualKey)))
          readByTags = true;

      if (readByTags)
        break;
    }

    return readByTags;
  }

  private boolean determineReadBySecondary(boolean secondary) {
    return connection().readPreference().secondary() && secondary;
  }

  private boolean determinaReadByPrimary(boolean primary) {
    return connection().readPreference().primary() && primary;
  }

  // finds an already connected socket or returns a new one
  private Socket getConnectedSocket(final InetSocketAddress address) throws MongoException {
    Socket socket = Iterables.find(sockets, new Predicate<Socket>() {

      @Override
      public boolean apply(Socket input) {
        return !input.isClosed()
            && input.isConnected()
            && input.getRemoteSocketAddress().equals(address);
      }
    }, null);

    if (socket == null) {
      socket = MongoSockets.get(connection().config());
      MongoSockets.connect(socket, address, connection().config());
    }

    return socket;
  }

  private final class Refresher implements Runnable {

    public Refresher() {}

    // @do-not-check-next-line CyclomaticComplexity
    @SuppressWarnings("synthetic-access")
    @Override
    public void run() {
      long refreshInterval = connection().config().refreshInterval(TimeUnit.MILLISECONDS);
      long refreshTimeout = connection().config().refreshTimeout(TimeUnit.MILLISECONDS);
      Stopwatch refreshTimer = new Stopwatch().start();
      for (;; Uninterruptibles.sleepUninterruptibly(refreshInterval, TimeUnit.MILLISECONDS))
        try {
          if (refreshTimer.elapsedMillis() > refreshTimeout + refreshInterval) {
            refreshedException.compareAndSet(null, new MongoRefreshTimeoutException());
            refreshedOrTimedOutAtLeastOnce.countDown();
            break;
          }
          refreshSayAndTellAddresses();
          refreshTimer.reset().start();
        } catch (MongoRefreshException ex) {
          // try again after waiting for the configured refresh interval
          continue;
        } catch (MongoException ex) {
          // something (other than refreshing) went wrong
          refreshedException.compareAndSet(null, ex);
          break;
        }
    }
  }
}
