package com.github.kohanyirobert.mongoroid;

import com.google.common.base.Preconditions;

import java.nio.ByteBuffer;

abstract class AbstractMessageRequest
    extends AbstractMessageHeader
    implements MongoMessageRequest {

  protected AbstractMessageRequest(MongoMessageOpcode opcode) {
    super(opcode);
  }

  @Override
  public final void writeTo(ByteBuffer buffer) {
    Preconditions.checkNotNull(buffer);

    writeHeaderTo(buffer);
    writeBodyTo(buffer);

    messageLength = buffer.position();
    buffer.putInt(0, messageLength);
  }

  protected abstract void writeBodyTo(ByteBuffer buffer);

  private void writeHeaderTo(ByteBuffer buffer) {
    buffer.putInt(0);
    buffer.putInt(requestId);
    buffer.putInt(responseTo);
    buffer.putInt(opcode.code());
  }
}
