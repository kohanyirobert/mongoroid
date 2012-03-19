package com.github.kohanyirobert.mongoroid;

import java.nio.ByteBuffer;

interface MongoMessageResponse extends MongoMessageHeader {

  void readFrom(ByteBuffer buffer);
}
