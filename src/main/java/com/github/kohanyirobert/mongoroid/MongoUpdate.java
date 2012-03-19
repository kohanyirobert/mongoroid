package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

// @checkstyle:off Javadoc
public interface MongoUpdate {

  BsonDocument selector();

  BsonDocument document();

  boolean upsert();

  boolean multi();

  interface Builder {

    Builder selector(BsonDocument selector);

    Builder document(BsonDocument document);

    Builder upsert(boolean upsert);

    Builder multi(boolean multi);

    MongoUpdate build();
  }
}
