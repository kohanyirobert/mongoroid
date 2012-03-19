package com.github.kohanyirobert.mongoroid;

import java.util.concurrent.TimeUnit;

/**
 * Representation of a configuration used by {@linkplain MongoConnection
 * connections}. (It is also a loose interpretation of a <a
 * href="http://www.mongodb.org/display/DOCS/Connections">MongoDB URI</a>.)
 * <p>
 * <b>Notes:</b> use the {@linkplain MongoConfigs configs utility class} to
 * create new configs.
 * </p>
 */
public interface MongoConfig {

  /**
   * Converts this config's connect timeout using {@code connectTimeoutUnit} and
   * returns the result.
   * 
   * @param connectTimeoutUnit the time unit used to convert this config's
   * connect timeout
   * @return this config's connect timeout converted to
   * {@code connectTimeoutUnit}
   * @throws NullPointerException if {@code connectTimeoutUnit} is null
   */
  int connectTimeout(TimeUnit connectTimeoutUnit);

  /**
   * Converts this config's read timeout using {@code readTimeoutUnit} and
   * returns the result.
   * 
   * @param readTimeoutUnit the time unit used to convert this config's read
   * timeout
   * @return this config's read timeout converted to {@code readTimeoutUnit}
   * @throws NullPointerException if {@code readTimeoutUnit} is null
   */
  int readTimeout(TimeUnit readTimeoutUnit);

  /**
   * Converts this config's refresh interval using {@code refreshIntervalUnit}
   * and returns the result.
   * 
   * @param refreshIntervalUnit the time unit used to convert this config's
   * refresh interval
   * @return this config's refresh interval converted to
   * {@code refreshIntervalUnit}
   * @throws NullPointerException if {@code refreshIntervalUnit} is null
   */
  int refreshInterval(TimeUnit refreshIntervalUnit);

  /**
   * Converts this config's refresh timeout using {@code refreshTimeoutUnit} and
   * returns the result.
   * 
   * @param refreshTimeoutUnit the time unit used to convert this config's
   * refresh timeout
   * @return this config's refresh timeout converted to
   * {@code refreshTimeoutUnit}
   * @throws NullPointerException if {@code refreshTimeoutUnit} is null
   */
  int refreshTimeout(TimeUnit refreshTimeoutUnit);

  /**
   * Converts this config's retry interval using {@code retryIntervalUnit} and
   * returns the result.
   * 
   * @param retryIntervalUnit the time unit used to convert this config's retry
   * interval
   * @return this config's retry interval converted to {@code retryIntervalUnit}
   * @throws NullPointerException if {@code retryIntervalUnit} is null
   */
  int retryInterval(TimeUnit retryIntervalUnit);

  /**
   * Converts this config's retry timeout using {@code retryTimeoutUnit} and
   * returns the result.
   * 
   * @param retryTimeoutUnit the time unit used to convert this config's retry
   * timeout
   * @return this config's retry timeout converted to {@code retryTimeoutUnit}
   * @throws NullPointerException if {@code retryTimeoutUnit} is null
   */
  int retryTimeout(TimeUnit retryTimeoutUnit);

  /**
   * Return this config's pool size.
   * 
   * @return this config's pool size
   */
  int poolSize();

  /**
   * Return this config's buffer size in bytes.
   * 
   * @return this config's pool size in bytes
   */
  int bufferSize();

  /**
   * {@linkplain MongoConfig Config} builder.
   * <p>
   * <b>Notes:</b>
   * <ul>
   * <li>Calling a builder's {@linkplain #build} method <em>does not clear its
   * state</em>, however subsequent invocations of it returns new connections.</li>
   * <li>Config builders can be acquired via the {@linkplain MongoConfigs
   * configs utility class}.</li>
   * </ul>
   * </p>
   */
  interface Builder {

    /**
     * Sets {@code connectTimeout} and {@code connectTimeoutUnit} as the ones to
     * be used by the config being built.
     * <p>
     * <b>Note:</b> defaults to 1 seconds.
     * </p>
     * 
     * @param connectTimeout the connect timeout to be used by the config being
     * built
     * @param connectTimeoutUnit the connect timeout unit to be used by the
     * config being built
     * @return this builder
     * @throws IllegalArgumentException if {@code connectTimeout} is less than
     * zero
     * @throws NullPointerException if {@code connectTimeoutUnit} is null
     */
    Builder connectTimeout(int connectTimeout, TimeUnit connectTimeoutUnit);

