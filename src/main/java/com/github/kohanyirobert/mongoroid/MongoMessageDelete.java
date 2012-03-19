package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

interface MongoMessageDelete extends MongoMessageRequest {

  String fullCollectionName();

  void fullCollectionName(String fullCollectionName);

  BsonDocument selector();

  void selector(BsonDocument selector);

  boolean singleRemove();

  void singleRemove(boolean singleDelete);
}
