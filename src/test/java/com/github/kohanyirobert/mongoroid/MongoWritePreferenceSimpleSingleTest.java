package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocuments;

import org.junit.Before;
import org.junit.Test;

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
            // @do-not-check-next-3-lines MagicNumber
            .address(27018)
            .address(27019)
            .address(27020)
            .build());
  }

  @Test(expected = MongoException.class)
  public void writePreferenceNonExistentGetLastErrorMode() throws MongoException {
    // @do-not-check-next-line InnerAssignment
    try (MongoConnection connection = builder.writePreference(
        MongoWritePreferences.builder()
            .w("drop")
            .build())
        .build()) {

      // getLastError needs admin privileges so unfortunately we need to
      // login as admin at this point (the other test behaves like this too)
      connection.database("admin").login("admin", "admin");

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
    // @do-not-check-next-line InnerAssignment
    try (MongoConnection connection = builder.writePreference(
        MongoWritePreferences.builder()
            .w("three")
            .build())
        .build()) {

      connection.database("admin").login("admin", "admin");

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
