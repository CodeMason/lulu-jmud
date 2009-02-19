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
import jmud.engine.netIO.Connection;
import jmud.engine.netIO.ConnectionState;

/**
 * Just a template. Can be deleted once the Job Repository has sufficient
 * samples to draw from.
 * 
 * @author David Loman
 * @version 0.1
 */

public class LogoutJob extends AbstractJob {
	private Connection c = null;

	public LogoutJob(Connection c) {
		super();
		this.c = c;
	}

	@Override
	public final boolean doJob() {
		synchronized (this.c) {

			// Check for a valid command
			if (this.c.getCmdBuffer().hasNextCommand() == false) {
				return false;
			}

			this.c.sendCRLF();
			this.c.setUName("");
			this.c.setPassWd("");
			this.c.setConnState(ConnectionState.CONNECTED);
		}
		return true;
	}

	public void sendLoginPrompt() {
		this.c.sendText(JMudStatics.getSplashScreen());
	}

}
