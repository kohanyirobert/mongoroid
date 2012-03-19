package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

final class DefaultCount implements MongoCount {

  private final BsonDocument selector;
  private final BsonDocument fields;
  private final int skip;
  private final int limit;

  DefaultCount(
      BsonDocument selector,
      BsonDocument fields,
      int skip,
      int limit) {
    this.selector = selector;
    this.fields = fields;
    this.skip = skip;
    this.limit = limit;
  }

  @Override
  public BsonDocument selector() {
    return selector;
  }

  @Override
  public BsonDocument fields() {
    return fields;
  }

  @Override
  public int skip() {
    return skip;
  }

  @Override
  public int limit() {
    return limit;
  }
}
