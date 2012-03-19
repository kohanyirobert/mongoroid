package com.github.kohanyirobert.mongoroid;

import com.google.common.base.Preconditions;

import java.util.concurrent.atomic.AtomicInteger;

final class MongoMessageRequestIds {

  private static final ThreadLocal<AtomicInteger> THEAD_LOCAL_SEQUENCE =
      new ThreadLocal<AtomicInteger>() {

        @Override
        protected AtomicInteger initialValue() {
          return new AtomicInteger(Integer.MIN_VALUE);
        }
      };

  private MongoMessageRequestIds() {}

  public static int get() {
    Preconditions.checkState(THEAD_LOCAL_SEQUENCE.get().get() <= Integer.MAX_VALUE);
    return THEAD_LOCAL_SEQUENCE.get().getAndIncrement();
  }
}
