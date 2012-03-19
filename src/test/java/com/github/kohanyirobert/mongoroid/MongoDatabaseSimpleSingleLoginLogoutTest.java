package com.github.kohanyirobert.mongoroid;

import org.junit.Before;
import org.junit.Test;

public final class MongoDatabaseSimpleSingleLoginLogoutTest
    extends AbstractMongoConnectionSimpleSingleTest {

  private MongoDatabase database;

  public MongoDatabaseSimpleSingleLoginLogoutTest() {}

  @Override
  @Before
  public void setUp() throws MongoException {
    super.setUp();
    database = connection.database("admin");
    // the superclass' setUp calls login
    database.logout();
  }

  @Test(expected = MongoLoginException.class)
  public void loginWithBadCredentials() throws MongoException {
    database.login("", "");
  }

  @Test(expected = MongoLogoutException.class)
  public void logoutWithoutLoggingIn() throws MongoException {
    database.logout();
  }

  @Test
  public void loginWithGoodCredentials() throws MongoException {
    database.login("admin", "admin");
  }

  @Test
  public void loginWithGoodCredentialsThenLogout() throws MongoException {
    database.login("admin", "admin");
    database.logout();
  }

  @Test(expected = MongoException.class)
  public void invokeSecureOperationAfterLoggingOut() throws MongoException {
    database.login("admin", "admin");
    database.logout();
    database.command("replSetGetStatus");
  }
}
