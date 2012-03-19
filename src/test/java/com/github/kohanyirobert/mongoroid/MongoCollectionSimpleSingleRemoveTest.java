package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;
import com.github.kohanyirobert.ebson.BsonDocuments;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public final class MongoCollectionSimpleSingleRemoveTest
    extends AbstractMongoConnectionSimpleSingleTest {

  private MongoDatabase database;
  private MongoCollection collection;

  public MongoCollectionSimpleSingleRemoveTest() {}

  @Override
  @Before
  public void setUp() throws MongoException {
    super.setUp();
    database = connection.database("test");
    collection = database.collection("test");
    collection.insert(MongoInserts.builder()
        .documents(BsonDocuments.of("a", "a"))
        .documents(BsonDocuments.of("b", "b"))
        .documents(BsonDocuments.of("c", "c"))
        .build());
  }

  @Test
  public void removeAllFromNonEmptyCollection() throws MongoException {
    collection.remove(MongoRemoves.get());
    Assert.assertEquals(0, collection.count(MongoCounts.get()));
  }

  @Test
  public void removeAnyFromNonEmptyCollection() throws MongoException {
    collection.remove(MongoRemoves.builder()
        .single(true)
        .build());
    Assert.assertEquals(2, collection.count(MongoCounts.get()));
  }

  @Test
  public void removeSelectedFromNonEmptyCollection() throws MongoException {
    collection.remove(MongoRemoves.builder()
        .selector(BsonDocuments.of("a", "a"))
        .build());

    List<BsonDocument> documents =
        Lists.newArrayList(collection.find(MongoFinds.get()));

    Assert.assertEquals(2, documents.size());
    Assert.assertTrue(!Iterables.any(documents, new HasKeyValuePairPredicate("a", "a")));
  }

  @Test
  public void removeAnyFromEmptyCollection() throws MongoException {
    collection.remove(MongoRemoves.get());
    collection.remove(MongoRemoves.builder()
        .selector(BsonDocuments.of("x", "x"))
        .build());
  }
}
