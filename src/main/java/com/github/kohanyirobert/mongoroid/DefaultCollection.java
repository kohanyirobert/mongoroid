package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;
import com.github.kohanyirobert.ebson.BsonDocuments;

import com.google.common.base.Objects;

// @do-not-check ClassFanOutComplexity
final class DefaultCollection implements MongoCollection {

  final String name;
  final DefaultDatabase database;

  DefaultCollection(String name, DefaultDatabase database) {
    this.name = name;
    this.database = database;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public MongoDatabase database() {
    return database;
  }

  @Override
  public BsonDocument command(String command) throws MongoException {
    return database.command(command);
  }

  @Override
  public BsonDocument command(BsonDocument command) throws MongoException {
    return database.command(command);
  }

  @Override
  public int count(MongoCount count) throws MongoException {
    BsonDocument.Builder command = BsonDocuments.builder()
        .put("count", name)
        .put("query", count.selector());

    if (!count.fields().isEmpty())
      command.put("fields", count.fields());

    if (count.skip() > 0)
      command.put("skip", Integer.valueOf(count.skip()));

    if (count.limit() > 0)
      command.put("limit", Integer.valueOf(count.limit()));

    return command(command.build()).get("n", Double.class).intValue();
  }

  // @do-not-check .
  @Override
  public MongoCursor find(MongoFind find) {
    MongoMessageQuery query = new DefaultMessageQuery();
    query.fullCollectionName(database.name() + "." + name);
    query.slaveOk(database().connection().readPreference().secondary());

    if (!find.fields().isEmpty())
      query.returnFieldSelector(find.fields());

    if (find.skip() > 0)
      query.numberToSkip(find.skip());

    if (find.limit() > 0)
      query.numberToReturn(find.limit());

    BsonDocument.Builder builder = BsonDocuments.builder()
        .put("query", find.selector());

    if (!find.sort().isEmpty())
      builder.put("orderby", find.sort());

    if (find.explain())
      builder.put("explain", Boolean.valueOf(find.explain()));

    if (find.snapshot())
      builder.put("snapshot", Boolean.valueOf(find.snapshot()));

    query.query(builder.build());

    return new DefaultCursor(this, query);
  }

  @Override
  public BsonDocument distinct(MongoDistinct distinct) throws MongoException {
    BsonDocument.Builder command = BsonDocuments.builder()
        .put("distinct", name)
        .put("key", distinct.key());

    if (!distinct.selector().isEmpty())
      command.put("update", distinct.selector());

    return command(command.build()).get("values", BsonDocument.class);
  }

  // @do-not-check CyclomaticComplexity
  @Override
  public BsonDocument modify(MongoModify modify) throws MongoException {
    BsonDocument.Builder command = BsonDocuments.builder()
        .put("findandmodify", name)
        .put("query", modify.selector());

    if (!modify.document().isEmpty())
      command.put("update", modify.document());

    if (!modify.fields().isEmpty())
      command.put("fields", modify.fields());

    command.put("sort", modify.sort());

    if (modify.remove())
      command.put("remove", Boolean.valueOf(modify.remove()));

    command.put("new", Boolean.valueOf(modify.modified()))
        .put("upsert", Boolean.valueOf(modify.upsert()));

    Object value = command(command.build()).get("value");
    return value == null
        ? BsonDocuments.of()
        : (BsonDocument) value;
  }

  @Override
  public void insert(MongoInsert insert) throws MongoException {
    MongoMessageInsert message = new DefaultMessageInsert();
    message.fullCollectionName(database.name() + "." + name);
    message.documents(insert.documents());
    message.continueOnError(insert.continueOnError());
    database.tell(message);
  }

  @Override
  public void index(MongoIndex index) throws MongoException {
    MongoCollection indexes = database.collection("system.indexes");
    indexes.insert(MongoInserts.builder()
        .documents(BsonDocuments.of(
            "key", index.selector(),
            "name", index.name(),
            "ns", database.name() + "." + name,
            "sparse", Boolean.valueOf(index.sparse()),
            "unique", Boolean.valueOf(index.unique()),
            "dropDups", Boolean.valueOf(index.dropDuplicates()),
            "background", Boolean.valueOf(index.background()))).build());
  }

  @Override
  public void update(MongoUpdate update) throws MongoException {
    MongoMessageUpdate message = new DefaultMessageUpdate();
    message.fullCollectionName(database.name() + "." + name);
    message.selector(update.selector());
    message.update(update.document());
    message.upsert(update.upsert());
    message.multiUpdate(update.multi());
    database.tell(message);
  }

  @Override
  public void remove(MongoRemove remove) throws MongoException {
    MongoMessageDelete message = new DefaultMessageDelete();
    message.selector(remove.selector());
    message.fullCollectionName(database.name() + "." + name);
    message.singleRemove(remove.single());
    database.tell(message);
  }

  @Override
  public void drop() throws MongoException {
    command(BsonDocuments.of("drop", name()));
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name(), database());
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof MongoCollection) {
      MongoCollection other = (MongoCollection) object;
      return name().equals(other.name())
          && database().equals(other.database());
    }
    return false;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(MongoCollection.class)
        .add("name", name())
        .add("database", database())
        .toString();
  }

  void tell(MongoMessageRequest request) throws MongoException {
    database.tell(request);
  }

  MongoMessageReply say(MongoMessageRequest request) throws MongoException {
    return database.say(request);
  }
}
