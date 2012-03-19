package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

import com.google.common.base.Objects;

import java.util.List;

final class DefaultReadPreference implements MongoReadPreference {

  private final boolean primary;
  private final boolean secondary;
  private final List<BsonDocument> tags;

  DefaultReadPreference(
      boolean primary,
      boolean secondary,
      List<BsonDocument> tags) {
    this.primary = primary;
    this.secondary = secondary;
    this.tags = tags;
  }

  @Override
  public boolean primary() {
    return primary;
  }

  @Override
  public boolean secondary() {
    return secondary;
  }

  @Override
  public List<BsonDocument> tags() {
    return tags;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(
        Boolean.valueOf(primary()),
        Boolean.valueOf(secondary()),
        tags());
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof MongoReadPreference) {
      MongoReadPreference other = (MongoReadPreference) object;
      return primary() == other.primary()
          && secondary() == other.secondary()
          && tags().equals(other.tags());
    }
    return false;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(MongoReadPreference.class)
        .add("primary", primary())
        .add("secondary", secondary())
        .add("tags", tags())
        .toString();
  }
}
