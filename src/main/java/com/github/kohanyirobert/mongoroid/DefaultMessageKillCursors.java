package com.github.kohanyirobert.mongoroid;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.primitives.Longs;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

final class DefaultMessageKillCursors extends AbstractMessageRequest
    implements MongoMessageKillCursors {

  private int numberOfCursorIds;
  private final List<Long> cursorIds = Lists.newArrayList();

  DefaultMessageKillCursors() {
    super(MongoMessageOpcode.KILL_CURSORS);
  }

  @Override
  public int numberOfCursorIds() {
    return cursorIds.size();
  }

  @Override
  public void numberOfCursorIds(int numberOfCursorIds) {
    Preconditions.checkArgument(numberOfCursorIds >= 0);
    this.numberOfCursorIds = numberOfCursorIds;
  }

  @Override
  public List<Long> cursorIds() {
    return Collections.unmodifiableList(cursorIds);
  }

  @Override
  public void cursorIds(long... cursorIds) {
    Preconditions.checkNotNull(cursorIds);
    cursorIds(Longs.asList(cursorIds));
  }

  @Override
  public void cursorIds(Iterable<Long> cursorIds) {
    Preconditions.checkNotNull(cursorIds);
    Iterables.addAll(this.cursorIds, cursorIds);
  }

  @Override
  protected void writeBodyTo(ByteBuffer buffer) {
    Preconditions.checkNotNull(buffer);
    Preconditions.checkArgument(numberOfCursorIds == cursorIds.size());

    buffer.putInt(0);
    buffer.putInt(numberOfCursorIds);
    for (int i = 0; i < numberOfCursorIds; i++)
      buffer.putLong(cursorIds.get(i).longValue());
  }
}
