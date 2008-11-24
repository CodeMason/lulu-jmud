package jmud.job;

import jmud.job.definitions.AbstractJob;


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
		super();
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
					continue;
				} else {
					System.err.println("JobWorker ID:" + this.WorkerID + " reports that JobID: " + job.getJobID() + " has returned FALSE.");					
				}
				
			} else {
				try {
					//FIXME cannot remember how java handles self Monitoring state.... This wait statement may throw an error.
					this.myThread.wait(1000L); // Wait a max of 1 second
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
