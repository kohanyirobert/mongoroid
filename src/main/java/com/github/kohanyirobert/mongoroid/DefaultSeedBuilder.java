package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.mongoroid.MongoSeed.Builder;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;

final class DefaultSeedBuilder implements MongoSeed.Builder {

  private static final String DEFAULT_HOST = "127.0.0.1";
  private static final int DEFAULT_PORT = 27017;

  private final List<InetSocketAddress> addresses = Lists.newArrayList();

  DefaultSeedBuilder() {}

  @Override
  public Builder address(int port) {
    return address(DEFAULT_HOST, port);
  }

  @Override
  public Builder address(String host, int port) {
    Preconditions.checkNotNull(host);
    return address(new InetSocketAddress(host, 0).getAddress(), port);
  }

  @Override
  public Builder address(InetAddress host, int port) {
    Preconditions.checkNotNull(host);
    return address(new InetSocketAddress(host, port));
  }

  @Override
  public MongoSeed.Builder address(InetSocketAddress address) {
    Preconditions.checkNotNull(address);
    addresses.add(address);
    return this;
  }

  @Override
  public MongoSeed build() {
    if (addresses.isEmpty())
      address(DEFAULT_PORT);
    return new DefaultSeed(addresses);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(MongoSeed.Builder.class).toString();
  }
}
