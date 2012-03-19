package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonBytes;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;

import java.nio.ByteBuffer;

final class DefaultMessageGetmore
    extends AbstractMessageRequest
    implements MongoMessageGetmore {

  private String fullCollectionName;
  private int numberToReturn;
  private long cursorId;

  DefaultMessageGetmore() {
    super(MongoMessageOpcode.GETMORE);
  }

  @Override
  public String fullCollectionName() {
    return fullCollectionName;
  }

  @Override
  public void fullCollectionName(String fullCollectionName) {
    Preconditions.checkNotNull(fullCollectionName);
    this.fullCollectionName = fullCollectionName;
  }

  @Override
  public int numberToReturn() {
    return numberToReturn;
  }

  @Override
  public void numberToReturn(int numberToReturn) {
    Preconditions.checkArgument(numberToReturn >= 0);
    this.numberToReturn = numberToReturn;
  }

  @Override
  public long cursorId() {
    return cursorId;
  }

  @Override
  public void cursorId(long cursorId) {
    this.cursorId = cursorId;
  }

  @Override
  protected void writeBodyTo(ByteBuffer buffer) {
    Preconditions.checkNotNull(buffer);

    buffer.putInt(0);
    buffer.put(fullCollectionName.getBytes(Charsets.UTF_8));
    buffer.put(BsonBytes.EOO);
    buffer.putInt(numberToReturn);
    buffer.putLong(cursorId);
  }
}
