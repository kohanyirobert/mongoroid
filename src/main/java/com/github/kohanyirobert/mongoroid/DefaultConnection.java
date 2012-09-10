// @do-not-check-next-line FileLength
package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;
import com.github.kohanyirobert.ebson.BsonDocuments;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.Closeables;
import com.google.common.primitives.Ints;
import com.google.common.util.concurrent.Uninterruptibles;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// @do-not-check-next-line Class(DataAbstractionCoupling|FanOutComplexity)
final class DefaultConnection implements MongoConnection {

  private final MongoConfig config;
  private final MongoSeed seed;
  private final MongoReadPreference readPreference;
  private final MongoWritePreference writePreference;
  private final MongoRefresher refresher;

  private final List<Lock> locks;
  private final List<Set<String>> authenticatedDatabases;
  private final List<Socket> sockets;
  private final List<ByteBuffer> buffers;
  private final Map<Long, Integer> threads;
  private final Map<String, Entry<String, String>> logins;
  private final ConcurrentMap<String, MongoDatabase> databases;
  private final BsonDocument getLastError;
  private final AtomicBoolean closed = new AtomicBoolean();

  // @do-not-check-next-line MethodLength
  @SuppressWarnings("unchecked")
  DefaultConnection(
      MongoConfig config,
      MongoSeed seed,
      MongoReadPreference readPreference,
      MongoWritePreference writePreference) {
    this.config = config;
    this.seed = seed;
    this.readPreference = readPreference;
    this.writePreference = writePreference;
    refresher = new DefaultRefresher(this);

    locks = Arrays.asList(new Lock[config.poolSize()]);
    for (int i = 0; i < locks.size(); i++)
      locks.set(i, new ReentrantLock(true));

    authenticatedDatabases = Arrays.<Set<String>>asList(new Set[config.poolSize()]);
    for (int i = 0; i < authenticatedDatabases.size(); i++)
      authenticatedDatabases.set(i, Sets.<String>newHashSet());

    sockets = Arrays.asList(new Socket[config.poolSize()]);
    buffers = Arrays.asList(new ByteBuffer[config.poolSize()]);
    threads = Maps.newHashMap();
    logins = Maps.newHashMap();
    databases = Maps.newConcurrentMap();
    getLastError = BsonDocuments.builder()
        .put("getLastError", Integer.valueOf(1))
        .put("w", Ints.tryParse(writePreference().w()) != null
            ? Integer.valueOf(writePreference().w())
            : writePreference().w())
        .put("wtimeout", Integer.valueOf(writePreference.wTimeout(TimeUnit.MILLISECONDS)))
        .put("fsync", Boolean.valueOf(writePreference.fsync()))
        .put("j", Boolean.valueOf(writePreference.j()))
        .put("journal", Boolean.valueOf(writePreference.journal()))
        .build();
  }

  @Override
  public MongoConfig config() {
    return config;
  }

  @Override
  public MongoSeed seed() {
    return seed;
  }

  @Override
  public MongoReadPreference readPreference() {
    return readPreference;
  }

  @Override
  public MongoWritePreference writePreference() {
    return writePreference;
  }

  @Override
  public List<MongoDatabase> databases() throws MongoException {
    checkClosed();
    BsonDocument result = command(pick(MongoSendType.SAY), "admin", "listDatabases", true);
    BsonDocument list = result.get("databases", BsonDocument.class);
    for (String index : list.keySet())
      database(list.get(index, BsonDocument.class).get("name", String.class));
    return ImmutableList.copyOf(databases.values());
  }

  @Override
  public MongoDatabase database(String name) throws MongoException {
    Preconditions.checkNotNull(name);
    checkClosed();
    databases.putIfAbsent(name, new DefaultDatabase(name, this));
    return databases.get(name);
  }

  @Override
  public void close() throws MongoException {
    if (closed.compareAndSet(false, true))
      for (int i = 0; i < config.poolSize(); i++) {
        Lock lock = locks.get(i);
        lock.lock();
        Socket socket = sockets.get(i);
        Closeables.closeQuietly(socket);
      }
    else
      throw new MongoClosedException();
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(MongoConnection.class)
        .add("config", config())
        .add("seed", seed())
        .add("readPreference", readPreference())
        .add("writePreference", writePreference())
        .toString();
  }

  // most of the methods indirectly calls sockety-send method or the
  // pickety-send method which calls the pick method, so checking for a
  // closed state is only necessary in these two methods
  void checkClosed() throws MongoException {
    if (closed.get())
      throw new MongoClosedException();
  }

  void login(String database, String user, String password) throws MongoException {
    String passwordDigest = MongoDigests.newPasswordDigest(user, password);
    BsonDocument result = command(pick(MongoSendType.SAY), database, "getnonce", false);
    String nonce = result.get("nonce", String.class);
    result = command(
        pick(MongoSendType.SAY),
        database,
        BsonDocuments.of(
            "authenticate", Integer.valueOf(1),
            "user", user,
            "nonce", nonce,
            "key", MongoDigests.newDigest(nonce, user, passwordDigest)),
        false);
    logins.put(database, Maps.immutableEntry(user, password));
  }

