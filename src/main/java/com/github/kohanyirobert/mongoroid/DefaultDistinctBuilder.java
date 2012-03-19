package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;
import com.github.kohanyirobert.ebson.BsonDocuments;
import com.github.kohanyirobert.mongoroid.MongoDistinct.Builder;

import com.google.common.base.Preconditions;


final class DefaultDistinctBuilder implements MongoDistinct.Builder {

  private String key;
  private BsonDocument selector = BsonDocuments.of();

  DefaultDistinctBuilder() {}

  @Override
  public Builder key(String key) {
    Preconditions.checkNotNull(key);
    this.key = key;
    return this;
  }

  @Override
  public Builder selector(BsonDocument selector) {
    Preconditions.checkNotNull(selector);
    this.selector = selector;
    return this;
  }

  @Override
  public MongoDistinct build() {
    return new DefaultDistinct(key, selector);
  }
}
