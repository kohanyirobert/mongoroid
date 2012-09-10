package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;
import com.github.kohanyirobert.ebson.BsonDocuments;

import com.google.common.net.HostAndPort;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

// @checkstyle:off MagicNumber|InnerAssignment
public final class MongoReadPreferenceSimpleSingleTest {

  private MongoConnection.Builder builder;

  public MongoReadPreferenceSimpleSingleTest() {}

  @Before
  public void setUp() {
    builder = MongoConnections.builder()
        .config(MongoConfigs.builder()
            .poolSize(1)
            .refreshInterval(1, TimeUnit.SECONDS)
            .refreshTimeout(5, TimeUnit.SECONDS)
            .retryInterval(1, TimeUnit.SECONDS)
            .retryTimeout(5, TimeUnit.SECONDS)
            .build())
        .seed(MongoSeeds.builder()
            .address(27018)
            .address(27019)
            .address(27020)
            .build());
  }

  @Test(expected = MongoRefreshTimeoutException.class)
  public void readPreferenceNoPrimaryOrSecondaryWithoutTags() throws MongoException {
    try (MongoConnection connection = builder.readPreference(
        MongoReadPreferences.builder()
            .primary(false)
            .secondary(false)
            .build())
        .build()) {
      connection.database("admin").command("isMaster");
    }
  }

  @Test
  public void readPreferencePrimaryOnly() throws MongoException {
    try (MongoConnection connection = builder.readPreference(
        MongoReadPreferences.builder()
            .primary(true)
            .secondary(false)
            .build())
        .build()) {
      for (int i = 0; i < 100; i++)
        Assert.assertTrue(connection.database("admin").command("isMaster")
            .get("ismaster", Boolean.class).booleanValue());
    }
  }

  @Test
  public void readPreferenceSecondaryOnly() throws MongoException {
    try (MongoConnection connection = builder.readPreference(
        MongoReadPreferences.builder()
            .primary(false)
            .secondary(true)
            .build())
        .build()) {
      for (int i = 0; i < 100; i++)
        Assert.assertTrue(connection.database("admin").command("isMaster")
            .get("secondary", Boolean.class).booleanValue());
    }
  }

  @Test
  public void readPreferenceWithSingleTag() throws MongoException {
    try (MongoConnection connection = builder.readPreference(
        MongoReadPreferences.builder()
            .primary(false)
            .secondary(false)
            .tags(BsonDocuments.of("test", "27018"))
            .build())
        .build()) {
      for (int i = 0; i < 100; i++)
        Assert.assertTrue(connection.database("admin").command("isMaster")
            .get("secondary", Boolean.class).booleanValue());
    }
  }

  @Test
  public void readPreferenceWithMultipleTag() throws MongoException {
    try (MongoConnection connection = builder.readPreference(
        MongoReadPreferences.builder()
            .primary(false)
            .secondary(false)
            .tags(BsonDocuments.of("test", "27018"), BsonDocuments.of("test", "27019"))
            .build())
        .build()) {
      for (int i = 0; i < 100; i++) {
        BsonDocument isMaster = connection.database("admin").command("isMaster");
        int port = HostAndPort.fromString(isMaster.get("me", String.class)).getPort();
        Assert.assertTrue(port == 27018 || port == 27019);
        Assert.assertTrue(isMaster.get("secondary", Boolean.class).booleanValue());
      }
    }
  }
}
