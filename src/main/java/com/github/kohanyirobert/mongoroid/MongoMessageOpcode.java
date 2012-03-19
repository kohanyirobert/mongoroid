package com.github.kohanyirobert.mongoroid;

enum MongoMessageOpcode {

  REPLY(1),
  UPDATE(2001),
  INSERT(2002),
  QUERY(2004),
  GETMORE(2005),
  DELETE(2006),
  KILL_CURSORS(2007);

  private final int code;

  private MongoMessageOpcode(int code) {
    this.code = code;
  }

  public int code() {
    return code;
  }

  public static MongoMessageOpcode find(int code) {
    for (MongoMessageOpcode opcode : values())
      if (opcode.code() - code == 0)
        return opcode;
    throw new IllegalArgumentException(String.format("no opcode "
        + "corresponding for the code '%s' was found", Integer.valueOf(code)));
  }
}
