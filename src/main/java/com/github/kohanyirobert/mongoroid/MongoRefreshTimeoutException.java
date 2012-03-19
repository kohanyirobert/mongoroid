package com.github.kohanyirobert.mongoroid;

// @checkstyle:off Javadoc
@SuppressWarnings("serial")
public final class MongoRefreshTimeoutException extends MongoException {

  public MongoRefreshTimeoutException() {
    super();
  }

  public MongoRefreshTimeoutException(String message, Throwable cause) {
    super(message, cause);
  }

  public MongoRefreshTimeoutException(String message) {
    super(message);
  }

  public MongoRefreshTimeoutException(Throwable cause) {
    super(cause);
  }
}
