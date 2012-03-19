package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;
import com.github.kohanyirobert.mongoroid.MongoInsert.Builder;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

final class DefaultInsertBuilder implements MongoInsert.Builder {

  private final ImmutableList.Builder<BsonDocument> documents = ImmutableList.builder();
  private boolean continueOnError;

  DefaultInsertBuilder() {}

  @Override
  public Builder documents(BsonDocument... documents) {
    Preconditions.checkNotNull(documents);
    this.documents.add(documents);
    return this;
  }

  @Override
  public Builder documents(Iterable<BsonDocument> documents) {
    Preconditions.checkNotNull(documents);
    this.documents.addAll(documents);
    return this;
  }

  @Override
  public Builder continueOnError(boolean continueOnError) {
    this.continueOnError = continueOnError;
    return this;
  }

  @Override
  public MongoInsert build() {
    return new DefaultInsert(documents.build(), continueOnError);
  }
}