    /**
     * Sets {@code readTimeout} and {@code readTimeoutUnit} as the ones to
     * be used by the config being built.
     * <p>
     * <b>Note:</b> defaults to 10 seconds.
     * </p>
     * 
     * @param readTimeout the read timeout to be used by the config being built
     * @param readTimeoutUnit the read timeout unit to be used by the config
     * being built
     * @return this builder
     * @throws IllegalArgumentException if {@code readTimeout} is less than
     * zero
     * @throws NullPointerException if {@code readTimeoutUnit} is null
     */
    Builder readTimeout(int readTimeout, TimeUnit readTimeoutUnit);

    /**
     * Sets {@code refreshInterval} and {@code refreshIntervalUnit} as the ones
     * to
     * be used by the config being built.
     * <p>
     * <b>Note:</b> defaults to 5 seconds.
     * </p>
     * 
     * @param refreshInterval the refresh interval to be used by the config
     * being built
     * @param refreshIntervalUnit the refresh interval unit to be used by the
     * config being built
     * @return this builder
     * @throws IllegalArgumentException if {@code refreshInterval} is less than
     * zero
     * @throws NullPointerException if {@code refreshIntervalUnit} is null
     */
    Builder refreshInterval(int refreshInterval, TimeUnit refreshIntervalUnit);

    /**
     * Sets {@code refreshTimeout} and {@code refreshTimeoutUnit} as the ones to
     * be used by the config being built.
     * <p>
     * <b>Note:</b> defaults to 60 seconds.
     * </p>
     * 
     * @param refreshTimeout the refresh timeout to be used by the config being
     * built
     * @param refreshTimeoutUnit the refresh timeout unit to be used by the
     * config being built
     * @return this builder
     * @throws IllegalArgumentException if {@code refreshTimeout} is less than
     * zero
     * @throws NullPointerException if {@code refreshTimeoutUnit} is null
     */
    Builder refreshTimeout(int refreshTimeout, TimeUnit refreshTimeoutUnit);

    /**
     * Sets {@code retryInterval} and {@code retryIntervalUnit} as the ones to
     * be used by the config being built.
     * <p>
     * <b>Note:</b> defaults to 10 seconds.
     * </p>
     * 
     * @param retryInterval the retry interval to be used by the config being
     * built
     * @param retryIntervalUnit the retry interval unit to be used by the config
     * being built
     * @return this builder
     * @throws IllegalArgumentException if {@code retryInterval} is less than
     * zero
     * @throws NullPointerException if {@code retryIntervalUnit} is null
     */
    Builder retryInterval(int retryInterval, TimeUnit retryIntervalUnit);

    /**
     * Sets {@code retryTimeout} and {@code retryTimeoutUnit} as the ones to
     * be used by the config being built.
     * <p>
     * <b>Note:</b> defaults to 60 seconds.
     * </p>
     * 
     * @param retryTimeout the retry timeout to be used by the config being
     * built
     * @param retryTimeoutUnit the retry timeout unit to be used by the config
     * being built
     * @return this builder
     * @throws IllegalArgumentException if {@code retryTimeout} is less than
     * zero
     * @throws NullPointerException if {@code retryTimeoutUnit} is null
     */
    Builder retryTimeout(int retryTimeout, TimeUnit retryTimeoutUnit);

    /**
     * Sets {@code poolSize} as the one to be used by the config being built.
     * <p>
     * <b>Note:</b> defaults to 10.
     * </p>
     * 
     * @param poolSize the pool size to be used by the config being built
     * @return this builder
     * @throws IllegalArgumentException if {@code poolSize} is less than
     * zero
     */
    Builder poolSize(int poolSize);

    /**
     * Sets {@code bufferSize} as the one to be used by the config being built.
     * <p>
     * <b>Note:</b> defaults to 16 megabytes.
     * </p>
     * 
     * @param bufferSize the buffer size in bytes to be used by the config being
     * built
     * @return this builder
     * @throws IllegalArgumentException if {@code bufferSize} is less than
     * zero
     */
    Builder bufferSize(int bufferSize);

    /**
     * Returns a new config using the contents of this builder.
     * 
     * @return a new config using the contents of this builder
     */
    MongoConfig build();
  }
}
