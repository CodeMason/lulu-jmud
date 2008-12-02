package jmud.engine.netIO;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * AcceptThread spawns a ConnectionAttempt object for each attempted connection.
 * ConnectionAttempt object will spawn ConnectionAttemptJob objects which will
 * attempt to authenticate against the DB and return a boolean.
 * ConnectionAttempt objects will track the number of times an account has
 * attempted to log in and will handle the text sending to the client. Once the
 * ConnectionAttempt object has attained a successful Authentication from the DB
 * via ConnectionAttempJob objects, a Player object is spawned and added to the
 * PlayerManager.
 * @author David Loman
 * @version 0.1
 */
public class Connection {
   /**
    * Default buffer size.
    */
   private static final int DEFAULT_BUFFER_SIZE = 8192;

   /**
    * Supplies the data that is read into the buffer.
    */
   private final SocketChannel sc;

   /**
    * The buffer into which we'll read incoming data when it's available.
    */
   private final ByteBuffer readBuffer = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);

   /**
    * The buffer into which we'll write outgoing data when it's available.
    */
   private final ByteBuffer writeBuffer = ByteBuffer.allocate(8192);

   /**
    * Explicit constructor.
    * @param inSc
    *           the source data
    */
   public Connection(final SocketChannel inSc) {
      this.sc = inSc;
   }

   /**
    * @return the read buffer
    */
   public final ByteBuffer getReadBuffer() {
      return readBuffer;
   }

   /**
    * @return the data socket
    */
   public final SocketChannel getSc() {
      return sc;
   }

   /**
    * @return the write buffer
    */
   public final ByteBuffer getWriteBuffer() {
      return writeBuffer;
   }

}
