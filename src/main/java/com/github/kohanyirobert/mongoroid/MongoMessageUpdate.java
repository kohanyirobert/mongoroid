package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

interface MongoMessageUpdate extends MongoMessageRequest {

  String fullCollectionName();

  void fullCollectionName(String fullCollectionName);

  BsonDocument selector();

  void selector(BsonDocument selector);

  BsonDocument update();

  void update(BsonDocument update);

  boolean upsert();

  void upsert(boolean upsert);

  boolean multiUpdate();

  void multiUpdate(boolean multiUpdate);
}
