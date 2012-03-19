package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

// @checkstyle:off Javadoc
public interface MongoFind {

  BsonDocument selector();

  BsonDocument fields();

  int skip();

  int limit();

  BsonDocument sort();

  boolean explain();

  boolean snapshot();

  BsonDocument hint();

  interface Builder {

    Builder selector(BsonDocument selector);

    Builder fields(BsonDocument fields);

    Builder skip(int skip);

    Builder limit(int limit);

    Builder sort(BsonDocument sort);

    Builder explain(boolean explain);

    Builder snapshot(boolean snapshot);

    Builder hint(BsonDocument hint);

    MongoFind build();
  }
}
