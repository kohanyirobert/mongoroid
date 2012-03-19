package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

final class DefaultDatabase implements MongoDatabase {

  private final String name;
  private final DefaultConnection connection;

  private final ConcurrentMap<String, MongoCollection> collections = Maps.newConcurrentMap();

  DefaultDatabase(String name, DefaultConnection connection) {
    this.name = name;
    this.connection = connection;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public MongoConnection connection() {
    return connection;
  }

  @Override
  public List<MongoCollection> collections() throws MongoException {
    MongoCollection namespaces = collection("system.namespaces");
    // @do-not-check InnerAssignment
    try (MongoCursor cursor = namespaces.find(MongoFinds.get())) {
      while (cursor.hasNext()) {
        String fullCollectionName = cursor.next().get("name", String.class);
        assert fullCollectionName.startsWith(name() + ".");
        collection(fullCollectionName.substring((name() + ".").length()));
      }
    }
    List<MongoCollection> userCollections = Lists.newArrayList(collections.values());
    Iterables.removeIf(userCollections, new Predicate<MongoCollection>() {

      @Override
      public boolean apply(MongoCollection input) {
        return input.name().startsWith("system.") || input.name().contains(".$_");
      }
    });
    return ImmutableList.copyOf(userCollections);
  }

  @Override
  public MongoCollection collection(String name) {
    collections.putIfAbsent(name, new DefaultCollection(name, this));
    return collections.get(name);
  }

  @Override
  public BsonDocument command(String command) throws MongoException {
    return connection.command(name(), command, true);
  }

  @Override
  public BsonDocument command(BsonDocument command) throws MongoException {
    return connection.command(name(), command, true);
  }

  @Override
  public void login(String user, String password) throws MongoException {
    connection.login(name(), user, password);
  }

  @Override
  public void logout() throws MongoException {
    connection.logout(name());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name(), connection());
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof MongoDatabase) {
      MongoDatabase other = (MongoDatabase) object;
      return name().equals(other.name())
          && connection().equals(other.connection());
    }
    return false;
  }

  @Override
  public void drop() throws MongoException {
    command("dropDatabase");
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(MongoDatabase.class)
        .add("name", name())
        .add("connection", connection())
        .toString();
  }

  void tell(MongoMessageRequest request) throws MongoException {
    connection.tell(request, true);
  }

  MongoMessageReply say(MongoMessageRequest request) throws MongoException {
    return connection.say(request, true);
  }
}
