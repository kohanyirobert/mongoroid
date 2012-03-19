package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

interface MongoMessageQuery extends MongoMessageRequest {

  String fullCollectionName();

  void fullCollectionName(String fullCollectionName);

  boolean tailableCursor();

  void tailableCursor(boolean tailableCursor);

  boolean slaveOk();

  void slaveOk(boolean slaveOk);

  boolean oplogReplay();

  void oplogReplay(boolean oplogReplay);

  boolean noCursorTimeout();

  void noCursorTimeout(boolean noCursorTimeout);

  boolean awaitData();

  void awaitData(boolean awaitData);

  boolean exhaust();

  void exhaust(boolean exhaust);

  boolean partial();

  void partial(boolean partial);

  int numberToSkip();

  void numberToSkip(int numberToSkip);

  int numberToReturn();

  void numberToReturn(int numberToReturn);

  BsonDocument query();

  void query(BsonDocument query);

  BsonDocument returnFieldSelector();

  void returnFieldSelector(BsonDocument returnFieldSelector);
}
