package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

final class DefaultFind implements MongoFind {

  private final BsonDocument selector;
  private final BsonDocument fields;
  private final int skip;
  private final int limit;
  private final BsonDocument sort;
  private final boolean explain;
  private final boolean snapshot;
  private final BsonDocument hint;
  private final boolean close;

  // @do-not-check-next-line ParameterNumber
  DefaultFind(
      BsonDocument selector,
      BsonDocument fields,
      int skip,
      int limit,
      BsonDocument sort,
      boolean explain,
      boolean snapshot,
      BsonDocument hint,
      boolean close) {
    this.selector = selector;
    this.fields = fields;
    this.skip = skip;
    this.limit = limit;
    this.sort = sort;
    this.explain = explain;
    this.snapshot = snapshot;
    this.hint = hint;
    this.close = close;
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

  @Override
  public BsonDocument sort() {
    return sort;
  }

  @Override
  public boolean explain() {
    return explain;
  }

  @Override
  public boolean snapshot() {
    return snapshot;
  }

  @Override
  public BsonDocument hint() {
    return hint;
  }

  @Override
  public boolean close() {
    return close;
  }
}
