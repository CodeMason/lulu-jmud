package jmud.engine.job;

/**
 * At this time there are two statuses a JobWorker may be placed in -
 * <code>Working</code> or <code>idle</code>.
 * @author David Loman
 */
public enum JobWorkerStatus {
   /**
    * Working.
    */
   WORKING,
   /**
    * Idle.
    */
   IDLE;
}
