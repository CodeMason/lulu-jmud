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
package jmud.engine.job.definitions;

import jmud.engine.core.JMudStatics;
import jmud.engine.job.JobManager;

import java.util.UUID;

/**
 * Provides mandatory base implementation for all 'Job's'.
 * 
 * @author David Loman
 * @version 0.1
 */
public abstract class AbstractJob {

	protected JobManager jobMan = JMudStatics.getJobManager();
	protected UUID uuid = null;

	public AbstractJob() {
		this(JMudStatics.getJobManager());
	}

	public AbstractJob( JobManager jm) {
		super();
		this.jobMan = jm;
		this.uuid = UUID.randomUUID();
	}

	/**
	 * Boolean value is returned to show the sucess of the job. This could
	 * (Should) be expanded out to be a more dynamic system that would capture
	 * more Job states that just Success or Fail
	 * 
	 * @return success of the job.
	 */
	public abstract boolean doJob();

	public final UUID getUUID() {
		return uuid;
	}

	public void selfSubmit() {
		jobMan.pushJobToQueue(this);
	}
}
