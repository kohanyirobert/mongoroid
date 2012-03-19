package com.github.kohanyirobert.mongoroid;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public final class MongoDatabaseSimpleSingleDropTest
    extends AbstractMongoConnectionSimpleSingleTest {

  private MongoDatabase database;

  public MongoDatabaseSimpleSingleDropTest() {}

  @Override
  @Before
  public void setUp() throws MongoException {
    super.setUp();
    database = connection.database("drop");
  }

  @Ignore("in my opinion this should throw, but the server returns ok")
  @Test(expected = MongoNamespaceException.class)
  public void dropNonExistentDatabase() throws MongoException {
    database.drop();
  }

  @Test
  public void dropExistingDatabase() throws MongoException {
    // a database is created on the event of a query or some other shit
    database.collection("drop").find(MongoFinds.get());
    database.drop();
  }
}
