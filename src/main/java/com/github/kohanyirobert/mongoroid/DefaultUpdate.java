package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

final class DefaultUpdate implements MongoUpdate {

  private final BsonDocument selector;
  private final BsonDocument update;
  private final boolean upsert;
  private final boolean multi;

  DefaultUpdate(
      BsonDocument selector,
      BsonDocument update,
      boolean upsert,
      boolean multi) {
    this.selector = selector;
    this.update = update;
    this.upsert = upsert;
    this.multi = multi;
  }

  @Override
  public BsonDocument selector() {
    return selector;
  }

  @Override
  public BsonDocument document() {
    return update;
  }

  @Override
  public boolean upsert() {
    return upsert;
  }

  @Override
  public boolean multi() {
    return multi;
  }
}
