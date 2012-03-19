package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

import java.util.List;

interface MongoMessageReply extends MongoMessageResponse {

  long cursorId();

  void cursorId(long cursorId);

  int startingFrom();

  void startingFrom(int startingFrom);

  int numberReturned();

  void numberReturned(int numberReturned);

  List<BsonDocument> documents();

  void documents(BsonDocument... documents);

  void documents(Iterable<BsonDocument> documents);

  boolean cursorNotFound();

  void cursorNotFound(boolean cursorNotFound);

  boolean queryFailure();

  void queryFailure(boolean queryFailure);

  boolean shardConfigStale();

  void shardConfigStale(boolean shardConfigStale);

  boolean awaitCapable();

  void awaitCapable(boolean awaitCapable);
}
