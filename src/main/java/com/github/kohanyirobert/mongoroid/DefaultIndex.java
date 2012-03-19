package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

final class DefaultIndex implements MongoIndex {

  private final BsonDocument selector;
  private final String name;
  private final boolean sparse;
  private final boolean unique;
  private final boolean dropDuplicates;
  private final boolean background;

  // @do-not-check ParameterNumber
  DefaultIndex(
      BsonDocument selector,
      String name,
      boolean unique,
      boolean sparse,
      boolean dropDuplicates,
      boolean background) {
    this.selector = selector;
    this.name = name;
    this.unique = unique;
    this.sparse = sparse;
    this.dropDuplicates = dropDuplicates;
    this.background = background;
  }

  @Override
  public BsonDocument selector() {
    return selector;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public boolean sparse() {
    return sparse;
  }

  @Override
  public boolean unique() {
    return unique;
  }

  @Override
  public boolean dropDuplicates() {
    return dropDuplicates;
  }

  @Override
  public boolean background() {
    return background;
  }
}
