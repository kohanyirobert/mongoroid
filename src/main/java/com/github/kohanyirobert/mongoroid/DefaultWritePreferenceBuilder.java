package com.github.kohanyirobert.mongoroid;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import java.util.concurrent.TimeUnit;

final class DefaultWritePreferenceBuilder implements MongoWritePreference.Builder {

  private String w = "1";
  private int wTimeout;
  private TimeUnit wTimeoutUnit = TimeUnit.MILLISECONDS;
  private boolean fsync;
  private boolean j;
  private boolean journal;

  DefaultWritePreferenceBuilder() {}

  @Override
  public MongoWritePreference.Builder w(String w) {
    Preconditions.checkNotNull(w);
    this.w = w;
    return this;
  }

  @Override
  public MongoWritePreference.Builder wTimeout(int wTimeout, TimeUnit wTimeoutUnit) {
    Preconditions.checkArgument(wTimeout >= 0L);
    Preconditions.checkNotNull(wTimeoutUnit);
    this.wTimeout = wTimeout;
    this.wTimeoutUnit = wTimeoutUnit;
    return this;
  }

  @Override
  public MongoWritePreference.Builder fsync(boolean fsync) {
    this.fsync = fsync;
    return this;
  }

  @Override
  public MongoWritePreference.Builder j(boolean j) {
    this.j = j;
    return this;
  }

  @Override
  public MongoWritePreference.Builder journal(boolean journal) {
    this.journal = journal;
    return this;
  }

  @Override
  public MongoWritePreference build() {
    return new DefaultWritePreference(
        w,
        wTimeout, wTimeoutUnit,
        fsync,
        j,
        journal);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(MongoWritePreference.Builder.class).toString();
  }
}
