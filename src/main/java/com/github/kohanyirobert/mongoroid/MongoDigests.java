package com.github.kohanyirobert.mongoroid;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.primitives.Bytes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

final class MongoDigests {

  private static final ThreadLocal<MessageDigest> MD5 = new ThreadLocal<MessageDigest>() {

    @Override
    protected MessageDigest initialValue() {
      try {
        return MessageDigest.getInstance("MD5");
      } catch (NoSuchAlgorithmException ex) {
        throw new RuntimeException("cannot find md5 hashing algorithm", ex);
      }
    }
  };

  private MongoDigests() {}

  public static String newDigest(String nonce, String username, String passwordDigest) {
    return hex(MD5.get().digest(binary(nonce, username, passwordDigest)));
  }

  public static String newPasswordDigest(String username, String password) {
    return hex(MD5.get().digest(binary(username, ":mongo:", password)));
  }

  private static byte[] binary(String... strings) {
    return Joiner.on("").join(strings).getBytes(Charsets.UTF_8);
  }

  private static String hex(byte[]... bytes) {
    return DatatypeConverter.printHexBinary(Bytes.concat(bytes)).toLowerCase();
  }
}
