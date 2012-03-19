package com.github.kohanyirobert.mongoroid;

@SuppressWarnings("serial")
final class MongoRefreshException extends MongoException {

  public MongoRefreshException() {
    super();
  }

  public MongoRefreshException(String message, Throwable cause) {
    super(message, cause);
  }

  public MongoRefreshException(String message) {
    super(message);
  }

  public MongoRefreshException(Throwable cause) {
    super(cause);
  }
}
