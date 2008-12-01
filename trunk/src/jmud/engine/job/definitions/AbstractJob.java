package jmud.engine.job.definitions;

import java.util.UUID;

import jmud.engine.job.JobManager;

/**
 * Provides mandatory base implementation for all 'Job's'.
 * @author David Loman
 * @version 0.1
 */
public abstract class AbstractJob {

   private UUID JobID = null;

   public AbstractJob() {
      super();
      this.JobID = UUID.randomUUID();
   }

   public abstract boolean doJob();

   public final UUID getJobID() {
      return JobID;
   }

   public final void submitSelf() {
      JobManager.getInstance().pushJobToQueue(this);
   }
}
