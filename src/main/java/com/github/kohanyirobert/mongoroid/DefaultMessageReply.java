package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;
import com.github.kohanyirobert.ebson.BsonDocuments;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class DefaultMessageReply extends AbstractMessageResponse implements MongoMessageReply {

  private static final int CURSOR_NOT_FOUND = 0;
  private static final int QUERY_FAILURE = 1;
  private static final int SHARD_CONFIG_STALE = 2;
  private static final int AWAIT_CAPABLE = 3;

  private static final int[][] RESERVED = new int[][] {{4, 31}};

  private MongoMessageFlags responseFlags = new DefaultMessageFlags();
  private long cursorId;
  private int startingFrom;
  private int numberReturned;
  private final List<BsonDocument> documents = Lists.newArrayList();

  DefaultMessageReply() {
    super();
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
  public int startingFrom() {
    return startingFrom;
  }

  @Override
  public void startingFrom(int startingFrom) {
    Preconditions.checkArgument(startingFrom >= 0);
    this.startingFrom = startingFrom;
  }

  @Override
  public int numberReturned() {
    return numberReturned;
  }

  @Override
  public void numberReturned(int numberReturned) {
    Preconditions.checkArgument(numberReturned >= 0);
    this.numberReturned = numberReturned;
  }

  @Override
  public List<BsonDocument> documents() {
    return Collections.unmodifiableList(documents);
  }

  @Override
  public void documents(BsonDocument... documents) {
    Preconditions.checkNotNull(documents);
    documents(Arrays.asList(documents));
  }

  @Override
  public void documents(Iterable<BsonDocument> documents) {
    Preconditions.checkNotNull(documents);
    Iterables.addAll(this.documents, documents);
  }

  @Override
  public boolean cursorNotFound() {
    return responseFlags.get(CURSOR_NOT_FOUND);
  }

  @Override
  public void cursorNotFound(boolean cursorNotFound) {
    responseFlags.set(CURSOR_NOT_FOUND, cursorNotFound);
  }

  @Override
  public boolean queryFailure() {
    return responseFlags.get(QUERY_FAILURE);
  }

  @Override
  public void queryFailure(boolean queryFailure) {
    responseFlags.set(QUERY_FAILURE, queryFailure);
  }

  @Override
  public boolean shardConfigStale() {
    return responseFlags.get(SHARD_CONFIG_STALE);
  }

  @Override
  public void shardConfigStale(boolean shardConfigStale) {
    responseFlags.set(SHARD_CONFIG_STALE, shardConfigStale);
  }

  @Override
  public boolean awaitCapable() {
    return responseFlags.get(AWAIT_CAPABLE);
  }

  @Override
  public void awaitCapable(boolean awaitCapable) {
    responseFlags.set(AWAIT_CAPABLE, awaitCapable);
  }

  @Override
  public void readFrom(ByteBuffer buffer) {
    Preconditions.checkNotNull(buffer);

    messageLength = buffer.getInt();
    requestId = buffer.getInt();
    responseTo = buffer.getInt();
    opcode = MongoMessageOpcode.find(buffer.getInt());
    responseFlags = new DefaultMessageFlags(buffer.getInt());

    for (int[] range : RESERVED)
      for (int position = range[0]; position <= range[1]; position++)
        assert !responseFlags.get(position);

    cursorId = buffer.getLong();
    startingFrom = buffer.getInt();
    numberReturned = buffer.getInt();
    documents.clear();
    for (int i = 0; i < numberReturned; i++)
      documents.add(BsonDocuments.readFrom(buffer));
  }
}
