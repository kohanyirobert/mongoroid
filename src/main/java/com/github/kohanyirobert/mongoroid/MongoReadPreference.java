package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

import java.util.List;

/**
 * Representation of a read preference used by {@linkplain MongoConnection
 * connections} to determine where to send read operations in case of
 * communicating with more than one database instance.
 * <p>
 * <b>Notes:</b> use the {@linkplain MongoReadPreferences read preferences
 * utility class} to create new read preferences.
 * </p>
 */
public interface MongoReadPreference {

  /**
   * Returns <em>true</em> if this read preference allows read operations to be
   * sent to primary database instances; <em>false</em> otherwise.
   * 
   * @return <em>true</em> if this read preference allows read operations to be
   * sent to primary database instances; <em>false</em> otherwise
   */
  boolean primary();

  /**
   * Returns <em>true</em> if this read preference allows read operations to be
   * sent to secondary database instances; <em>false</em> otherwise.
   * 
   * @return <em>true</em> if this read preference allows read operations to be
   * sent to secondary database instances; <em>false</em> otherwise
   */
  boolean secondary();

  /**
   * Returns an immutable view of the database instance tags this read
   * preference allows read operations to be sent.
   * 
   * @return an immutable view of the database instance tags this read
   * preference allows read operations to be sent
   */
  List<BsonDocument> tags();

  /**
   * {@linkplain MongoReadPreference Read preference} builder.
   * <p>
   * <b>Notes:</b>
   * <ul>
   * <li>Calling a builder's {@linkplain #build} method <em>does not clear its
   * state</em>, however subsequent invocations of it returns new connections.</li>
   * <li>Seed builders can be acquired via the {@linkplain MongoReadPreferences
   * read preferences utility class}.</li>
   * </ul>
   * </p>
   */
  interface Builder {

    /**
     * Sets the boolean flag that indicates whether the read
     * preference being built allows read operations to be sent to primary
     * database instances to {@code primary}.
     * <p>
     * <b>Note:</b> defaults <em>true</em>.
     * </p>
     * 
     * @param primary the boolean flag that indicates whether the read
     * preference being built allows read operations to be sent to primary
     * database instances
     * @return this builder
     */
    Builder primary(boolean primary);

    /**
     * Sets the boolean flag that indicates whether the read
     * preference being built allows read operations to be sent to secondary
     * database instances to {@code secondary}.
     * <p>
     * <b>Note:</b> defaults <em>false</em>.
     * </p>
     * 
     * @param secondary the boolean flag that indicates whether the read
     * preference being built allows read operations to be sent to secondary
     * database instances
     * @return this builder
     */
    Builder secondary(boolean secondary);

    /**
     * Adds {@code tags} to the list of tags to be contained by the read
     * preference being built.
     * <p>
     * <b>Note:</b> defaults to an empty collection of documents.
     * </p>
     * 
     * @param tags the database instance tags to be contained by the read
     * preference being built
     * @return this builder
     * @throws NullPointerException if {@code tags} is null
     * @throws IllegalArgumentException if {@code tags} contains null
     */
    Builder tags(BsonDocument... tags);

    /**
     * Adds {@code tags} to the list of tags to be contained by the read
     * preference being built.
     * <p>
     * <b>Note:</b> defaults to an empty collection of documents.
     * </p>
     * 
     * @param tags the database instance tags to be contained by the read
     * preference being built
     * @return this builder
     * @throws NullPointerException if {@code tags} is null
     * @throws IllegalArgumentException if {@code tags} contains null
     */
    Builder tags(Iterable<BsonDocument> tags);

    /**
     * Returns a new read preference using the contents of this builder.
     * 
     * @return a new read preference using the contents of this builder
     */
    MongoReadPreference build();
  }
}
