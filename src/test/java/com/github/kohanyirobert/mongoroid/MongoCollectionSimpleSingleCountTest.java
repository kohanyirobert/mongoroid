package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocuments;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public final class MongoCollectionSimpleSingleCountTest
    extends AbstractMongoConnectionSimpleSingleTest {

  private MongoDatabase database;
  private MongoCollection collection;

  public MongoCollectionSimpleSingleCountTest() {}

  @Override
  @Before
  public void setUp() throws MongoException {
    super.setUp();
    database = connection.database("test");
    collection = database.collection("test");
  }

  @Test
  public void countEmpty() throws MongoException {
    Assert.assertEquals(0, collection.count(MongoCounts.get()));
  }

  @Test
  public void countNonEmpty() throws MongoException {
    int expected = 42;
    for (int i = 0; i < expected; i++)
      collection.insert(MongoInserts.builder()
          .documents(BsonDocuments.of("i", Integer.valueOf(i)))
          .build());
    Assert.assertEquals(expected, collection.count(MongoCounts.get()));
  }
}
