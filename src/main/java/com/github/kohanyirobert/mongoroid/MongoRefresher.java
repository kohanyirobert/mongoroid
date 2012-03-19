package com.github.kohanyirobert.mongoroid;

import java.net.InetSocketAddress;

interface MongoRefresher extends AutoCloseable {

  MongoConnection connection();

  InetSocketAddress address(MongoSendType type) throws MongoException;

  @Override
  void close() throws MongoException;
}
