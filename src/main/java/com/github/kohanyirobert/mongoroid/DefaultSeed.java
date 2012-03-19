package com.github.kohanyirobert.mongoroid;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.net.InetSocketAddress;
import java.util.List;

final class DefaultSeed implements MongoSeed {

  private static final Function<InetSocketAddress, String> TO_STRING =
      new Function<InetSocketAddress, String>() {

        @Override
        public String apply(InetSocketAddress input) {
          return input.getHostName() + ":" + input.getPort();
        }
      };

  private final List<InetSocketAddress> addresses;

  DefaultSeed(List<InetSocketAddress> addresses) {
    this.addresses = ImmutableList.copyOf(addresses);
  }

  @Override
  public List<InetSocketAddress> addresses() {
    return addresses;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(addresses());
  }

  @Override
  public boolean equals(Object object) {
    return object instanceof MongoSeed
        ? addresses().equals(((MongoSeed) object).addresses())
        : false;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(MongoSeed.class)
        .add("addresses", Lists.transform(addresses(), TO_STRING))
        .toString();
  }
}
