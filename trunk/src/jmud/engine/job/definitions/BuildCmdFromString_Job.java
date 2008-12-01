package jmud.engine.job.definitions;

import jmud.engine.netIO.Connection;

/**
 * Checks a connection's ByteBuffer to see if a valid command has been stored
 * yet. If there has, then this Job will submit a DetermineWhichCommand_Job.
 * @author David Loman
 * @version 0.1
 */

public class BuildCmdFromString_Job extends AbstractJob {

   private final Connection c;
   private String cmd = "";

   public BuildCmdFromString_Job(final Connection c, final String cmd) {
      this.c = c;
      this.cmd = cmd;
   }

   @Override
   public final boolean doJob() {

      return true;
   }

}
