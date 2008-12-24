/**
 * This file is part of Lulu's JMud.
 *
 *  Lulu's JMud is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Lulu's JMud is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Lulu's JMud.  If not, see <http://www.gnu.org/licenses/>.
 */
package jmud.engine.job;

import jmud.engine.core.JMudStatics;
import jmud.engine.job.definitions.AbstractJob;

/**
 * @author David Loman
 */

public class JobWorker implements Runnable {

	private int workerId = -1;
	private JobWorkerStatus status = JobWorkerStatus.IDLE; // default to
	// idle
	private boolean runCmd = true;
	private Thread myThread;

	public JobWorker(final int workerID) {
		workerId = workerID;
	}

	public final Thread getMyThread() {
		return myThread;
	}

	public final JobWorkerStatus getStatus() {
		return status;
	}

	public final int getWorkerId() {
		return workerId;
	}

	@Override
	public final void run() {
		System.out.println("JobWorker ID:" + this.workerId + " is running.");
		while (this.runCmd) {

			// Access to Queue is synchronized internal to JobManager
			AbstractJob job = JMudStatics.getDefaultJobManager().popJobFromQueue();

			if (job != null) {

				@SuppressWarnings("unused")
				boolean retVal = job.doJob();

			} else {
				try {
					synchronized (this) {
						this.status = JobWorkerStatus.IDLE;
						this.wait(1000L); // Wait a max of 1 second
						this.status = JobWorkerStatus.WORKING;
					}
				} catch (InterruptedException e) {
					System.err.println("JobWorker ID:" + this.workerId
							+ " threw a InterruptedException while .wait()ing.\n" + e.getStackTrace().toString());
				}
			}

		}
		System.out.println("JobWorker ID:" + this.workerId + " is shutdown.");
	}

	public final void start() {
		System.out.println("JobWorker ID:" + this.workerId + ": Received Startup Command.");
		this.runCmd = true;
		this.myThread = new Thread(this, "JobWorker" + this.workerId + "-Thread");
		this.myThread.start();
	}

	public final void stop() {
		System.out.println("JobWorker ID:" + this.workerId + ": Received stop Command.");
		this.runCmd = false;
		synchronized (this) {
			this.notify();
		}
    }
}
