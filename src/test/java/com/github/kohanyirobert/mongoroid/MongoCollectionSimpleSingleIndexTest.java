package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocuments;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public final class MongoCollectionSimpleSingleIndexTest
    extends AbstractMongoConnectionSimpleSingleTest {

  private MongoDatabase database;
  private MongoCollection collection;

  public MongoCollectionSimpleSingleIndexTest() {}

  @Override
  @Before
  public void setUp() throws MongoException {
    super.setUp();
    database = connection.database("test");
    collection = database.collection("test");
    collection.insert(MongoInserts.builder()
        .documents(BsonDocuments.of("a", "a"))
        .documents(BsonDocuments.of("a", "b"))
        .documents(BsonDocuments.of("a", "c"))
        .build());
  }

  @Test
  public void indexSimpleField() throws MongoException {
    collection.index(MongoIndexes.builder()
        .name("a")
        .selector(BsonDocuments.of("a", Integer.valueOf(1)))
        .build());

    Assert.assertEquals(1, database.collection("system.indexes")
        .count(MongoCounts.builder()
            .selector(BsonDocuments.builder()
                .put("key.a", Integer.valueOf(1))
                .put("ns", database.name() + "." + collection.name())
                .build())
            .build()));
  }
}
