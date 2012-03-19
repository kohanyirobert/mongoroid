package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;
import com.github.kohanyirobert.ebson.BsonDocuments;
import com.github.kohanyirobert.mongoroid.MongoModify.Builder;

import com.google.common.base.Preconditions;

final class DefaultModifyBuilder implements MongoModify.Builder {

  private BsonDocument selector = BsonDocuments.of();
  private BsonDocument document;
  private BsonDocument fields = BsonDocuments.of();
  private BsonDocument sort = BsonDocuments.of();
  private boolean remove;
  private boolean modified;
  private boolean upsert;

  DefaultModifyBuilder() {}

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
  public Builder fields(BsonDocument fields) {
    Preconditions.checkNotNull(fields);
    this.fields = fields;
    return this;
  }

  @Override
  public Builder sort(BsonDocument sort) {
    Preconditions.checkNotNull(sort);
    this.sort = sort;
    return this;
  }

  @Override
  public Builder remove(boolean remove) {
    this.remove = remove;
    return this;
  }

  @Override
  public Builder modified(boolean modified) {
    this.modified = modified;
    return this;
  }

  @Override
  public Builder upsert(boolean upsert) {
    this.upsert = upsert;
    return this;
  }

  @Override
  public MongoModify build() {
    return new DefaultModify(
        selector,
        document,
        fields,
        sort,
        remove,
        modified,
        upsert);
  }
}
