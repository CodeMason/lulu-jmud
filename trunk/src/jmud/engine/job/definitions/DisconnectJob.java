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

import jmud.engine.netIO.JMudClient;

/**
 * Disconnect a connection
 *
 * @author Chris Maguire
 * @date December 5, 2008
 */

public class DisconnectJob extends AbstractJob {

	private JMudClient c;
	private String msg;
	
	public DisconnectJob(JMudClient c, String message) {
		super();
		this.c = c;
		this.msg = message;
	}

	@Override
	public final boolean doJob() {
		synchronized (this.c) {
			
			this.c.sendCRLFs(3);
			this.c.sendTextLn(this.msg);
			this.c.sendCRLFs(3);
			
			try {
				Thread.sleep(500L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			this.c.disconnect();
        }
		return true;
	}

}