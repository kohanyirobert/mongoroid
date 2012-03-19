package com.github.kohanyirobert.mongoroid;

import com.google.common.base.Objects;

/**
 * Utility class for working with {@linkplain MongoSeed seeds}.
 */
public final class MongoSeeds {

  private MongoSeeds() {}

  @Override
  public String toString() {
    return Objects.toStringHelper(MongoSeeds.class).toString();
  }

  /**
   * Returns the default seed.
   * 
   * @return the default seed
   */
  public static MongoSeed get() {
    return builder().build();
  }

  /**
   * Returns a new {@linkplain MongoSeed.Builder seed builder}.
   * 
   * @return a new seed builder
   */
  public static MongoSeed.Builder builder() {
    return new DefaultSeedBuilder();
  }
}
