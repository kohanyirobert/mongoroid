package com.github.kohanyirobert.mongoroid;

import java.util.List;

interface MongoMessageKillCursors extends MongoMessageRequest {

  int numberOfCursorIds();

  void numberOfCursorIds(int numberOfCursorIds);

  List<Long> cursorIds();

  void cursorIds(long... cursorIds);

  void cursorIds(Iterable<Long> cursorIds);
}
