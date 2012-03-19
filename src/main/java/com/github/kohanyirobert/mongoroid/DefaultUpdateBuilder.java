package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;
import com.github.kohanyirobert.ebson.BsonDocuments;
import com.github.kohanyirobert.mongoroid.MongoUpdate.Builder;

import com.google.common.base.Preconditions;

final class DefaultUpdateBuilder implements MongoUpdate.Builder {

  private BsonDocument selector = BsonDocuments.of();
  private BsonDocument document = BsonDocuments.of();
  private boolean upsert;
  private boolean multi;

  DefaultUpdateBuilder() {}

  @Override
  public Builder selector(BsonDocument selector) {
    Preconditions.checkNotNull(selector);
    this.selector = selector;
    return this;
  }

  @Override
  public Builder document(BsonDocument document) {
    Preconditions.checkNotNull(document);
    this.document = document;
    return this;
  }

  @Override
  public Builder upsert(boolean upsert) {
    this.upsert = upsert;
    return this;
  }

  @Override
  public Builder multi(boolean multi) {
    this.multi = multi;
    return this;
  }

  @Override
  public MongoUpdate build() {
    return new DefaultUpdate(selector, document, upsert, multi);
  }
}
