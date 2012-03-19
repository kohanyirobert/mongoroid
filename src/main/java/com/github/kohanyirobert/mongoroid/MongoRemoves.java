package com.github.kohanyirobert.mongoroid;

import com.google.common.base.Objects;

// @checkstyle:off Javadoc
public final class MongoRemoves {

  private MongoRemoves() {}

  public static MongoRemove get() {
    return builder().build();
  }

  public static MongoRemove.Builder builder() {
    return new DefaultRemoveBuilder();
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(MongoRemoves.class).toString();
  }
}
