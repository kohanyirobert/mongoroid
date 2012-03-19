package com.github.kohanyirobert.mongoroid;

// @checkstyle:off Javadoc
@SuppressWarnings("serial")
public final class MongoAccessDeniedException extends MongoException {

  public MongoAccessDeniedException() {
    super();
  }

  public MongoAccessDeniedException(String message, Throwable cause) {
    super(message, cause);
  }

  public MongoAccessDeniedException(String message) {
    super(message);
  }

  public MongoAccessDeniedException(Throwable cause) {
    super(cause);
  }
}