  void logout(String database) throws MongoException {
    command(pick(MongoSendType.SAY), database, "logout", false);
    logins.remove(database);
  }

  int pick(MongoSendType type) throws MongoException {
    checkClosed();

    long thread = Thread.currentThread().getId();
    int pick = -1;
    InetSocketAddress address = refresher.address(type);

    if (threads.containsKey(Long.valueOf(thread))) {
      pick = threads.get(Long.valueOf(thread)).intValue();
      locks.get(pick).lock();
    } else {
      pick = lock(address);
      threads.put(Long.valueOf(thread), Integer.valueOf(pick));
    }

    connectIfNecessary(pick, address);
    return pick;
  }

  // @do-not-check-next-line CyclomaticComplexity
  void ensureLogin(int pick) throws MongoException {
    for (String database : authenticatedDatabases.get(pick))
      if (!logins.containsKey(database)) {
        command(pick, database, "logout", false);
        authenticatedDatabases.get(pick).remove(database);
      }

    for (String database : logins.keySet())
      if (!authenticatedDatabases.get(pick).contains(database)) {
        Entry<String, String> credential = logins.get(database);
        login(database, credential.getKey(), credential.getValue());
        authenticatedDatabases.get(pick).add(database);
      }
  }

  // @do-not-check-next-line CyclomaticComplexity|MethodLength|ExecutableStatementCount
  int lock(InetSocketAddress address) {
    int pick = -1;

    // try to find a socket which is not yet connected
    // or already connected to the address
    // @do-not-check-next-line Indentation
    OUTER : {
      for (Integer choice : randomPickSequence()) {

        Socket socket = sockets.get(choice.intValue());
        if (socket == null)
          continue;

        if (!socket.isConnected()
            || socket.getRemoteSocketAddress().equals(address)) {
          Lock lock = locks.get(choice.intValue());
          if (lock.tryLock()) {
            pick = choice.intValue();
            break OUTER;
          }
        }
      }

      // otherwise just to try to lock a socket not currently in use
      if (pick == -1)
        for (Integer choice : randomPickSequence()) {
          Lock lock = locks.get(choice.intValue());
          if (lock.tryLock()) {
            pick = choice.intValue();
            break OUTER;
          }
        }

      // in the worst scenario just wait for the first socket to become
      // available
      if (pick == -1) {
        Lock lock = locks.get(0);
        lock.lock();
        pick = 0;
        break OUTER;
      }
    }

    return pick;
  }

  List<Integer> randomPickSequence() {
    // return a random sequence denoting the individual sockets (their indexes)
    List<Integer> choices = Lists.newArrayList();
    for (int i = 0; i < config().poolSize(); i++)
      choices.add(Integer.valueOf(i));
    Collections.shuffle(choices);
    return Collections.unmodifiableList(choices);
  }

  // @do-not-check-next-line CyclomaticComplexity
  void connectIfNecessary(int pick, InetSocketAddress address) throws MongoException {
    Socket socket = sockets.get(pick);
    ByteBuffer buffer = buffers.get(pick);

    if (socket == null) {
      socket = MongoSockets.get(config());
      sockets.set(pick, socket);
    }

    if (buffer == null) {
      buffer = MongoByteBuffers.get(config());
      buffers.set(pick, buffer);
    }

    try {
      if (!address.equals(socket.getRemoteSocketAddress())) {
        Closeables.closeQuietly(socket);
        socket = MongoSockets.get(config());
        sockets.set(pick, socket);
      }

      if (!socket.isConnected())
        MongoSockets.connect(socket, address, config());
    } catch (MongoException ex) {
      System.out.println("MI A ?" + ex);
      throw ex;
    }
  }

  void destructNoMatterWhat(int pick) {
    Socket socket = sockets.get(pick);
    Closeables.closeQuietly(socket);
    sockets.set(pick, null);
  }

  BsonDocument command(
      String database,
      String command,
      boolean authenticated) throws MongoException {
    return command(pick(MongoSendType.SAY), database, command, authenticated);
  }

  BsonDocument command(
      String database,
      BsonDocument command,
      boolean authenticated) throws MongoException {
    return command(pick(MongoSendType.SAY), database, command, authenticated);
  }

  void tell(
      MongoMessageRequest request,
      boolean authenticated) throws MongoException {
    MongoSendType sendType = MongoSendType.TELL;
    assert send(pick(sendType), request, sendType, authenticated) == null;
  }

  MongoMessageReply say(
      MongoMessageRequest request,
      boolean authenticated) throws MongoException {
    MongoSendType sendType = MongoSendType.SAY;
    return send(pick(sendType), request, sendType, authenticated);
  }

