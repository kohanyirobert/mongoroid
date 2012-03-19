package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

import java.util.List;

interface MongoMessageInsert extends MongoMessageRequest {

  String fullCollectionName();

  void fullCollectionName(String fullCollectionName);

  List<BsonDocument> documents();

  void documents(BsonDocument... documents);

  void documents(Iterable<BsonDocument> documents);

  boolean continueOnError();

  void continueOnError(boolean continueOnError);
}
