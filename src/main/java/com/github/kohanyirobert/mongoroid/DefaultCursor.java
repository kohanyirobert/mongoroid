package com.github.kohanyirobert.mongoroid;

import com.github.kohanyirobert.ebson.BsonDocument;

import com.google.common.util.concurrent.Atomics;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

final class DefaultCursor implements MongoCursor {

  final DefaultCollection collection;
  final MongoMessageQuery query;

  final AtomicBoolean started = new AtomicBoolean();
  final AtomicBoolean closed = new AtomicBoolean();
  final AtomicLong cursorId = new AtomicLong();
  final AtomicReference<MongoException> exception = Atomics.newReference();

  MongoMessageReply reply;
  Iterator<BsonDocument> view;

  DefaultCursor(DefaultCollection collection, MongoMessageQuery query) {
    this.collection = collection;
    this.query = query;
  }

  // @checkstyle:off ReturnCount
  @Override
  public boolean hasNext() {
    try {
      startIfNotAlreadyStarted();

      if (view.hasNext())
        return true;

      else if (getMoreAndUpdateView())
        return hasNext();

      else
        return false;
    } catch (MongoException ex) {
      exception.set(ex);
      return false;
    }
  }

  @Override
  public BsonDocument next() {
    try {
      startIfNotAlreadyStarted();

      if (view.hasNext())
        return view.next();

      else if (getMoreAndUpdateView())
        return next();

      else
        throw new NoSuchElementException();
    } catch (MongoException ex) {
      exception.set(ex);
      throw new NoSuchElementException();
    }
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException(String.format("calling remove() on "
        + "'%s' doesn't supported; '%s' objects are mere snapshots of a query",
        MongoCursor.class, MongoCursor.class.getSimpleName()));
  }

  @Override
  public void close() throws MongoException {
    if (exception.get() != null)
      throw exception.get();

    if (exhausted())
      return;

    if (closed.compareAndSet(false, true)) {
      MongoMessageKillCursors killCursors = new DefaultMessageKillCursors();
      killCursors.cursorIds(cursorId.get());
      killCursors.numberOfCursorIds(killCursors.cursorIds().size());
      collection.tell(killCursors);
    }
  }

  private void startIfNotAlreadyStarted() throws MongoException {
    if (started.compareAndSet(false, true)) {
      reply = collection.say(query);
      cursorId.set(reply.cursorId());
      view = reply.documents().iterator();
    }
  }

  private boolean getMoreAndUpdateView() throws MongoException {
    if (exhausted())
      return false;

    MongoMessageGetmore getmore = new DefaultMessageGetmore();
    getmore.fullCollectionName(query.fullCollectionName());
    getmore.cursorId(reply.cursorId());

    reply = collection.say(getmore);
    cursorId.set(reply.cursorId());
    view = reply.documents().iterator();
    return true;
  }

  private boolean exhausted() {
    return cursorId.get() == 0L;
  }
}
