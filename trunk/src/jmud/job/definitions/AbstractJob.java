package jmud.job.definitions;

import jmud.job.JobManager;

/**
 * Provides mandatory base implementation for all 'Job's'
 *
 * @author David Loman
 * @version 0.1
 */
public abstract class AbstractJob {

	private long JobID = -1L;

	public abstract boolean doJob();

	public long getJobID() {
		return JobID;
	}

	public void setJobID(long jobID) {
		JobID = jobID;
	}

	public void submitSelf() {
		JobManager.getInstance().pushJobToQueue(this);
	}
}
