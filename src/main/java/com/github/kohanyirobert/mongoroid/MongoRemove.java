package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

// @checkstyle:off Javadoc
public interface MongoRemove {

  BsonDocument selector();

  boolean single();

  interface Builder {

    Builder selector(BsonDocument selector);

    Builder single(boolean single);

    MongoRemove build();
  }
}
