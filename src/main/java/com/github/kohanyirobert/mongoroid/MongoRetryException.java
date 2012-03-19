package com.github.kohanyirobert.mongoroid;

// @checkstyle:off Javadoc
@SuppressWarnings("serial")
public final class MongoRetryException extends MongoException {

  public MongoRetryException() {
    super();
  }

  public MongoRetryException(String message, Throwable cause) {
    super(message, cause);
  }

  public MongoRetryException(String message) {
    super(message);
  }

  public MongoRetryException(Throwable cause) {
    super(cause);
  }
}
