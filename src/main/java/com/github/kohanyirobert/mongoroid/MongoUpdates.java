package com.github.kohanyirobert.mongoroid;

import com.google.common.base.Objects;

// @checkstyle:off Javadoc
public final class MongoUpdates {

  private MongoUpdates() {}

  public static MongoUpdate get() {
    return builder().build();
  }

  public static MongoUpdate.Builder builder() {
    return new DefaultUpdateBuilder();
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(MongoUpdates.class).toString();
  }
}
