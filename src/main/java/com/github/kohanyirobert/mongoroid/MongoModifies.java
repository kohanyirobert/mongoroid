package com.github.kohanyirobert.mongoroid;

import com.google.common.base.Objects;

// @checkstyle:off Javadoc
public final class MongoModifies {

  private MongoModifies() {}

  public static MongoModify get() {
    return builder().build();
  }

  public static MongoModify.Builder builder() {
    return new DefaultModifyBuilder();
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(MongoModifies.class).toString();
  }
}
