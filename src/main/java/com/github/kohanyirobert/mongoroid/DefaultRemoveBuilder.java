package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;
import com.github.kohanyirobert.ebson.BsonDocuments;

import com.google.common.base.Preconditions;

final class DefaultRemoveBuilder implements MongoRemove.Builder {

  private BsonDocument selector = BsonDocuments.of();
  private boolean single;

  DefaultRemoveBuilder() {}

  @Override
  public MongoRemove.Builder selector(BsonDocument selector) {
    Preconditions.checkNotNull(selector);
    this.selector = selector;
    return this;
  }

  @Override
  public MongoRemove.Builder single(boolean single) {
    this.single = single;
    return this;
  }

  @Override
  public MongoRemove build() {
    return new DefaultRemove(selector, single);
  }
}
