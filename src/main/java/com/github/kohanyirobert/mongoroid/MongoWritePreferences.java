package com.github.kohanyirobert.mongoroid;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

/**
 * Utility class for working with {@linkplain MongoWritePreferences write
 * preferences}.
 */
public final class MongoWritePreferences {

  private MongoWritePreferences() {}

  @Override
  public String toString() {
    return Objects.toStringHelper(MongoWritePreferences.class).toString();
  }

  /**
   * Returns the default connection.
   * 
   * @return the default connection
   */
  public static MongoWritePreference get() {
    return builder().build();
  }

  /**
   * Returns a new {@linkplain MongoWritePreference.Builder write preference
   * builder}.
   * 
   * @return a new write preference builder
   */
  public static MongoWritePreference.Builder builder() {
    return new DefaultWritePreferenceBuilder();
  }

  // @do-not-check (Cyclomatic|BooleanExpression)Complexity
  static boolean send(MongoWritePreference writePreference) {
    return !Strings.isNullOrEmpty(writePreference.w())
        || writePreference.fsync()
        || writePreference.j()
        || writePreference.journal();
  }
}
