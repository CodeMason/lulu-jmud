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

import jmud.engine.commands.AbstractCommand;
import jmud.engine.commands.CommandManager;
import jmud.engine.netIO.Connection;

/**
 * Just a template. Can be deleted once the Job Repository has sufficient
 * samples to draw from.
 * 
 * @author David Loman
 * @version 0.1
 */

public class LookupCommandJob extends AbstractConnectionJob {

	private String[] cmdArray;

	public LookupCommandJob(Connection c, String[] cmdArray) {
		super(c);
		this.cmdArray = cmdArray;
	}

	@Override
	public final boolean doJob() {
		synchronized (this.c) {

			//See if we can lookup the
			AbstractCommand ac = CommandManager.getLazyLoadedInstance()
					.lookupCommand(this.cmdArray[0]);

			if (ac == null) {
				// No command was found with that name. Tell client as much:
				this.c.sendTextLn("I do not understand: " + this.cmdArray[0]);
				this.c.sendPrompt();
				return false;
			} else {
				AbstractCommand newAc = ac.getNewInstance(this.c, this.jobMan,
						this.cmdArray);
				newAc.selfSubmit();
				return true;
			}
		}
	}
}
