package com.github.kohanyirobert.mongoroid;

import java.util.List;

/**
 * Representation of a <a
 * href="http://www.mongodb.org/display/DOCS/Connections">MongoDB connection</a>
 * used to connect to one or more database instances.
 * <p>
 * <b>Notes:</b> use the {@linkplain MongoConnections connections utility class}
 * to create new connections.
 * </p>
 */
public interface MongoConnection extends AutoCloseable {

  /**
   * Returns this connection's configuration.
   * 
   * @return this connection's configuration
   */
  MongoConfig config();

  /**
   * Returns this connection's seed.
   * 
   * @return this connection's seed
   */
  MongoSeed seed();

  /**
   * Returns this connection's read preference.
   * 
   * @return this connection's read preference
   */
  MongoReadPreference readPreference();

  /**
   * Returns this connection's write preference.
   * 
   * @return this connection's write preference
   */
  MongoWritePreference writePreference();

  /**
   * Returns an immutable view of the databases known by this connection.
   * 
   * @return an immutable view of the databases known by this connection
   * @throws MongoException if a network or authentication error occurs during
   * the operation, or if this connection is already closed
   */
  List<MongoDatabase> databases() throws MongoException;

  /**
   * Returns the database associated with {@code name}.
   * <p>
   * <b>Note:</b> databases are created on first access. Subsequent calls to
   * this method with the same parameter will always return the same database
   * instance.
   * </p>
   * 
   * @param name the name of the database to be returned
   * @return the database associated with {@code name}
   * @throws IllegalArgumentException if {@code name} is null
   * @throws MongoException if this connection is already closed
   */
  MongoDatabase database(String name) throws MongoException;

  /**
   * Closes this connection along with its associated resources.
   * <p>
   * <b>Notes:</b> after calling this method every other method will throw an
   * exception that uses this connection to communicate to a database instance.
   * </p>
   * 
   * @throws MongoException if this connection is already closed
   */
  @Override
  void close() throws MongoException;

  /**
   * {@linkplain MongoConnection Connection} builder.
   * <p>
   * <b>Notes:</b>
   * <ul>
   * <li>Calling a builder's {@linkplain #build} method <em>does not clear its
   * state</em>, however subsequent invocations of it returns new connections.</li>
   * <li>Connection builders can be acquired via the
   * {@linkplain MongoConnections connections utility class}.</li>
   * </ul>
   * </p>
   */
  interface Builder {

    /**
     * Sets {@code config} as the one to be used by the connection being built.
     * <p>
     * <b>Note:</b> defaults to the config returned by the
     * {@linkplain MongoConfigs configs utility class'}
     * {@linkplain MongoConfigs#get() get method}.
     * </p>
     * 
     * @param config the config to be used by the connection being built
     * @return this builder
     * @throws NullPointerException if {@code config} is null
     */
    Builder config(MongoConfig config);

    /**
     * Sets {@code seed} as the one to be used by the connection being built.
     * <p>
     * <b>Note:</b> defaults to the seed returned by the {@linkplain MongoSeeds
     * seeds utility class'} {@linkplain MongoSeeds#get() get method}.
     * </p>
     * 
     * @param seed the seed to be used by the connection being built
     * @return this builder
     * @throws NullPointerException if {@code seed} is null
     */
    Builder seed(MongoSeed seed);

    /**
     * Sets {@code readPreference} as the one to be used by the connection
     * being built.
     * <p>
     * <b>Note:</b> defaults to the read preference returned by the
     * {@linkplain MongoReadPreferences read preferences utility class'}
     * {@linkplain MongoReadPreferences#get() get method}.
     * </p>
     * 
     * @param readPreference the read preference to be used by the connection
     * being built
     * @return this builder
     * @throws NullPointerException if {@code readPreference} is null
     */
    Builder readPreference(MongoReadPreference readPreference);

    /**
     * Sets {@code writePreference} as the one to be used by the connection
     * being built.
     * <p>
     * <b>Note:</b> defaults to the write preference returned by the
     * {@linkplain MongoWritePreferences write preferences utility class'}
     * {@linkplain MongoWritePreferences#get() get method}.
     * </p>
     * 
     * @param writePreference the write preference to be used by the connection
     * being built
     * @return this builder
     * @throws NullPointerException if {@code writePreference} is null
     */
    Builder writePreference(MongoWritePreference writePreference);

    /**
     * Returns a new connection using the contents of this builder.
     * 
     * @return a new connection using the contents of this builder
     */
    MongoConnection build();
  }
}
