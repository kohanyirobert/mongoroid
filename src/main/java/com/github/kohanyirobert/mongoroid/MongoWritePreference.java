package com.github.kohanyirobert.mongoroid;

import java.util.concurrent.TimeUnit;

/**
 * Representation of a write preference used by {@linkplain MongoConnection
 * connections} to determine how to send write operations in case of
 * communicating with more than one database instance.
 * <p>
 * <b>Notes:</b> use the {@linkplain MongoWritePreferences write preferences
 * utility class} to create new write preferences.
 * </p>
 */
// @checkstyle:off Javadoc
public interface MongoWritePreference {

  String w();

  int wTimeout(TimeUnit wTimeoutUnit);

  boolean fsync();

  boolean j();

  boolean journal();

  interface Builder {

    Builder w(String w);

    Builder wTimeout(int wTimeout, TimeUnit wTimeoutUnit);

    Builder fsync(boolean fsync);

    Builder j(boolean j);

    Builder journal(boolean journal);

    MongoWritePreference build();
  }
}
