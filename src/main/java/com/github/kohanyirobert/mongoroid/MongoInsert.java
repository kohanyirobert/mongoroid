package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

import java.util.List;

// @checkstyle:off Javadoc
public interface MongoInsert {

  List<BsonDocument> documents();

  boolean continueOnError();

  interface Builder {

    Builder documents(BsonDocument... documents);

    Builder documents(Iterable<BsonDocument> documents);

    Builder continueOnError(boolean continueOnError);

    MongoInsert build();
  }
}
