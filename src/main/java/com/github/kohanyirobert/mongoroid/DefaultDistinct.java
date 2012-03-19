package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

final class DefaultDistinct implements MongoDistinct {

  private final String key;
  private final BsonDocument selector;

  DefaultDistinct(String key, BsonDocument selector) {
    this.key = key;
    this.selector = selector;
  }

  @Override
  public String key() {
    return key;
  }

  @Override
  public BsonDocument selector() {
    return selector;
  }
}
