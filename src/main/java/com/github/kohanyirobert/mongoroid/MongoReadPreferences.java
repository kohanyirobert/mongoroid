package com.github.kohanyirobert.mongoroid;

import com.google.common.base.Objects;

/**
 * Utility class for working with {@linkplain MongoReadPreference read
 * preferences}.
 */
public final class MongoReadPreferences {

  private MongoReadPreferences() {}

  @Override
  public String toString() {
    return Objects.toStringHelper(MongoReadPreferences.class).toString();
  }

  /**
   * Returns the default read preference.
   * 
   * @return the default read preference
   */
  public static MongoReadPreference get() {
    return builder().build();
  }

  /**
   * Returns a new {@linkplain MongoReadPreference.Builder read preference
   * builder}.
   * 
   * @return a new read preference builder
   */
  public static MongoReadPreference.Builder builder() {
    return new DefaultReadPreferenceBuilder();
  }
}
