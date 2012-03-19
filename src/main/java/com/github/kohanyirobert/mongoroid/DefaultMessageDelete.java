package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonBytes;
import com.github.kohanyirobert.ebson.BsonDocument;
import com.github.kohanyirobert.ebson.BsonDocuments;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;

import java.nio.ByteBuffer;

final class DefaultMessageDelete extends AbstractMessageRequest implements MongoMessageDelete {

  private static final int SINGLE_REMOVE = 0;

  private static final int[][] RESERVED = new int[][] {{1, 31}};

  private String fullCollectionName;
  private final MongoMessageFlags flags = new DefaultMessageFlags();
  private BsonDocument selector;

  DefaultMessageDelete() {
    super(MongoMessageOpcode.DELETE);
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
  public BsonDocument selector() {
    return selector;
  }

  @Override
  public void selector(BsonDocument selector) {
    Preconditions.checkNotNull(selector);
    this.selector = selector;
  }

  @Override
  public boolean singleRemove() {
    return flags.get(SINGLE_REMOVE);
  }

  @Override
  public void singleRemove(boolean singleRemove) {
    flags.set(SINGLE_REMOVE, singleRemove);
  }

  @Override
  public String toString() {
    return selector.toString();
  }

  @Override
  protected void writeBodyTo(ByteBuffer buffer) {
    Preconditions.checkNotNull(buffer);

    for (int[] range : RESERVED)
      for (int position = range[0]; position <= range[1]; position++)
        assert !flags.get(position);

    buffer.putInt(0);
    buffer.put(fullCollectionName.getBytes(Charsets.UTF_8));
    buffer.put(BsonBytes.EOO);
    buffer.putInt(flags.mask());
    BsonDocuments.writeTo(buffer, selector);
  }
}
