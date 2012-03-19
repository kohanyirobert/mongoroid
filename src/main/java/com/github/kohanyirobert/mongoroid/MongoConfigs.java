package com.github.kohanyirobert.mongoroid;

import com.google.common.base.Objects;

/**
 * Utility class for working with {@linkplain MongoConfig configs}.
 */
public final class MongoConfigs {

  private MongoConfigs() {}

  @Override
  public String toString() {
    return Objects.toStringHelper(MongoConfigs.class).toString();
  }

  /**
   * Returns the default config.
   * 
   * @return the default config
   */
  public static MongoConfig get() {
    return builder().build();
  }

  /**
   * Returns a new {@linkplain MongoConfig.Builder config builder}.
   * 
   * @return a new config builder
   */
  public static MongoConfig.Builder builder() {
    return new DefaultConfigBuilder();
  }
}
