package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;
import com.github.kohanyirobert.ebson.BsonDocuments;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public final class MongoCollectionSimpleSingleUpdateTest
    extends AbstractMongoConnectionSimpleSingleTest {

  private MongoDatabase database;
  private MongoCollection collection;
  private List<BsonDocument> documents;

  public MongoCollectionSimpleSingleUpdateTest() {}

  @Override
  @Before
  public void setUp() throws MongoException {
    super.setUp();
    database = connection.database("test");
    collection = database.collection("test");
    collection.insert(MongoInserts.builder()
        .documents(BsonDocuments.of("a", "a"))
        .documents(BsonDocuments.of("a", "a"))
        .build());
  }

  @Test
  public void updateSingleDocument() throws MongoException {
    collection.update(MongoUpdates.builder()
        .selector(BsonDocuments.of())
        .document(BsonDocuments.of("a", "b"))
        .build());

    documents = Lists.newArrayList(collection.find(MongoFinds.get()));

    Assert.assertEquals(2, documents.size());
    Assert.assertTrue(has("a", "a") && has("a", "b"));
  }

  @Test
  public void updateMultipleDocuments() throws MongoException {
    collection.update(MongoUpdates.builder()
        .selector(BsonDocuments.of())
        .document(BsonDocuments.of("$set", BsonDocuments.of("a", "b")))
        .multi(true)
        .build());

    documents = Lists.newArrayList(collection.find(MongoFinds.get()));

    Assert.assertEquals(2, documents.size());
    Assert.assertTrue(!has("a", "a"));
  }

  @Test
  public void updateExistingDocumentWithUpsert() throws MongoException {
    collection.update(MongoUpdates.builder()
        .selector(BsonDocuments.of())
        .document(BsonDocuments.of("a", "b"))
        .upsert(true)
        .build());

    documents = Lists.newArrayList(collection.find(MongoFinds.get()));

    Assert.assertEquals(2, documents.size());
    Assert.assertTrue(Iterables.any(documents,
        new HasKeyValuePairPredicate("a", "a"))
        && Iterables.any(documents,
            new HasKeyValuePairPredicate("a", "b")));
  }

  @Test
  public void updateNonExistentDocumentWithUpsert() throws MongoException {
    collection.update(MongoUpdates.builder()
        .selector(BsonDocuments.of("b", "b"))
        .document(BsonDocuments.of("a", "b"))
        .upsert(true)
        .build());

    documents = Lists.newArrayList(collection.find(MongoFinds.get()));

    Assert.assertEquals(3, documents.size());
    Assert.assertTrue(has("a", "b"));
  }

  private boolean has(String key, Object value) {
    return Iterables.any(documents, new HasKeyValuePairPredicate(key, value));
  }
}
