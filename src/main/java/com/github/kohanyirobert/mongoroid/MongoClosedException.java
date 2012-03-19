package com.github.kohanyirobert.mongoroid;

// @checkstyle:off Javadoc
@SuppressWarnings("serial")
public final class MongoClosedException extends MongoException {

  public MongoClosedException() {
    super();
  }

  public MongoClosedException(String message, Throwable cause) {
    super(message, cause);
  }

  public MongoClosedException(String message) {
    super(message);
  }

  public MongoClosedException(Throwable cause) {
    super(cause);
  }
}