  BsonDocument command(
      int pick,
      String database,
      String command,
      boolean authenticated) throws MongoException {
    return command(
        pick,
        database,
        BsonDocuments.of(command, Integer.valueOf(1)),
        authenticated);
  }

  BsonDocument command(
      int pick,
      String database,
      BsonDocument command,
      boolean authenticated) throws MongoException {
    MongoMessageQuery query = new DefaultMessageQuery();
    query.numberToReturn(1);
    query.fullCollectionName(database + "." + "$cmd");
    query.query(command);
    return say(pick, query, authenticated).documents().get(0);
  }

  void tell(
      int pick,
      MongoMessageRequest request,
      boolean authenticated) throws MongoException {
    assert send(pick, request, MongoSendType.TELL, authenticated) == null;
  }

  MongoMessageReply say(
      int pick,
      MongoMessageRequest request,
      boolean authenticated) throws MongoException {
    return send(pick, request, MongoSendType.SAY, authenticated);
  }

  MongoMessageReply send(
      int pick,
      MongoMessageRequest request,
      MongoSendType sendType,
      boolean authenticated) throws MongoException {
    return send(pick, request, sendType, authenticated, false);
  }

  // this method is seriously fucked
  // @do-not-check-next-line .
  MongoMessageReply send(
      int pick,
      MongoMessageRequest request,
      MongoSendType sendType,
      boolean authenticated,
      boolean retrying) throws MongoException {
    MongoMessageReply reply = null;
    Socket socket = null;
    ByteBuffer buffer = null;
    try {
      if (authenticated)
        ensureLogin(pick);
      socket = sockets.get(pick);
      buffer = buffers.get(pick);
      reply = send(socket, buffer, request, sendType);
    } catch (MongoIOException ex) {
      destructNoMatterWhat(pick);
      if (!retrying) {
        long retryInterval = config().retryInterval(TimeUnit.MILLISECONDS);
        long retryTimeout = config.retryTimeout(TimeUnit.MILLISECONDS);
        Stopwatch retryTimer = new Stopwatch().start();
        for (;; Uninterruptibles.sleepUninterruptibly(retryInterval, TimeUnit.MILLISECONDS)) {
          int retryPick = -1;
          try {
            if (retryTimer.elapsedMillis() > retryInterval + retryTimeout)
              throw new MongoRetryTimeoutException();
            retryPick = pick(sendType);
            // "return" must be used, do not assign to "reply"
            MongoMessageReply retryReply = send(retryPick, request, sendType, true);
            retryTimer.reset().start();
            return retryReply;
          } catch (MongoIOException innerEx) {
            if (retryPick != -1)
              destructNoMatterWhat(retryPick);
            System.out.println(socket + "" + innerEx);
            continue;
          }
        }
      }
    } finally {
      locks.get(pick).unlock();
    }
    return reply;
  }

  BsonDocument command(
      Socket socket,
      ByteBuffer buffer,
      String database,
      String command) throws MongoException {
    return command(
        socket,
        buffer,
        database,
        BsonDocuments.of(command, Integer.valueOf(1)));
  }

  BsonDocument command(
      Socket socket,
      ByteBuffer buffer,
      String database,
      BsonDocument command) throws MongoException {
    MongoMessageQuery query = new DefaultMessageQuery();
    query.numberToReturn(1);
    query.fullCollectionName(database + "." + "$cmd");
    query.query(command);
    return say(socket, buffer, query).documents().get(0);
  }

  void tell(
      Socket socket,
      ByteBuffer buffer,
      MongoMessageRequest request) throws MongoException {
    assert send(socket, buffer, request, MongoSendType.TELL) == null;
  }

  MongoMessageReply say(
      Socket socket,
      ByteBuffer buffer,
      MongoMessageRequest request) throws MongoException {
    return send(socket, buffer, request, MongoSendType.SAY);
  }

  // @do-not-check-next-line MethodLength|ExecutableStatementCount|CyclomaticComplexity
  MongoMessageReply send(
      Socket socket,
      ByteBuffer buffer,
      MongoMessageRequest request,
      MongoSendType type) throws MongoException {
    checkClosed();
    try {
      SocketChannel channel = MongoSocketChannels.get(socket);

      // write
      buffer.clear();
      request.writeTo(buffer);
      buffer.flip();
      channel.write(buffer);

      // say
      if (type == MongoSendType.SAY) {
        // read
        buffer.clear();
        buffer.limit(Ints.BYTES);
        channel.read(buffer);
        buffer.limit(buffer.getInt(0));
        while (buffer.hasRemaining())
          channel.read(buffer);
        buffer.flip();

        // return
        MongoMessageReply reply = new DefaultMessageReply();
        reply.readFrom(buffer);
        MongoExceptions.check(reply);
        return reply;
      }

      // tell
      if (MongoWritePreferences.send(writePreference()))
        MongoExceptions.check(command(socket, buffer, "admin", getLastError));
      return null;
    } catch (IOException ex) {
      throw new MongoIOException(ex);
    }
  }
}
