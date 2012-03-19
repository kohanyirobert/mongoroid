package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

// @checkstyle:off Javadoc
public interface MongoCollection {

  String name();

  MongoDatabase database();

  BsonDocument command(String command) throws MongoException;

  BsonDocument command(BsonDocument command) throws MongoException;

  int count(MongoCount count) throws MongoException;

  MongoCursor find(MongoFind find);

  BsonDocument distinct(MongoDistinct distinct) throws MongoException;

  BsonDocument modify(MongoModify modify) throws MongoException;

  void index(MongoIndex index) throws MongoException;

  void insert(MongoInsert insert) throws MongoException;

  void update(MongoUpdate update) throws MongoException;

  void remove(MongoRemove remove) throws MongoException;

  void drop() throws MongoException;
}
