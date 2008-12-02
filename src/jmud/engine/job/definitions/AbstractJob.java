package jmud.engine.job.definitions;

import java.util.UUID;

import jmud.engine.job.JobManager;

/**
 * Provides mandatory base implementation for all 'Job's'.
 * @author David Loman
 * @version 0.1
 */
public abstract class AbstractJob {

   private UUID ID = null;

   public AbstractJob() {
      super();
      this.ID = UUID.randomUUID();
   }

   public abstract boolean doJob();

   public final UUID getID() {
      return ID;
   }

   public final void submitSelf() {
      JobManager.getInstance().pushJobToQueue(this);
   }
}
