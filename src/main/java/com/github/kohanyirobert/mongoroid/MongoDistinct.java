package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

// @checkstyle:off Javadoc
public interface MongoDistinct {

  String key();

  BsonDocument selector();

  interface Builder {

    Builder key(String key);

    Builder selector(BsonDocument selector);

    MongoDistinct build();
  }
}
