package com.github.kohanyirobert.mongoroid;

import com.google.common.base.Objects;

// @checkstyle:off Javadoc
public final class MongoFinds {

  private MongoFinds() {}

  public static MongoFind get() {
    return builder().build();
  }

  public static MongoFind.Builder builder() {
    return new DefaultFindBuilder();
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(MongoFinds.class).toString();
  }
}
