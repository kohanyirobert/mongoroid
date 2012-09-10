package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;
import com.github.kohanyirobert.ebson.BsonDocuments;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;

final class MongoExceptions {

  private MongoExceptions() {}

  // @do-not-check-next-line CyclomaticComplexity
  public static void check(BsonDocument lastError) throws MongoException {
    if (!ok(lastError)) {
      String err = lastError.containsKey("err")
          ? lastError.get("err", String.class)
          : "";
      if ("Message contains no documents".equals(err)
          || "multi update only works with $ operators".equals(err))
        throw new IllegalArgumentException(err);
      else if ("".equals(err))
        return;
      else
        throw new MongoException(err);
    }
  }

  // @do-not-check-next-line CyclomaticComplexity|MethodLength
  public static void check(MongoMessageReply reply) throws MongoException {
    if (!ok(reply)) {
      BsonDocument document = Iterables.getFirst(reply.documents(), BsonDocuments.of());

      String err = document.containsKey("err")
          ? document.get("err", String.class)
          : document.containsKey("$err")
              ? document.get("$err", String.class)
              : "";

      String assertion = document.containsKey("assertion")
          ? document.get("assertion", String.class)
          : "";

      String assertionCode = document.containsKey("assertionCode")
          ? String.valueOf(document.get("assertionCode", Integer.class).intValue())
          : "";

      String errmsg = document.containsKey("errmsg")
          ? document.get("errmsg", String.class)
          : "";

      if (reply.queryFailure() || reply.cursorNotFound())
        throw new MongoException(err);

      else if (!"".equals(assertion))
        throw new MongoAssertionException(String.format("(%s) %s", assertionCode, assertion));

      else if (!"".equals(errmsg))
        throw new MongoException(errmsg);
    }
  }

  public static boolean ok(BsonDocument lastError) {
    return lastError.get("ok", Number.class).doubleValue() == 1.0
        && lastError.get("err") == null;
  }

  public static boolean ok(MongoMessageReply reply) {
    BsonDocument first = Iterables.getFirst(reply.documents(), BsonDocuments.of());
    return first.containsKey("ok") && first.get("ok", Number.class).doubleValue() == 1.0;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(MongoExceptions.class).toString();
  }
}
