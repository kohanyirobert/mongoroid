package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

import org.junit.Assert;
import org.junit.Test;

public final class MongoDatabaseSimpleSingleCommandTest
    extends AbstractMongoConnectionSimpleSingleTest {

  public MongoDatabaseSimpleSingleCommandTest() {}

  @Test(expected = MongoException.class)
  public void adminCommandAgainstNonAdminDatabase() throws MongoException {
    connection.database("test").command("replSetGetStatus");
  }

  @Test
  public void adminCommandAgainstAdminDatabase() throws MongoException {
    assertCommandResult(connection.database("admin").command("replSetGetStatus"));
  }

  private static void assertCommandResult(BsonDocument result) {
    Assert.assertEquals(1.0, result.get("ok", Number.class).doubleValue(), 0.0);
  }
}
