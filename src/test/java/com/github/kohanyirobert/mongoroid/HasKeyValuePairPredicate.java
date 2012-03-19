package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

import com.google.common.base.Predicate;

final class HasKeyValuePairPredicate implements Predicate<BsonDocument> {

  private final String key;
  private final Object value;

  HasKeyValuePairPredicate(String key, Object value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public boolean apply(BsonDocument input) {
    return input.containsKey(key) && input.get(key).equals(value);
  }
}
