package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocuments;

import org.junit.Before;
import org.junit.Test;

// @checkstyle:off InnerAssignment
public final class MongoWritePreferenceSimpleSingleTest {

  private MongoConnection.Builder builder;

  public MongoWritePreferenceSimpleSingleTest() {}

  @Before
  public void setUp() {
    builder = MongoConnections.builder()
        .config(MongoConfigs.builder()
            .poolSize(1)
            .build())
        .seed(MongoSeeds.builder()
            // @do-not-check MagicNumber
            .address(27018)
            .address(27019)
            .address(27020)
            .build());
  }

  @Test(expected = MongoAssertionException.class)
  public void writePreferenceNonExistentGetLastErrorMode() throws MongoException {
    try (MongoConnection connection = builder.writePreference(
        MongoWritePreferences.builder()
            .w("drop")
            .build())
        .build()) {

      MongoDatabase database = connection.database("test");
      database.login("test", "test");
      MongoCollection collection = database.collection("test");
      collection.insert(MongoInserts.builder()
          .documents(BsonDocuments.of())
          .build());
      collection.remove(MongoRemoves.get());
    }
  }

  @Test
  public void writePreferenceExistingGetLastErrorMode() throws MongoException {
    try (MongoConnection connection = builder.writePreference(
        MongoWritePreferences.builder()
            .w("three")
            .build())
        .build()) {

      MongoDatabase database = connection.database("test");
      database.login("test", "test");
      MongoCollection collection = database.collection("test");
      collection.insert(MongoInserts.builder()
          .documents(BsonDocuments.of("a", "b"))
          .build());
      collection.remove(MongoRemoves.get());
    }
  }
}
