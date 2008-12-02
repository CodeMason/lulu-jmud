package jmud.engine.job;

import jmud.engine.job.definitions.AbstractJob;

/*
 * JobWorker.java
 *
 * Created on 18NOV08 by David Loman
 */

public class JobWorker implements Runnable {

	private int WorkerID = -1;
	private final JobWorkerStatus status = JobWorkerStatus.idle; // default to
	// idle
	private boolean runCmd = true;
	private Thread myThread;

	public JobWorker(final int workerID) {
		WorkerID = workerID;
	}

	public final Thread getMyThread() {
		return myThread;
	}

	public final JobWorkerStatus getStatus() {
		return status;
	}

	public final int getWorkerID() {
		return WorkerID;
	}

	@Override
	public final void run() {
		System.out.println("JobWorker ID:" + this.WorkerID + " is running.");
		while (this.runCmd) {

			// Access to Queue is synchronized internal to JobManager
			AbstractJob job = JobManager.getInstance().popJobFromQueue();

			if (job != null) {
				boolean retVal = job.doJob();

				synchronized (System.out) {
					System.out.println("JobWorker ID:" + this.WorkerID + " reports that Job: " + job.toString()
							+ " has completed: " + retVal);
				}

			} else {
				try {
					synchronized (this) {
						this.wait(1000L); // Wait a max of 1 second
						//System.out.println("Worker ID: " + this.WorkerID + "\t Wait Finished. Queue len: "
						//		+ JobManager.getInstance().getQueueLen());
					}
				} catch (InterruptedException e) {
					System.err.println("JobWorker ID:" + this.WorkerID
							+ " threw a InterruptedException while .wait()ing.\n" + e.getStackTrace().toString());
				}
			}

		}
		System.out.println("JobWorker ID:" + this.WorkerID + " is shutdown.");
	}

	public final void start() {
		System.out.println("JobWorker ID:" + this.WorkerID + ": Received Startup Command.");
		this.runCmd = true;
		this.myThread = new Thread(this, "JobWorker" + this.WorkerID + "-Thread");
		this.myThread.start();
	}

	public final void stop() {
		System.out.println("JobWorker ID:" + this.WorkerID + ": Received Shutdown Command.");
		this.runCmd = false;
	      synchronized (this) {
	            this.notify();
	         }

	}
}
