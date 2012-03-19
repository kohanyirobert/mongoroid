package com.github.kohanyirobert.mongoroid;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicLong;

final class MongoByteBuffers {

  private static final long MAX_CALLS = 1000;
  private static final AtomicLong CALLS = new AtomicLong();
  private static final ThreadLocal<ByteBuffer> BYTE_BUFFER = new ThreadLocal<>();

  private MongoByteBuffers() {}

  public static ByteBuffer get(MongoConfig config) {
    assert CALLS.incrementAndGet() <= MAX_CALLS;
    if (BYTE_BUFFER.get() == null)
      BYTE_BUFFER.set(ByteBuffer.allocate(config.bufferSize())
          .order(ByteOrder.LITTLE_ENDIAN));
    return BYTE_BUFFER.get();
  }
}
