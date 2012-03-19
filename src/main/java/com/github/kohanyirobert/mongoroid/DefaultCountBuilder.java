package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;
import com.github.kohanyirobert.ebson.BsonDocuments;
import com.github.kohanyirobert.mongoroid.MongoCount.Builder;

import com.google.common.base.Preconditions;


final class DefaultCountBuilder implements MongoCount.Builder {

  private BsonDocument selector = BsonDocuments.of();
  private BsonDocument fields = BsonDocuments.of();
  private int skip;
  private int limit;

  DefaultCountBuilder() {}

  @Override
  public Builder selector(BsonDocument selector) {
    Preconditions.checkNotNull(selector);
    this.selector = selector;
    return this;
  }

  @Override
  public Builder fields(BsonDocument fields) {
    Preconditions.checkNotNull(fields);
    this.fields = fields;
    return this;
  }

  @Override
  public Builder skip(int skip) {
    Preconditions.checkArgument(skip >= 0);
    this.skip = skip;
    return this;
  }

  @Override
  public Builder limit(int limit) {
    Preconditions.checkArgument(limit >= 0);
    this.limit = limit;
    return this;
  }

  @Override
  public MongoCount build() {
    return new DefaultCount(selector, fields, skip, limit);
  }
}
