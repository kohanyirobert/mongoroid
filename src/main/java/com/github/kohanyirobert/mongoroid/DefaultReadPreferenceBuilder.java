package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;
import com.github.kohanyirobert.mongoroid.MongoReadPreference.Builder;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.Arrays;

final class DefaultReadPreferenceBuilder implements MongoReadPreference.Builder {

  private boolean primary = true;
  private boolean secondary;
  private final ImmutableList.Builder<BsonDocument> tags = ImmutableList.builder();

  DefaultReadPreferenceBuilder() {}

  @Override
  public Builder primary(boolean primary) {
    this.primary = primary;
    return this;
  }

  @Override
  public Builder secondary(boolean secondary) {
    this.secondary = secondary;
    return this;
  }

  @Override
  public Builder tags(BsonDocument... tags) {
    Preconditions.checkNotNull(tags);
    return tags(Arrays.asList(tags));
  }

  @Override
  public Builder tags(Iterable<BsonDocument> tags) {
    Preconditions.checkNotNull(tags);
    Preconditions.checkArgument(!Iterables.contains(tags, null));
    this.tags.addAll(tags);
    return this;
  }

  @Override
  public MongoReadPreference build() {
    return new DefaultReadPreference(primary, secondary, tags.build());
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(MongoReadPreference.Builder.class).toString();
  }
}
