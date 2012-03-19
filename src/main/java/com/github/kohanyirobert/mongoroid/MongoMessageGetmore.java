package com.github.kohanyirobert.mongoroid;

interface MongoMessageGetmore extends MongoMessageRequest {

  String fullCollectionName();

  void fullCollectionName(String fullCollectionName);

  int numberToReturn();

  void numberToReturn(int numberToReturn);

  long cursorId();

  void cursorId(long cursorId);
}
