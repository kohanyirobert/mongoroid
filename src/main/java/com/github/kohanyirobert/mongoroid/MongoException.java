package com.github.kohanyirobert.mongoroid;

// @checkstyle:off Javadoc
@SuppressWarnings("serial")
public class MongoException extends Exception {

  public MongoException() {
    super();
  }

  public MongoException(String message, Throwable cause) {
    super(message, cause);
  }

  public MongoException(String message) {
    super(message);
  }

  public MongoException(Throwable cause) {
    super(cause);
  }
}
