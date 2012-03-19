package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

final class DefaultRemove implements MongoRemove {

  private final BsonDocument selector;
  private final boolean single;

  DefaultRemove(BsonDocument selector, boolean single) {
    this.selector = selector;
    this.single = single;
  }

  @Override
  public BsonDocument selector() {
    return selector;
  }

  @Override
  public boolean single() {
    return single;
  }
}
