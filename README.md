# mongoroid
*mongoroid* is a simple and (hopefully) straightforward-to-use [MongoDB][]
driver written in Java. Its dependencies are [ebson][] and the
[Guava libraries][] by Google.

## License
Released under the permissive [MIT License][].

## Author
[Kohányi Róbert][].

## Download
Add the library as a dependency in your project's *pom.xml* like this.

```xml
<dependency>
  <groupId>com.github.kohanyirobert</groupId>
  <artifactId>mongoroid</artifactId>
  <version>...</version>
</dependency>
```

Releases and snapshots are deployed to [Sonatype's][] [OSS repository][] (and
synced to the [Central Maven Repository][] from there). To download JARs from
Sonatype's repository include the following repository tag inside your Maven
installation's *settings.xml* or your project's *pom.xml*.

```xml
<repository>
  <id>sonatype-oss<id>
  <url>https://oss.sonatype.org/content/groups/public</url>
</repository>
```

## Build
As the project is managed with [Maven][] you simply clone it and issue *mvn
install* or *mvn package* inside the clone's directory.

```
git clone git://github.com/kohanyirobert/mongoroid.git
cd mongoroid/
mvn package
# and/or
mvn install
```

## Usage
### Simple Usage
```java
// get a connection to the default database instance
MongoConnection connection = MongoConnections.get();

// get a database over the connection
MongoDatabase database = connection.database("test");

// get a collection in a database
MongoCollection collection = database.collection("test");

// query the collection
try (MongoCursor cursor = collection.find(MongoFinds.get())) {
  while (cursor.hasNext()) {
    BsonDocument document = cursor.next();
    ...
  }
}

// insert into the collection
collection.insert(MongoInserts.builder()
    .documents(BsonDocuments.of("key", "value"))
    .build());

// ...
```
### Advanced Usage
```java
// get a seed list for the database istances you wish to communicate with
 MongoSeed seed = MongoSeeds.builder()
        .address("a.com", 27018)
        .address("b.com", 27019)
        .address("c.com", 27020)
        .build();

// get a configration for the connection to be create in the following steps
MongoConfig config = MongoConfigs.builder()
    .poolSize(3)
    .connectTimeout(10, TimeUnit.SECONDS)
    .refreshTimeout(60, TimeUnit.MINUTES)
    .build();

// get a read preference which influences the behaviour of read operations
MongoReadPreference readPreference = MongoReadPreferences.builder()
    .primary(true)
    .secondary(true)
    .tags(BsonDocuments.of("test", "ok"))
    .build();

// get a write preference which influences the behavior write operations
MongoWritePreference writePreference = MongoWritePreferences.builder()
    .w("test")
    .journal(true)
    .build();

// finally get a connection using the previously create objects
MongoConnection connection = MongoConnections.builder()
    .seed(seed)
    .config(config)
    .readPreference(readPreference)
    .writePreference(writePreference)
    .build();

// ...
```

[MongoDB]: http://www.mongodb.org
[ebson]: https://github.com/kohanyirobert/ebson
[Guava libraries]: http://code.google.com/p/guava-libraries
[Kohányi Róbert]: http://kohanyirobert.github.com
[MIT License]: https://raw.github.com/kohanyirobert/ebson/master/LICENSE.txt
[Sonatype's]: http://sonatype.com
[OSS repository]: https://oss.sonatype.org
[Central Maven Repository]: http://search.maven.org
[Maven]: http://maven.apache.org
