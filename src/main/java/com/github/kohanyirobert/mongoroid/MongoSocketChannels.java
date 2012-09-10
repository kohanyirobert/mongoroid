package com.github.kohanyirobert.mongoroid;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Set;

final class MongoSocketChannels {

  private MongoSocketChannels() {}

  // @do-not-check-next-line JavaNCSS|MethodLength
  public static SocketChannel get(final Socket socket) throws MongoException {
    try {
      final ReadableByteChannel in = Channels.newChannel(socket.getInputStream());
      final WritableByteChannel out = Channels.newChannel(socket.getOutputStream());
      // @do-not-check-next-line AnonInnerLength
      return new SocketChannel(null) {

        @Override
        public int read(ByteBuffer dst) throws IOException {
          return in.read(dst);
        }

        @Override
        public int write(ByteBuffer src) throws IOException {
          return out.write(src);
        }

        @Override
        public SocketAddress getLocalAddress() throws IOException {
          throw new UnsupportedOperationException();
        }

        @Override
        public <T> T getOption(SocketOption<T> name) throws IOException {
          throw new UnsupportedOperationException();
        }

        @Override
        public Set<SocketOption<?>> supportedOptions() {
          throw new UnsupportedOperationException();
        }

        @Override
        public SocketChannel bind(SocketAddress local) throws IOException {
          throw new UnsupportedOperationException();
        }

        @Override
        public <T> SocketChannel setOption(SocketOption<T> name, T value) throws IOException {
          throw new UnsupportedOperationException();
        }

        @Override
        public SocketChannel shutdownInput() throws IOException {
          throw new UnsupportedOperationException();
        }

        @Override
        public SocketChannel shutdownOutput() throws IOException {
          throw new UnsupportedOperationException();
        }

        @Override
        public Socket socket() {
          throw new UnsupportedOperationException();
        }

        @Override
        public boolean isConnected() {
          throw new UnsupportedOperationException();
        }

        @Override
        public boolean isConnectionPending() {
          throw new UnsupportedOperationException();
        }

        @Override
        public boolean connect(SocketAddress remote) throws IOException {
          throw new UnsupportedOperationException();
        }

        @Override
        public boolean finishConnect() throws IOException {
          throw new UnsupportedOperationException();
        }

        @Override
        public SocketAddress getRemoteAddress() throws IOException {
          throw new UnsupportedOperationException();
        }

        @Override
        public long read(ByteBuffer[] dsts, int offset, int length) throws IOException {
          throw new UnsupportedOperationException();
        }

        @Override
        public long write(ByteBuffer[] srcs, int offset, int length) throws IOException {
          throw new UnsupportedOperationException();
        }

        @Override
        protected void implCloseSelectableChannel() throws IOException {
          throw new UnsupportedOperationException();
        }

        @Override
        protected void implConfigureBlocking(boolean block) throws IOException {
          throw new UnsupportedOperationException();
        }
      };
    } catch (IOException ex) {
      throw new MongoException(ex);
    }
  }
}
