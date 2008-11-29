package jmud.engine.job;

import jmud.engine.job.definitions.AbstractJob;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Singleton patterned class Manages all AbstractJobs in a queue. Controls all
 * JobWorkers.
 * 
 * @author David Loman
 * @version 0.1
 */

public class JobManager {
	/*
	 * 
	 * Singleton Implementation
	 */
	/**
	 * Protected constructor is sufficient to suppress unauthorized calls to the
	 * constructor
	 */
	protected JobManager() {
	}

	/**
	 * JobManagerHolder is loaded on the first execution of
	 * JobManager.getInstance() or the first access to
	 * JobManagerHolder.INSTANCE, not before.
	 */
	private static class JobManagerHolder {
		private static final JobManager INSTANCE = new JobManager();
	}

	public static JobManager getInstance() {
		return JobManagerHolder.INSTANCE;
	}

	/*
	 * 
	 * Concrete Class Implementation
	 */

	private Map<Integer, JobWorker> workers = new HashMap<Integer, JobWorker>();

	private final LinkedList<AbstractJob> jobQ = new LinkedList<AbstractJob>();

	public void init(int numOfWorkers) {
		for (int i = 0; i < numOfWorkers; ++i) {
			this.createNewWorker();
		}
	}

	/*
	 * 
	 * Queue Access
	 */
	public void pushJobToQueue(AbstractJob aj) {
		synchronized (this.jobQ) {
			this.jobQ.addLast(aj);
		}
		// wake up a worker
		this.wakeWorkers();
	}

	public AbstractJob popJobFromQueue() {
		AbstractJob aj = null;
		synchronized (this.jobQ) {

			if (this.jobQ.size() != 0) {

				aj = this.jobQ.pop();
			}
		}
		return aj;
	}

	public int getQueueLen() {
		synchronized (this.jobQ) {
			return this.jobQ.size();
		}
	}

	/*
	 * 
	 * JobWorker Control
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

	public void wakeWorkers() {
		for (JobWorker jw : this.workers.values()) {
			synchronized (jw) {
				jw.notify();
			}
		}
	}

	public void stopWorker(int WorkerID) {
		JobWorker jw = this.workers.get(WorkerID);

		if (jw != null) {
			jw.stop();
		}
	}

	public void stopAllWorkers() {
		Set<Integer> keys = this.workers.keySet();

		for (Integer i : keys) {
			this.stopWorker(i);
		}

	}

	public JobWorkerStatus getJobWorkerStatus(int WorkerID) {
		JobWorker jw = this.workers.get(WorkerID);

		if (jw != null) {
			return jw.getStatus();
		}
		return null;
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

}
