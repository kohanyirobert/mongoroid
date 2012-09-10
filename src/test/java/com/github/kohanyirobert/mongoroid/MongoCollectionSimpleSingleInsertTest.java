package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;
import com.github.kohanyirobert.ebson.BsonDocuments;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public final class MongoCollectionSimpleSingleInsertTest
    extends AbstractMongoConnectionSimpleSingleTest {

  private MongoDatabase database;
  private MongoCollection collection;

  public MongoCollectionSimpleSingleInsertTest() {}

  @Override
  @Before
  public void setUp() throws MongoException {
    super.setUp();
    database = connection.database("test");
    collection = database.collection("test");
  }

  // @do-not-check-next-line MethodLength
  @Test
  public void insertHandfulOfSimpleDocuments() throws MongoException {
    Iterable<BsonDocument> documents = ImmutableList.<BsonDocument>builder()
        .add(BsonDocuments.of("a", null))
        .add(BsonDocuments.of("b", Integer.valueOf(Integer.MAX_VALUE)))
        .add(BsonDocuments.of("c", Double.valueOf(Double.MAX_VALUE)))
        .add(BsonDocuments.of("d", BsonDocuments.of()))
        .add(BsonDocuments.of("e", "So long, and thanks for all the fish!"))
        .add(BsonDocuments.of("f", new byte[] {42}))
        .build();

    collection.insert(MongoInserts.builder()
        .documents(documents)
        .build());

    // @do-not-check-next-line InnerAssignment
    try (final MongoCursor cursor = collection.find(MongoFinds.get())) {
      while (cursor.hasNext()) {
        final BsonDocument document = cursor.next();
        Iterables.transform(documents, new Function<BsonDocument, Void>() {

          @Override
          public Void apply(BsonDocument input) {
            Assert.assertTrue(document.containsKey("_id"));
            for (String key : document.keySet())
              Assert.assertEquals(input.get(key), document.get(key));
            return null;
          }
        });
      }
    }
  }
}
