package jmud.engine.job;

import jmud.engine.job.definitions.AbstractJob;


/*
 * JobWorker.java
 *
 * Created on 18NOV08 by David Loman
 */

public class JobWorker implements Runnable {

	private int WorkerID = -1;
	private JobWorkerStatus status = JobWorkerStatus.idle; // default to idle
	private boolean runCmd = true;
	private Thread myThread;

	public JobWorker(int workerID) {
        WorkerID = workerID;
	}

	@Override
	public void run() {
		System.out.println("JobWorker ID:" + this.WorkerID + " is running.");
		while (this.runCmd) {

			// Access to Queue is synchronized internal to JobManager
			AbstractJob job = JobManager.getInstance().popJobFromQueue();

			if (job != null) {
				//Call the do Job function.
				boolean retVal = job.doJob();

				if (retVal) {
					//System.out.println("JobWorker ID:" + this.WorkerID + " reports that JobID: " + job.getJobID() + " has returned TRUE.");
                    //noinspection UnnecessaryContinue
                    continue;
				} else {
					System.err.println("JobWorker ID:" + this.WorkerID + " reports that JobID: " + job.getJobID() + " has returned FALSE.");
				}

			} else {
				try {
					synchronized (this.myThread) {
						this.myThread.wait(1000L); // Wait a max of 1 second
						System.out.println("Worker ID: " + this.WorkerID + "\t Wait Finished. Queue len: " + JobManager.getInstance().getQueueLen());
					}
				} catch (InterruptedException e) {
					System.err.println("JobWorker ID:" + this.WorkerID
							+ " threw a InterruptedException while .wait()ing.\n" + e.getStackTrace().toString());
				}
			}

		}
		System.out.println("JobWorker ID:" + this.WorkerID + " is shutdown.");
	}

	public int getWorkerID() {
		return WorkerID;
	}

	public JobWorkerStatus getStatus() {
		return status;
	}

	public void start() {
		System.out.println("JobWorker ID:" + this.WorkerID + ": Received Startup Command.");
		this.runCmd = true;
		this.myThread = new Thread(this, "ConnectionManager-Thread");
		this.myThread.start();
	}

	public void stop() {
		System.out.println("JobWorker ID:" + this.WorkerID + ": Received Shutdown Command.");
		this.runCmd = false;
	}
}
