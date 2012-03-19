package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

import java.util.Iterator;

// @checkstyle:off Javadoc
public interface MongoCursor extends Iterator<BsonDocument>, AutoCloseable {

  @Override
  void close() throws MongoException;
}
