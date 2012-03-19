package com.github.kohanyirobert.mongoroid;

import java.nio.ByteBuffer;

interface MongoMessageRequest extends MongoMessageHeader {

  void writeTo(ByteBuffer buffer);
}
