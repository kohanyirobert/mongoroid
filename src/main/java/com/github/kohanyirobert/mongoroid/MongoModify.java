package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

// @checkstyle:off Javadoc
public interface MongoModify {

  BsonDocument selector();

  BsonDocument document();

  BsonDocument fields();

  BsonDocument sort();

  boolean remove();

  boolean modified();

  boolean upsert();

  interface Builder {

    Builder selector(BsonDocument selector);

    Builder document(BsonDocument document);

    Builder fields(BsonDocument fields);

    Builder sort(BsonDocument sort);

    Builder remove(boolean remove);

    Builder modified(boolean modified);

    Builder upsert(boolean upsert);

    MongoModify build();
  }
}
