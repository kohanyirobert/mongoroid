package com.github.kohanyirobert.mongoroid;

import com.google.common.base.Objects;

// @checkstyle:off Javadoc
public final class MongoIndexes {

  private MongoIndexes() {}

  public static MongoIndex get() {
    return builder().build();
  }

  public static MongoIndex.Builder builder() {
    return new DefaultIndexBuilder();
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(MongoIndexes.class).toString();
  }
}
