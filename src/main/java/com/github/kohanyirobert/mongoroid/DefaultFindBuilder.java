package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;
import com.github.kohanyirobert.ebson.BsonDocuments;
import com.github.kohanyirobert.mongoroid.MongoFind.Builder;

import com.google.common.base.Preconditions;

final class DefaultFindBuilder implements MongoFind.Builder {

  private BsonDocument selector = BsonDocuments.of();
  private BsonDocument fields = BsonDocuments.of();
  private int skip;
  private int limit;
  private BsonDocument sort = BsonDocuments.of();
  private boolean explain;
  private boolean snapshot;
  private BsonDocument hint = BsonDocuments.of();
  private boolean close;

  DefaultFindBuilder() {}

  @Override
  public Builder selector(BsonDocument selector) {
    Preconditions.checkNotNull(selector);
    this.selector = selector;
    return this;
  }

  @Override
  public Builder fields(BsonDocument fields) {
    Preconditions.checkNotNull(fields);
    this.fields = fields;
    return this;
  }

  @Override
  public Builder skip(int skip) {
    Preconditions.checkArgument(skip >= 0);
    this.skip = skip;
    return this;
  }

  @Override
  public Builder limit(int limit) {
    Preconditions.checkArgument(limit >= 0);
    this.limit = limit;
    return this;
  }

  @Override
  public Builder sort(BsonDocument sort) {
    Preconditions.checkNotNull(sort);
    Preconditions.checkState(!snapshot);
    this.sort = sort;
    return this;
  }

  @Override
  public Builder explain(boolean explain) {
    this.explain = explain;
    return this;
  }

  @Override
  public Builder snapshot(boolean snapshot) {
    Preconditions.checkArgument(sort.isEmpty());
    this.snapshot = snapshot;
    return this;
  }

  @Override
  public Builder hint(BsonDocument hint) {
    Preconditions.checkNotNull(hint);
    this.hint = hint;
    return this;
  }

  @Override
  public Builder close(boolean close) {
    Preconditions.checkArgument(sort.isEmpty());
    this.close = close;
    return this;
  }

  @Override
  public MongoFind build() {
    return new DefaultFind(
        selector,
        fields,
        skip,
        limit,
        sort,
        explain,
        snapshot,
        hint,
        close);
  }
}
