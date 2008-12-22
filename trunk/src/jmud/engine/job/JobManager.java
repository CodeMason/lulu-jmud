package jmud.engine.job;

import jmud.engine.job.definitions.AbstractJob;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Singleton patterned class manages all AbstractJobs in a queue. Controls all
 * JobWorkers.
 *
 * @author David Loman
 * @version 0.1
 */

public class JobManager {

    private static final class LazyInstanceLoader {
        private static final JobManager LAZY_LOADED_INSTANCE = new JobManager();

        private LazyInstanceLoader() {
        }
    }

    public static JobManager getLazyLoadedInstance() {
        return LazyInstanceLoader.LAZY_LOADED_INSTANCE;
    }

    private Map<Integer, JobWorker> workers = new HashMap<Integer, JobWorker>();

    private LinkedList<AbstractJob> jobQ = new LinkedList<AbstractJob>();

    /**
     * Protected constructor is sufficient to suppress unauthorized calls to the
     * constructor.
     */
    protected JobManager() {
    }

    public void init(int requestedWorkers) {
        startAllWorkers();
        int workersNeeded = getNumberOfWorkersNeeded(requestedWorkers);
        for (int i = 0; i < workersNeeded; ++i) {
            this.createNewWorker();
        }
    }

    private int getNumberOfWorkersNeeded(int requestedWorkers) {
        int workersNeeded = 0;

        if(requestedWorkers > workers.size()){
            workersNeeded = requestedWorkers - workers.size();
        }
        return workersNeeded;
    }

    /**
     * JobWorker control.
     *
     * @return the next unused worker number
     */
    public int createNewWorker() {
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

    public Map<Integer, JobWorkerStatus> getAllJobWorkerStatus() {
        Set<Integer> keys = this.workers.keySet();

        Map<Integer, JobWorkerStatus> out = new HashMap<Integer, JobWorkerStatus>();

        for (Integer i : keys) {
            JobWorkerStatus jsw = this.getJobWorkerStatus(i);
            out.put(i, jsw);
        }

        return out;
    }

    public JobWorkerStatus getJobWorkerStatus(final int WorkerID) {
        JobWorker jw = this.workers.get(WorkerID);

        if (jw != null) {
            return jw.getStatus();
        }
        return null;
    }

    public int getQueueLen() {
        synchronized (this.jobQ) {
            return this.jobQ.size();
        }
    }

    public AbstractJob popJobFromQueue() {
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
    public void pushJobToQueue(AbstractJob aj) {
        synchronized (this.jobQ) {
            this.jobQ.addLast(aj);
        }

        // wake up a worker
        this.wakeWorkers();
    }

    public void startAllWorkers() {
        Set<Integer> keys = this.workers.keySet();

        for (Integer i : keys) {
            this.startWorker(i);
        }

    }

    public void startWorker(int WorkerID) {
        JobWorker jw = this.workers.get(WorkerID);

        if (jw != null) {
            jw.start();
        }

    }

    public void stopAllWorkers() {
        Set<Integer> keys = this.workers.keySet();

        for (Integer i : keys) {
            this.stopWorker(i);
        }

    }

    public void stopWorker(int WorkerID) {
        JobWorker jw = this.workers.get(WorkerID);

        if (jw != null) {
            jw.stop();
        }

    }

    public void wakeWorkers() {
        for (JobWorker jw : this.workers.values()) {
            synchronized (jw) {
                jw.notify();
            }
        }
    }

}
