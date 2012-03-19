package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonBytes;
import com.github.kohanyirobert.ebson.BsonDocument;
import com.github.kohanyirobert.ebson.BsonDocuments;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class DefaultMessageInsert extends AbstractMessageRequest implements MongoMessageInsert {

  private static final int CONTINUE_ON_ERROR = 0;

  private static final int[][] RESERVED = new int[][] {{1, 31}};

  private final MongoMessageFlags flags = new DefaultMessageFlags();
  private String fullCollectionName;
  private final List<BsonDocument> documents = Lists.newArrayList();

  DefaultMessageInsert() {
    super(MongoMessageOpcode.INSERT);
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
  public boolean continueOnError() {
    return flags.get(CONTINUE_ON_ERROR);
  }

  @Override
  public void continueOnError(boolean continueOnError) {
    flags.set(CONTINUE_ON_ERROR, continueOnError);
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
    for (BsonDocument document : documents)
      BsonDocuments.writeTo(buffer, document);
  }
}
