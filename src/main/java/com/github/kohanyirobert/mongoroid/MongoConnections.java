package com.github.kohanyirobert.mongoroid;

import com.google.common.base.Objects;

/**
 * Utility class for working with {@linkplain MongoConnection connections}.
 */
public final class MongoConnections {

  private MongoConnections() {}

  @Override
  public String toString() {
    return Objects.toStringHelper(MongoConnections.class).toString();
  }

  /**
   * Returns a new default connection.
   * 
   * @return a new default connection
   */
  public static MongoConnection get() {
    return builder().build();
  }

  /**
   * Returns a new {@linkplain MongoConnection.Builder connection builder}.
   * 
   * @return a new connection builder
   */
  public static MongoConnection.Builder builder() {
    return new DefaultConnectionBuilder();
  }
}
