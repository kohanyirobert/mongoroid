package com.github.kohanyirobert.mongoroid;

interface MongoMessageHeader {

  int messageLength();

  void messageLength(int messageLength);

  int requestId();

  void requestId(int requestId);

  int responseTo();

  void responseTo(int responseTo);

  MongoMessageOpcode opcode();

  void opcode(MongoMessageOpcode opcode);
}
