package com.github.kohanyirobert.mongoroid;

import com.google.common.base.Objects;

// @checkstyle:off Javadoc
public final class MongoCounts {

  private MongoCounts() {}

  public static MongoCount get() {
    return builder().build();
  }

  public static MongoCount.Builder builder() {
    return new DefaultCountBuilder();
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(MongoCounts.class).toString();
  }
}
