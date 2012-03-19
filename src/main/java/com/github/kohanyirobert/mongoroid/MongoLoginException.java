package com.github.kohanyirobert.mongoroid;

// @checkstyle:off Javadoc
@SuppressWarnings("serial")
public final class MongoLoginException extends MongoException {

  public MongoLoginException() {
    super();
  }

  public MongoLoginException(String message, Throwable cause) {
    super(message, cause);
  }

  public MongoLoginException(String message) {
    super(message);
  }

  public MongoLoginException(Throwable cause) {
    super(cause);
  }
}
