package com.github.kohanyirobert.mongoroid;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;

import java.util.concurrent.TimeUnit;

final class DefaultConfig implements MongoConfig {

  private final int connectTimeout;
  private final TimeUnit connectTimeoutUnit;
  private final int readTimeout;
  private final TimeUnit readTimeoutUnit;
  private final int refreshInterval;
  private final TimeUnit refreshIntervalUnit;
  private final int refreshTimeout;
  private final TimeUnit refreshTimeoutUnit;
  private final int retryInterval;
  private final TimeUnit retryIntervalUnit;
  private final int retryTimeout;
  private final TimeUnit retryTimeoutUnit;
  private final int poolSize;
  private final int bufferSize;

  // @do-not-check-next-line ParameterNumber
  DefaultConfig(
      int connectTimeout, TimeUnit connectTimeoutUnit,
      int readTimeout, TimeUnit readTimeoutUnit,
      int refreshInterval, TimeUnit refreshIntervalUnit,
      int refreshTimeout, TimeUnit refreshTimeoutUnit,
      int retryInterval, TimeUnit retryIntervalUnit,
      int retryTimeout, TimeUnit retryTimeoutUnit,
      int poolSize,
      int bufferSize) {
    this.connectTimeout = connectTimeout;
    this.connectTimeoutUnit = connectTimeoutUnit;

    this.readTimeout = readTimeout;
    this.readTimeoutUnit = readTimeoutUnit;

    this.refreshInterval = refreshInterval;
    this.refreshIntervalUnit = refreshIntervalUnit;

    this.refreshTimeout = refreshTimeout;
    this.refreshTimeoutUnit = refreshTimeoutUnit;

    this.retryInterval = retryInterval;
    this.retryIntervalUnit = retryIntervalUnit;

    this.retryTimeout = retryTimeout;
    this.retryTimeoutUnit = retryTimeoutUnit;

    this.poolSize = poolSize;
    this.bufferSize = bufferSize;
  }

  @Override
  public int connectTimeout(TimeUnit connectTimeoutUnit) {
    Preconditions.checkNotNull(connectTimeoutUnit);
    return Ints.checkedCast(connectTimeoutUnit.convert(connectTimeout, this.connectTimeoutUnit));
  }

  @Override
  public int readTimeout(TimeUnit readTimeoutUnit) {
    Preconditions.checkNotNull(readTimeoutUnit);
    return Ints.checkedCast(readTimeoutUnit.convert(readTimeout, this.readTimeoutUnit));
  }

  @Override
  public int refreshInterval(TimeUnit refreshIntervalUnit) {
    Preconditions.checkNotNull(refreshIntervalUnit);
    return Ints.checkedCast(refreshIntervalUnit.convert(refreshInterval, this.refreshIntervalUnit));
  }

  @Override
  public int refreshTimeout(TimeUnit refreshTimeoutUnit) {
    Preconditions.checkNotNull(refreshTimeoutUnit);
    return Ints.checkedCast(refreshTimeoutUnit.convert(refreshTimeout, this.refreshTimeoutUnit));
  }

  @Override
  public int retryInterval(TimeUnit retryIntervalUnit) {
    Preconditions.checkNotNull(retryIntervalUnit);
    return Ints.checkedCast(retryIntervalUnit.convert(retryInterval, this.retryIntervalUnit));
  }

  @Override
  public int retryTimeout(TimeUnit retryTimeoutUnit) {
    Preconditions.checkNotNull(retryTimeoutUnit);
    return Ints.checkedCast(retryTimeoutUnit.convert(retryTimeout, this.retryTimeoutUnit));
  }

  @Override
  public int bufferSize() {
    return bufferSize;
  }

  @Override
  public int poolSize() {
    return poolSize;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(
        Integer.valueOf(connectTimeout(TimeUnit.MILLISECONDS)),
        Integer.valueOf(readTimeout(TimeUnit.MILLISECONDS)),
        Integer.valueOf(refreshInterval(TimeUnit.MILLISECONDS)),
        Integer.valueOf(refreshTimeout(TimeUnit.MILLISECONDS)),
        Integer.valueOf(retryInterval(TimeUnit.MILLISECONDS)),
        Integer.valueOf(retryTimeout(TimeUnit.MILLISECONDS)),
        Integer.valueOf(poolSize()),
        Integer.valueOf(bufferSize()));
  }

  // @do-not-check-next-line CyclomaticComplexity
  @Override
  public boolean equals(Object object) {
    if (object instanceof MongoConfig) {
      MongoConfig other = (MongoConfig) object;
      return connectTimeout(TimeUnit.MILLISECONDS) == other.connectTimeout(TimeUnit.MILLISECONDS)
          && readTimeout(TimeUnit.MILLISECONDS) == other.readTimeout(TimeUnit.MILLISECONDS)
          && refreshInterval(TimeUnit.MILLISECONDS) == other.refreshInterval(TimeUnit.MILLISECONDS)
          && refreshTimeout(TimeUnit.MILLISECONDS) == other.refreshTimeout(TimeUnit.MILLISECONDS)
          && refreshInterval(TimeUnit.MILLISECONDS) == other.refreshInterval(TimeUnit.MILLISECONDS)
          && retryTimeout(TimeUnit.MILLISECONDS) == other.retryTimeout(TimeUnit.MILLISECONDS)
          && poolSize() == other.poolSize()
          && bufferSize() == other.bufferSize();
    }
    return false;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(MongoConfig.class)
        .add("connectTimeout", connectTimeout)
        .add("connectTimeoutUnit", connectTimeoutUnit)
        .add("readTimeout", readTimeout)
        .add("readTimeoutUnit", readTimeoutUnit)
        .add("refreshInterval", refreshInterval)
        .add("refreshIntervalUnit", refreshIntervalUnit)
        .add("refreshTimeout", refreshTimeout)
        .add("refreshTimeoutUnit", refreshTimeoutUnit)
        .add("retryInterval", retryInterval)
        .add("retryIntervalUnit", retryIntervalUnit)
        .add("retryTimeout", retryTimeout)
        .add("retryTimeoutUnit", retryTimeoutUnit)
        .add("poolSize", poolSize())
        .add("bufferSize", bufferSize())
        .toString();
  }
}
