package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

final class DefaultModify implements MongoModify {

  private final BsonDocument selector;
  private final BsonDocument document;
  private final BsonDocument fields;
  private final BsonDocument sort;
  private final boolean remove;
  private final boolean modified;
  private final boolean upsert;

  // @do-not-check-next-line ParameterNumber
  DefaultModify(
      BsonDocument selector,
      BsonDocument document,
      BsonDocument fields,
      BsonDocument sort,
      boolean remove,
      boolean modified,
      boolean upsert) {
    this.selector = selector;
    this.document = document;
    this.fields = fields;
    this.sort = sort;
    this.remove = remove;
    this.modified = modified;
    this.upsert = upsert;
  }

  @Override
  public BsonDocument selector() {
    return selector;
  }

  @Override
  public BsonDocument document() {
    return document;
  }

  @Override
  public BsonDocument fields() {
    return fields;
  }

  @Override
  public BsonDocument sort() {
    return sort;
  }

  @Override
  public boolean remove() {
    return remove;
  }

  @Override
  public boolean modified() {
    return modified;
  }

  @Override
  public boolean upsert() {
    return upsert;
  }
}
