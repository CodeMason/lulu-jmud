package jmud.engine.job;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import jmud.engine.job.definitions.AbstractJob;

/**
 * Singleton patterned class manages all AbstractJobs in a queue. Controls all
 * JobWorkers.
 * @author David Loman
 * @version 0.1
 */

public class JobManager {
   /**
    * <code>JobManagerHolder</code> is loaded on the first execution of
    * <code>JobManager.getInstance()</code> or the first access to
    * <code>JobManagerHolder.INSTANCE</code>, not before.
    */
   private static final class JobManagerHolder {
      /**
       * The singleton instance.
       */
      private static final JobManager INSTANCE = new JobManager();

      /**
       * <code>JobManagerHolder</code> is a utility class. Disallowing
       * public/default constructor.
       */
      private JobManagerHolder() {
      }
   }

   /**
    * @return the singleton instance
    */
   public static JobManager getInstance() {
      return JobManagerHolder.INSTANCE;
   }

   private final Map<Integer, JobWorker> workers = new HashMap<Integer, JobWorker>();

   private final LinkedList<AbstractJob> jobQ = new LinkedList<AbstractJob>();

   /**
    * Protected constructor is sufficient to suppress unauthorized calls to the
    * constructor.
    */
   protected JobManager() {
   }

   /**
    * JobWorker control.
    * @return the next unused worker number
    */
   public final int createNewWorker() {
      int num = 0;

      // find the first unused number
      while (this.workers.containsKey(num)) {
         num++;
      }

      // make new worker
      JobWorker jw = new JobWorker(num);
      this.workers.put(num, jw);
      jw.start();

      return num;
   }

   public final Map<Integer, JobWorkerStatus> getAllJobWorkerStatus() {
      Set<Integer> keys = this.workers.keySet();

      Map<Integer, JobWorkerStatus> out = new HashMap<Integer, JobWorkerStatus>();

      for (Integer i : keys) {
         JobWorkerStatus jsw = this.getJobWorkerStatus(i);
         out.put(i, jsw);
      }

      return out;
   }

   public final JobWorkerStatus getJobWorkerStatus(final int WorkerID) {
      JobWorker jw = this.workers.get(WorkerID);

      if (jw != null) {
         return jw.getStatus();
      }
      return null;
   }

   public final int getQueueLen() {
      synchronized (this.jobQ) {
         return this.jobQ.size();
      }
   }

   public final void init(final int numOfWorkers) {
      for (int i = 0; i < numOfWorkers; ++i) {
         this.createNewWorker();
      }
   }

   public final AbstractJob popJobFromQueue() {
      AbstractJob aj = null;
      synchronized (this.jobQ) {

         if (!this.jobQ.isEmpty()) {

            aj = this.jobQ.pop();
         }
      }
      return aj;
   }

   /**
    * Queue access.
    */
   public final void pushJobToQueue(final AbstractJob aj) {
      synchronized (this.jobQ) {
         this.jobQ.addLast(aj);
      }

      // wake up a worker
      this.wakeWorkers();
   }

   public final void stopAllWorkers() {
      Set<Integer> keys = this.workers.keySet();

      for (Integer i : keys) {
         this.stopWorker(i);
      }

   }

   public final void stopWorker(final int WorkerID) {
      JobWorker jw = this.workers.get(WorkerID);

      if (jw != null) {
         jw.stop();
      }

   }

   public final void wakeWorkers() {
      for (JobWorker jw : this.workers.values()) {
         synchronized (jw) {
            jw.notify();
         }
      }
   }

}
