package com.github.kohanyirobert.mongoroid;

import com.google.common.base.Objects;

// @checkstyle:off Javadoc
public final class MongoDistincts {

  private MongoDistincts() {}

  public static MongoDistinct get() {
    return builder().build();
  }

  public static MongoDistinct.Builder builder() {
    return new DefaultDistinctBuilder();
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(MongoDistincts.class).toString();
  }
}
