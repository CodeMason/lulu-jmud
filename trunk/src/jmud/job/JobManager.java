package jmud.job;

import java.util.LinkedList;

/*
 * JobManager.java
 *
 * Created on 18NOV08 by David Loman
 */

public class JobManager {
	/* 
	 * ********************************************
	 * Singleton Implementation
	 * ********************************************
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
		private final static JobManager INSTANCE = new JobManager();
	}

	public static JobManager getInstance() {
		return JobManagerHolder.INSTANCE;
	}

	/* 
	 * ********************************************
	 * Concrete Class Implementation
	 * ********************************************
	 */	

	public void init () {
		//TODO add in some JobWorker initializing stuff here.
	}
	
	private LinkedList<AbstractJob> jobQ = new LinkedList<AbstractJob>();

	public void pushJobToQueue(AbstractJob aj) {
		synchronized (this.jobQ) {
			this.jobQ.addLast(aj);
		}
	}

	public AbstractJob popJobFromQueue() {
		AbstractJob aj = null;
		synchronized (this.jobQ) {
			aj = this.jobQ.pop();
		}
		return aj;
	}

	public int getQueueLen() {
		synchronized (this.jobQ) {
			return this.jobQ.size();
		}
	}
}
