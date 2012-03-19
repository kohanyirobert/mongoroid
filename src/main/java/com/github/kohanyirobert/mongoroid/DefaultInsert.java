package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

import java.util.List;

final class DefaultInsert implements MongoInsert {

  private final List<BsonDocument> documents;
  private final boolean continueOnError;

  DefaultInsert(List<BsonDocument> documents, boolean continueOnError) {
    this.documents = documents;
    this.continueOnError = continueOnError;
  }

  @Override
  public List<BsonDocument> documents() {
    return documents;
  }

  @Override
  public boolean continueOnError() {
    return continueOnError;
  }
}
