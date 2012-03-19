package com.github.kohanyirobert.mongoroid;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

final class DefaultConnectionBuilder implements MongoConnection.Builder {

  private MongoConfig config = MongoConfigs.get();
  private MongoSeed seed = MongoSeeds.get();
  private MongoReadPreference readPreference = MongoReadPreferences.get();
  private MongoWritePreference writePreference = MongoWritePreferences.get();

  DefaultConnectionBuilder() {}

  @Override
  public MongoConnection.Builder config(MongoConfig config) {
    Preconditions.checkNotNull(config);
    this.config = config;
    return this;
  }

  @Override
  public MongoConnection.Builder seed(MongoSeed seed) {
    Preconditions.checkNotNull(seed);
    this.seed = seed;
    return this;
  }

  @Override
  public MongoConnection.Builder readPreference(MongoReadPreference readPreference) {
    Preconditions.checkNotNull(readPreference);
    this.readPreference = readPreference;
    return this;
  }

  @Override
  public MongoConnection.Builder writePreference(MongoWritePreference writePreference) {
    Preconditions.checkNotNull(writePreference);
    this.writePreference = writePreference;
    return this;
  }

  @Override
  public MongoConnection build() {
    return new DefaultConnection(config, seed, readPreference, writePreference);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(MongoConnection.Builder.class).toString();
  }
}
