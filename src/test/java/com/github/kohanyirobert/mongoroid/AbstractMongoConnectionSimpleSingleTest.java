package com.github.kohanyirobert.mongoroid;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

public abstract class AbstractMongoConnectionSimpleSingleTest {

  protected MongoConnection connection;

  protected AbstractMongoConnectionSimpleSingleTest() {}

  // @do-not-check DesignForExtension
  @Before
  public void setUp() throws MongoException {
    connection = MongoConnections.builder()
        .config(MongoConfigs.builder()
            .poolSize(1)
            .build())
        .seed(MongoSeeds.builder()
            // @do-not-check MagicNumber
            .address(27018)
            .address(27019)
            .address(27020)
            .build())
        .build();
    connection.database("admin").login("admin", "admin");
    // there's a very vague issue with logins and sockets because as I've
    // noticed that if a communication goes through a socket which is
    // resolved as "127.0.0.1" mongodb grants read-write permission for every
    // database with an admin login, but not otherwise (not confirmed tho')
    connection.database("test").login("test", "test");
  }

  // @do-not-check DesignForExtension
  @After
  public void tearDown() throws MongoException {
    connection.database("test").collection("test").remove(MongoRemoves.get());
    // this makes sure that after running tests the
    // collection is cleared before closing the connection
    Assert.assertEquals(0, connection.database("test").collection("test").count(MongoCounts.get()));
    connection.close();
  }
}
