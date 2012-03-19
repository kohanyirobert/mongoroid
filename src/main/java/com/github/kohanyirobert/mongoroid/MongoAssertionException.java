package com.github.kohanyirobert.mongoroid;

// @checkstyle:off Javadoc
@SuppressWarnings("serial")
public final class MongoAssertionException extends MongoException {

  public MongoAssertionException() {
    super();
  }

  public MongoAssertionException(String message, Throwable cause) {
    super(message, cause);
  }

  public MongoAssertionException(String message) {
    super(message);
  }

  public MongoAssertionException(Throwable cause) {
    super(cause);
  }
}
