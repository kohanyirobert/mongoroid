package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

// @checkstyle:off Javadoc
public interface MongoCount {

  BsonDocument selector();

  BsonDocument fields();

  int skip();

  int limit();

  interface Builder {

    Builder selector(BsonDocument selector);

    Builder fields(BsonDocument fields);

    Builder skip(int skip);

    Builder limit(int limit);

    MongoCount build();
  }
}
