package com.github.kohanyirobert.mongoroid;

@SuppressWarnings("serial")
final class MongoIOException extends MongoException {

  public MongoIOException() {
    super();
  }

  public MongoIOException(String message, Throwable cause) {
    super(message, cause);
  }

  public MongoIOException(String message) {
    super(message);
  }

  public MongoIOException(Throwable cause) {
    super(cause);
  }
}
