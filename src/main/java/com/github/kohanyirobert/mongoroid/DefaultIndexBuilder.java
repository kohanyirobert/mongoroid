package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;
import com.github.kohanyirobert.ebson.BsonDocuments;
import com.github.kohanyirobert.mongoroid.MongoIndex.Builder;

import com.google.common.base.Preconditions;


final class DefaultIndexBuilder implements MongoIndex.Builder {

  private BsonDocument selector = BsonDocuments.of();
  private String name;
  private boolean sparse;
  private boolean unique;
  private boolean dropDuplicates;
  private boolean background;

  DefaultIndexBuilder() {}

  @Override
  public Builder selector(BsonDocument selector) {
    Preconditions.checkNotNull(selector);
    this.selector = selector;
    return this;
  }

  @Override
  public Builder name(String name) {
    Preconditions.checkNotNull(name);
    this.name = name;
    return this;
  }

  @Override
  public Builder sparse(boolean sparse) {
    this.sparse = sparse;
    return this;
  }

  @Override
  public Builder unique(boolean unique) {
    this.unique = unique;
    return this;
  }

  @Override
  public Builder dropDuplicates(boolean dropDuplicates) {
    this.dropDuplicates = dropDuplicates;
    return this;
  }

  @Override
  public Builder background(boolean background) {
    this.background = background;
    return this;
  }

  @Override
  public MongoIndex build() {
    return new DefaultIndex(
        selector,
        name,
        unique,
        sparse,
        dropDuplicates,
        background);
  }
}
