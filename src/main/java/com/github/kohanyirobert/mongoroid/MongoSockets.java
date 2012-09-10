package com.github.kohanyirobert.mongoroid;

import com.google.common.primitives.Ints;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

final class MongoSockets {

  private MongoSockets() {}

  public static void connect(
      Socket socket,
      InetSocketAddress address,
      MongoConfig config) throws MongoException {
    connect(socket, address, config.connectTimeout(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS);
  }

  public static void connect(
      Socket socket,
      InetSocketAddress address,
      int connectTimeout,
      TimeUnit connectTimeoutUnit) throws MongoException {
    try {
      socket.connect(address, Ints.checkedCast(connectTimeoutUnit.toMillis(connectTimeout)));
    } catch (IOException ex) {
      throw new MongoIOException(ex);
    }
  }

  public static Socket get(MongoConfig config) throws MongoException {
    return get(config.readTimeout(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS);
  }

  public static Socket get(
      int readTimeout,
      TimeUnit readTimeoutUnit) throws MongoException {
    Socket socket = null;
    try {
      socket = new Socket();
      socket.setSoTimeout(Ints.checkedCast(readTimeoutUnit.toMillis(readTimeout)));
      return socket;
    } catch (IOException ex) {
      throw new MongoIOException(ex);
    }
  }

  public static Socket get(
      InetSocketAddress address,
      MongoConfig config) throws MongoException {
    return get(
        address,
        config.connectTimeout(TimeUnit.MILLISECONDS),
        TimeUnit.MILLISECONDS,
        config.readTimeout(TimeUnit.MILLISECONDS),
        TimeUnit.MILLISECONDS);
  }

  // @do-not-check-next-line ParameterNumber
  public static Socket get(
      InetSocketAddress address,
      int connectTimeout,
      TimeUnit connectTimeoutUnit,
      int readTimeout,
      TimeUnit readTimeoutUnit) throws MongoException {
    Socket socket = null;
    try {
      socket = new Socket();
      socket.setSoTimeout(Ints.checkedCast(readTimeoutUnit.toMillis(readTimeout)));
      socket.connect(address, Ints.checkedCast(connectTimeoutUnit.toMillis(connectTimeout)));
      return socket;
    } catch (IOException ex) {
      throw new MongoIOException(ex);
    }
  }
}
