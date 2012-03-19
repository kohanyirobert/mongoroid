package com.github.kohanyirobert.mongoroid;

import com.google.common.base.Objects;

// @checkstyle:off Javadoc
public final class MongoInserts {

  private MongoInserts() {}

  public static MongoInsert get() {
    return builder().build();
  }

  public static MongoInsert.Builder builder() {
    return new DefaultInsertBuilder();
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(MongoInserts.class).toString();
  }
}
