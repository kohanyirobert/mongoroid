package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

// @checkstyle:off Javadoc
public interface MongoIndex {

  BsonDocument selector();

  String name();

  boolean sparse();

  boolean unique();

  boolean dropDuplicates();

  boolean background();

  interface Builder {

    Builder selector(BsonDocument selector);

    Builder name(String name);

    Builder sparse(boolean sparse);

    Builder unique(boolean unique);

    Builder dropDuplicates(boolean dropDuplicates);

    Builder background(boolean background);

    MongoIndex build();
  }
}
