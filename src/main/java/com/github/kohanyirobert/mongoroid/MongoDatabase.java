package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

import java.util.List;

// @checkstyle:off Javadoc
public interface MongoDatabase {

  String name();

  MongoConnection connection();

  List<MongoCollection> collections() throws MongoException;

  MongoCollection collection(String name) throws MongoException;

  BsonDocument command(String command) throws MongoException;

  BsonDocument command(BsonDocument command) throws MongoException;

  void drop() throws MongoException;

  void login(String username, String password) throws MongoException;

  void logout() throws MongoException;
}
