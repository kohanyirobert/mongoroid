package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.mongoroid.MongoConfig.Builder;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import java.util.concurrent.TimeUnit;

final class DefaultConfigBuilder implements MongoConfig.Builder {

  // @checkstyle:off MagicNumber

  private int connectTimeout = 1;
  private TimeUnit connectTimeoutUnit = TimeUnit.SECONDS;

  private int readTimeout = 10;
  private TimeUnit readTimeoutUnit = TimeUnit.SECONDS;

  private int refreshInterval = 5;
  private TimeUnit refreshIntervalUnit = TimeUnit.SECONDS;

  private int refreshTimeout = 60;
  private TimeUnit refreshTimeoutUnit = TimeUnit.SECONDS;

  private int retryInterval = 10;
  private TimeUnit retryIntervalUnit = TimeUnit.SECONDS;

  private int retryTimeout = 60;
  private TimeUnit retryTimeoutUnit = TimeUnit.SECONDS;

  private int poolSize = 10;
  private int bufferSize = 1024 * 1024 * 16;

  // @checkstyle:on MagicNumber

  DefaultConfigBuilder() {}

  @Override
  public MongoConfig.Builder connectTimeout(int connectTimeout, TimeUnit connectTimeoutUnit) {
    Preconditions.checkArgument(connectTimeout >= 0);
    Preconditions.checkNotNull(connectTimeoutUnit);
    this.connectTimeout = connectTimeout;
    this.connectTimeoutUnit = connectTimeoutUnit;
    return this;
  }

  @Override
  public MongoConfig.Builder readTimeout(int readTimeout, TimeUnit readTimeoutUnit) {
    Preconditions.checkArgument(readTimeout >= 0);
    Preconditions.checkNotNull(readTimeoutUnit);
    this.readTimeout = readTimeout;
    this.readTimeoutUnit = readTimeoutUnit;
    return this;
  }

  @Override
  public Builder refreshInterval(int refreshInterval, TimeUnit refreshIntervalUnit) {
    Preconditions.checkArgument(refreshInterval >= 0);
    Preconditions.checkNotNull(refreshIntervalUnit);
    this.refreshInterval = refreshInterval;
    this.refreshIntervalUnit = refreshIntervalUnit;
    return this;
  }

  @Override
  public MongoConfig.Builder refreshTimeout(int refreshTimeout, TimeUnit refreshTimeoutUnit) {
    Preconditions.checkArgument(refreshTimeout >= 0);
    Preconditions.checkNotNull(refreshTimeoutUnit);
    this.refreshTimeout = refreshTimeout;
    this.refreshTimeoutUnit = refreshTimeoutUnit;
    return this;
  }

  @Override
  public Builder retryInterval(int retryInterval, TimeUnit retryIntervalUnit) {
    Preconditions.checkArgument(retryInterval >= 0);
    Preconditions.checkNotNull(retryIntervalUnit);
    this.retryInterval = retryInterval;
    this.retryIntervalUnit = retryIntervalUnit;
    return this;
  }

  @Override
  public MongoConfig.Builder retryTimeout(int retryTimeout, TimeUnit retryTimeoutUnit) {
    Preconditions.checkArgument(retryTimeout >= 0);
    Preconditions.checkNotNull(retryTimeoutUnit);
    this.retryTimeout = retryTimeout;
    this.retryTimeoutUnit = retryTimeoutUnit;
    return this;
  }

  @Override
  public MongoConfig.Builder poolSize(int poolSize) {
    Preconditions.checkArgument(poolSize >= 0);
    this.poolSize = poolSize;
    return this;
  }

  @Override
  public MongoConfig.Builder bufferSize(int bufferSize) {
    Preconditions.checkArgument(bufferSize >= 0);
    this.bufferSize = bufferSize;
    return this;
  }

  @Override
  public MongoConfig build() {
    return new DefaultConfig(
        connectTimeout,
        connectTimeoutUnit,
        readTimeout, readTimeoutUnit,
        refreshInterval, refreshIntervalUnit,
        refreshTimeout, refreshTimeoutUnit,
        retryInterval, retryIntervalUnit,
        retryTimeout, retryTimeoutUnit,
        poolSize, bufferSize);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(MongoConfig.Builder.class).toString();
  }
}
