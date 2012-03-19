package com.github.kohanyirobert.mongoroid;

import org.junit.After;
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
  }

  // @do-not-check DesignForExtension
  @After
  public void tearDown() throws MongoException {
    connection.database("test").collection("test").remove(MongoRemoves.get());
    connection.close();
  }
}
