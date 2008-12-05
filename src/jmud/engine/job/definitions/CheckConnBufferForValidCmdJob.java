package jmud.engine.job.definitions;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;

import jmud.engine.netIO.Connection;

/**
 * Checks a connection's ByteBuffer to see if a valid command has been stored
 * yet. If there has, then this Job will submit a DetermineWhichCommand_Job.
 * @author David Loman
 * @version 0.1
 */

public class CheckConnBufferForValidCmdJob extends AbstractJob {

   private final Connection c;
   private CharsetDecoder asciiDecoder;

   public CheckConnBufferForValidCmdJob(final Connection c) {
      super();
      this.c = c;
   }

   @Override
   public final boolean doJob() {
      // Declare OUTSIDE of the synchro block.
      String data;
      String cmd;
      String remainder;

      /*
       * obtain a lock on the connection's ByteBuffer so nothing else can mess
       * with it while we manipulate it here.
       */

      synchronized (this.c.getReadBuffer()) {
         try {
            // Build a string out of the connections ByteBuffer.
            data = asciiDecoder.decode(this.c.getReadBuffer()).toString();
         } catch (CharacterCodingException e) {
            System.err
                  .println("CheckConnBufferForValidCmd_Job.doJob(): CharacterCodingException in ByteBuffer of: "
                        + this.c.getSc().socket().getInetAddress().toString());
            this.c.getReadBuffer().clear();
            // TODO Send a Message to the client telling them a server error
            // occured.
            return false;
         }

         // Obtain an index in the string for \n
         int indexOfCRLF = data.indexOf("\n");

         if (indexOfCRLF == -1) {
            // ByteBuffer doesn't contain a complete command yet!
            return false;
         }
         // get the command
         cmd = data.substring(0, indexOfCRLF);

         // get the remainder of the buffer
         remainder = data.substring(indexOfCRLF + 1);

         // clear out the connections buffer
         this.c.getReadBuffer().clear();

         // write back in the 'remainder' of the buffer.
         for (char ch : remainder.toCharArray()) {
            this.c.getReadBuffer().putChar(ch);
         }
         // Now we are done with the ByteBuffer and can release the lock.
      }


      System.out.println(this.c.getSc().socket().getInetAddress().toString()
            + ": New Command String Recvd: " + cmd);

      // Build next Job
      BuildCmdFromStringJob job = new BuildCmdFromStringJob(c, cmd);

      // Submit next Job
      job.submitSelf();

      // return status
      return true;
   }

}
