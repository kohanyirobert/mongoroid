package com.github.kohanyirobert.mongoroid;

import com.google.common.base.Preconditions;

abstract class AbstractMessageHeader implements MongoMessageHeader {

  protected int messageLength;
  protected int requestId;
  protected MongoMessageOpcode opcode;
  protected int responseTo;

  protected AbstractMessageHeader(MongoMessageOpcode opcode) {
    this(0, 0, opcode);
  }

  private AbstractMessageHeader(
      int messageLength,
      int responseTo,
      MongoMessageOpcode opcode) {
    this(messageLength, MongoMessageRequestIds.get(), responseTo, opcode);
  }

  protected AbstractMessageHeader(
      int messageLength,
      int requestId,
      int responseTo,
      MongoMessageOpcode opcode) {
    messageLength(messageLength);
    requestId(requestId);
    responseTo(responseTo);
    opcode(opcode);
  }

  @Override
  public final int messageLength() {
    return messageLength;
  }

  @Override
  public final void messageLength(int messageLength) {
    Preconditions.checkArgument(messageLength >= 0);
    this.messageLength = messageLength;
  }

  @Override
  public final int requestId() {
    return requestId;
  }

  @Override
  public final void requestId(int requestId) {
    this.requestId = requestId;
  }

  @Override
  public final int responseTo() {
    return responseTo;
  }

  @Override
  public final void responseTo(int responseTo) {
    this.responseTo = responseTo;
  }

  @Override
  public final MongoMessageOpcode opcode() {
    return opcode;
  }

  @Override
  public final void opcode(MongoMessageOpcode opcode) {
    Preconditions.checkNotNull(opcode);
    this.opcode = opcode;
  }
}
