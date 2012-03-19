package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonBytes;
import com.github.kohanyirobert.ebson.BsonDocument;
import com.github.kohanyirobert.ebson.BsonDocuments;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;

import java.nio.ByteBuffer;

final class DefaultMessageQuery extends AbstractMessageRequest
    implements MongoMessageQuery {

  private static final int TAILABLE_CURSOR = 1;
  private static final int SLAVE_OK = 2;
  private static final int OPLOG_REPLAY = 3;
  private static final int NO_CURSOR_TIMEOUT = 4;
  private static final int AWAIT_DATA = 5;
  private static final int EXHAUST = 6;
  private static final int PARTIAL = 7;

  private static final int[][] RESERVED = new int[][] {
    {0, 0},
    {8, 31}
  };

  private final MongoMessageFlags flags = new DefaultMessageFlags();
  private String fullCollectionName;
  private int numberToSkip;
  private int numberToReturn;
  private BsonDocument query;
  private BsonDocument returnFieldSelector;

  DefaultMessageQuery() {
    super(MongoMessageOpcode.QUERY);
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
  public int numberToSkip() {
    return numberToSkip;
  }

  @Override
  public void numberToSkip(int numberToSkip) {
    Preconditions.checkArgument(numberToSkip >= 0);
    this.numberToSkip = numberToSkip;
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
  public BsonDocument query() {
    return query;
  }

  @Override
  public void query(BsonDocument query) {
    Preconditions.checkNotNull(query);
    this.query = query;
  }

  @Override
  public BsonDocument returnFieldSelector() {
    return returnFieldSelector;
  }

  @Override
  public void returnFieldSelector(BsonDocument returnFieldSelector) {
    Preconditions.checkNotNull(returnFieldSelector);
    this.returnFieldSelector = returnFieldSelector;
  }

  @Override
  public boolean tailableCursor() {
    return flags.get(TAILABLE_CURSOR);
  }

  @Override
  public void tailableCursor(boolean tailableCursor) {
    flags.set(TAILABLE_CURSOR, tailableCursor);
  }

  @Override
  public boolean slaveOk() {
    return flags.get(SLAVE_OK);
  }

  @Override
  public void slaveOk(boolean slaveOk) {
    flags.set(SLAVE_OK, slaveOk);
  }

  @Override
  public boolean oplogReplay() {
    return flags.get(OPLOG_REPLAY);
  }

  @Override
  public void oplogReplay(boolean oplogReplay) {
    flags.set(OPLOG_REPLAY, oplogReplay);
  }

  @Override
  public boolean noCursorTimeout() {
    return flags.get(NO_CURSOR_TIMEOUT);
  }

  @Override
  public void noCursorTimeout(boolean noCursorTimeout) {
    flags.set(NO_CURSOR_TIMEOUT, noCursorTimeout);
  }

  @Override
  public boolean awaitData() {
    return flags.get(AWAIT_DATA);
  }

  @Override
  public void awaitData(boolean awaitData) {
    flags.set(AWAIT_DATA, awaitData);
  }

  @Override
  public boolean exhaust() {
    return flags.get(EXHAUST);
  }

  @Override
  public void exhaust(boolean exhaust) {
    flags.set(EXHAUST, exhaust);
  }

  @Override
  public boolean partial() {
    return flags.get(PARTIAL);
  }

  @Override
  public void partial(boolean partial) {
    flags.set(PARTIAL, partial);
  }

  @Override
  public String toString() {
    return query.toString();
  }

  @Override
  protected void writeBodyTo(ByteBuffer buffer) {
    Preconditions.checkNotNull(buffer);

    for (int[] range : RESERVED)
      for (int position = range[0]; position <= range[1]; position++)
        assert !flags.get(position);

    buffer.putInt(flags.mask());
    buffer.put(fullCollectionName.getBytes(Charsets.UTF_8));
    buffer.put(BsonBytes.EOO);
    buffer.putInt(numberToSkip);
    buffer.putInt(numberToReturn);
    BsonDocuments.writeTo(buffer, query);
    if (returnFieldSelector != null)
      BsonDocuments.writeTo(buffer, returnFieldSelector);
  }
}
