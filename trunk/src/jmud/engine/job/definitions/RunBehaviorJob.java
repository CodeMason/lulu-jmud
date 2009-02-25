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

import jmud.engine.behavior.definitions.AbstractBehavior;
import jmud.engine.event.JMudEvent;
import jmud.engine.object.JMudObject;

/**
 * A job that runs the supplied Behavior.
 * 
 * @author david.h.loman
 *
 */
public class RunBehaviorJob extends AbstractJob {

	private JMudObject whoToRunBehaviorOn;
	private AbstractBehavior behaviorToRun;
	private JMudEvent event;

	public RunBehaviorJob(JMudObject whoToRunBehaviorOn, AbstractBehavior ab, JMudEvent jme) {
		super();
		this.whoToRunBehaviorOn = whoToRunBehaviorOn;
		this.behaviorToRun = ab;
		this.event = jme;
	}

	@Override
	public final boolean doJob() {
		synchronized (this.behaviorToRun) {
			behaviorToRun.run(this.whoToRunBehaviorOn, this.event);
		}
		return true;
	}
	
}