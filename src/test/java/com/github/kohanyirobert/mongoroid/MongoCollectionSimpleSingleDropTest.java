package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocuments;

import org.junit.Before;
import org.junit.Test;

public final class MongoCollectionSimpleSingleDropTest
    extends AbstractMongoConnectionSimpleSingleTest {

  private MongoDatabase database;

  public MongoCollectionSimpleSingleDropTest() {}

  @Override
  @Before
  public void setUp() throws MongoException {
    super.setUp();
    database = connection.database("test");
  }

  @Test(expected = MongoException.class)
  public void dropNonExistentCollection() throws MongoException {
    MongoCollection collection = database.collection("drop");
    collection.drop();
  }

  @Test
  public void dropExistingCollection() throws MongoException {
    // in some rare cases this test hangs, but I don't know why yet
    MongoCollection collection = database.collection("drop");
    collection.insert(MongoInserts.builder()
        .documents(BsonDocuments.of("drop", "drop"))
        .build());
    collection.drop();
  }
}
