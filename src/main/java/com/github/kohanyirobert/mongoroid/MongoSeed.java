package com.github.kohanyirobert.mongoroid;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * Representation of an address list used by {@linkplain MongoConnection
 * connections} as a seed to initiate communication with one or more database
 * instances.
 * <p>
 * <b>Notes:</b> use the {@linkplain MongoSeeds seeds utility class} to create
 * new seeds.
 * </p>
 */
public interface MongoSeed {

  /**
   * Returns an immutable view of the addresses in this seed.
   * <p>
   * <b>Notes:</b> the contents of the list never change and the addresses in it
   * will always be used to initiate communications with database instances -
   * even if they are currently down.
   * </p>
   * 
   * @return an immutable view of the addresses in this seed
   */
  List<InetSocketAddress> addresses();

  /**
   * {@linkplain MongoSeed Seed} builder.
   * <p>
   * <b>Notes:</b>
   * <ul>
   * <li>Calling a builder's {@linkplain #build} method <em>does not clear its
   * state</em>, however subsequent invocations of it returns new connections.</li>
   * <li>Seed builders can be acquired via the {@linkplain MongoSeeds seeds
   * utility class}.</li>
   * </ul>
   * </p>
   */
  interface Builder {

    /**
     * Adds {@code port} (on the localhost) to the list of addresses to be
     * contained by the seed being built.
     * 
     * @param port the port of the address to add to the list of addresses to be
     * contained by the seed being built
     * @return this builder
     * @throws IllegalArgumentException if {@code port} is out of range
     */
    Builder address(int port);

    /**
     * Adds {@code host} and {@code port} as a combination to the list of
     * addresses to be contained by the seed being built.
     * 
     * @param host the host of the address to add to the list of addresses to be
     * contained by the seed being built
     * @param port the port of the address to add to the list of addresses to be
     * contained by the seed being built
     * @return this builder
     * @throws NullPointerException if {@code host} is null
     * @throws IllegalArgumentException if {@code port} is out of range
     */
    Builder address(String host, int port);

    /**
     * Adds {@code host} and {@code port} as a combination to the list of
     * addresses to be contained by the seed being built.
     * 
     * @param host the host of the address to add to the list of addresses to be
     * contained by the seed being built
     * @param port the port of the address to add to the list of addresses to be
     * contained by the seed being built
     * @return this builder
     * @throws NullPointerException if {@code host} is null
     * @throws IllegalArgumentException if {@code port} is out of range
     */
    Builder address(InetAddress host, int port);

    /**
     * Adds {@code address} to the list of addresses to be contained by the seed
     * being built.
     * 
     * @param address the address to add to the list of addresses to be
     * contained by the seed being built
     * @return this builder
     * @throws NullPointerException if {@code address} is null
     */
    Builder address(InetSocketAddress address);

    /**
     * Returns a new seed using the contents of this builder.
     * 
     * @return a new seed using the contents of this builder
     */
    MongoSeed build();
  }
}
