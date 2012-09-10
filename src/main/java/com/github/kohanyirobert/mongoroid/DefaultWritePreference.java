package com.github.kohanyirobert.mongoroid;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;

import java.util.concurrent.TimeUnit;

final class DefaultWritePreference implements MongoWritePreference {

  private final String w;
  private final int wTimeout;
  private final TimeUnit wTimeoutUnit;
  private final boolean fsync;
  private final boolean j;
  private final boolean journal;

  // @do-not-check-next-line ParameterNumber
  DefaultWritePreference(
      String w,
      int wTimeout, TimeUnit wTimeoutUnit,
      boolean fsync,
      boolean j,
      boolean journal) {
    this.w = w;
    this.wTimeout = wTimeout;
    this.wTimeoutUnit = wTimeoutUnit;
    this.fsync = fsync;
    this.j = j;
    this.journal = journal;
  }

  @Override
  public String w() {
    return w;
  }

  @Override
  public int wTimeout(TimeUnit wTimeoutUnit) {
    Preconditions.checkNotNull(wTimeoutUnit);
    return Ints.checkedCast(wTimeoutUnit.convert(wTimeout, this.wTimeoutUnit));
  }

  @Override
  public boolean fsync() {
    return fsync;
  }

  @Override
  public boolean j() {
    return j;
  }

  @Override
  public boolean journal() {
    return journal;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(
        w(),
        Integer.valueOf(wTimeout(TimeUnit.MILLISECONDS)),
        Boolean.valueOf(fsync()),
        Boolean.valueOf(j()),
        Boolean.valueOf(journal()));
  }

  // @do-not-check-next-line CyclomaticComplexity
  @Override
  public boolean equals(Object object) {
    if (object instanceof MongoWritePreference) {
      MongoWritePreference other = (MongoWritePreference) object;
      return Objects.equal(w(), other.w())
          && wTimeout(TimeUnit.MILLISECONDS) == other.wTimeout(TimeUnit.MILLISECONDS)
          && fsync() == other.fsync()
          && j() == other.j()
          && journal() == journal();
    }
    return false;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(MongoWritePreference.class)
        .add("w", w())
        .add("wTimeout", wTimeout)
        .add("wTimeoutUnit", wTimeoutUnit)
        .add("fsync", fsync())
        .add("j", j())
        .add("journal", journal())
        .toString();
  }
}
