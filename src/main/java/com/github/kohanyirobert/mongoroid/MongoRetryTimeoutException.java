package com.github.kohanyirobert.mongoroid;

// @checkstyle:off Javadoc
@SuppressWarnings("serial")
public final class MongoRetryTimeoutException extends MongoException {

  public MongoRetryTimeoutException() {
    super();
  }

  public MongoRetryTimeoutException(String message, Throwable cause) {
    super(message, cause);
  }

  public MongoRetryTimeoutException(String message) {
    super(message);
  }

  public MongoRetryTimeoutException(Throwable cause) {
    super(cause);
  }
}
