package com.github.kohanyirobert.mongoroid;

interface MongoMessageFlags {

  int mask();

  void set(int position, boolean value);

  boolean get(int position);

  void clear();
}
