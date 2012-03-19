package com.github.kohanyirobert.mongoroid;

abstract class AbstractMessageResponse
    extends AbstractMessageHeader
    implements MongoMessageResponse {

  protected AbstractMessageResponse() {
    super(MongoMessageOpcode.REPLY);
  }
}
