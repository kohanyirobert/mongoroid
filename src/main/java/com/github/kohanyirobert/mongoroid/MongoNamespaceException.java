package com.github.kohanyirobert.mongoroid;

// @checkstyle:off Javadoc
@SuppressWarnings("serial")
public final class MongoNamespaceException extends MongoException {

  public MongoNamespaceException() {
    super();
  }

  public MongoNamespaceException(String message, Throwable cause) {
    super(message, cause);
  }

  public MongoNamespaceException(String message) {
    super(message);
  }

  public MongoNamespaceException(Throwable cause) {
    super(cause);
  }
}
