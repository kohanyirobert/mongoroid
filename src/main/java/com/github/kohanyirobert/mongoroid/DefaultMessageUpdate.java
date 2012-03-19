package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonBytes;
import com.github.kohanyirobert.ebson.BsonDocument;
import com.github.kohanyirobert.ebson.BsonDocuments;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;

import java.nio.ByteBuffer;

final class DefaultMessageUpdate extends AbstractMessageRequest
    implements MongoMessageUpdate {

  private static final int UPSERT = 0;
  private static final int MULTI_UPDATE = 1;

  private static final int[][] RESERVED = new int[][] {{2, 31}};

  private String fullCollectionName;
  private final MongoMessageFlags flags = new DefaultMessageFlags();
  private BsonDocument selector;
  private BsonDocument update;

  DefaultMessageUpdate() {
    super(MongoMessageOpcode.UPDATE);
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
  public BsonDocument update() {
    return update;
  }

  @Override
  public void update(BsonDocument update) {
    Preconditions.checkNotNull(update);
    this.update = update;
  }

  @Override
  public boolean upsert() {
    return flags.get(UPSERT);
  }

  @Override
  public void upsert(boolean upsert) {
    flags.set(UPSERT, upsert);
  }

  @Override
  public boolean multiUpdate() {
    return flags.get(MULTI_UPDATE);
  }

  @Override
  public void multiUpdate(boolean multiUpdate) {
    flags.set(MULTI_UPDATE, multiUpdate);
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
    BsonDocuments.writeTo(buffer, update);
  }
}
