package com.github.kohanyirobert.mongoroid;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

final class DefaultMessageFlags implements MongoMessageFlags {

  private static final int MIN_POSITION = 0;
  private static final int MAX_POSITION = Integer.SIZE - 1;

  private int mask;

  DefaultMessageFlags() {
    this(0);
  }

  DefaultMessageFlags(int mask) {
    this.mask = mask;
  }

  @Override
  public int mask() {
    return mask;
  }

  @Override
  public void set(int position, boolean value) {
    checkPosition(position);
    if (value)
      mask |= 1 << position;
    else
      mask &= ~(1 << position);
  }

  @Override
  public boolean get(int position) {
    return (mask & 1 << checkPosition(position)) != 0;
  }

  @Override
  public void clear() {
    mask = 0;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(Integer.valueOf(mask));
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof DefaultMessageFlags)
      return mask == ((DefaultMessageFlags) object).mask;
    return false;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(MongoMessageFlags.class)
        .add("mask", Strings.padStart(Integer.toBinaryString(mask), Integer.SIZE, '0'))
        .toString();
  }

  private static int checkPosition(int position) {
    Preconditions.checkArgument(position >= MIN_POSITION);
    Preconditions.checkArgument(position <= MAX_POSITION);
    return position;
  }
}
