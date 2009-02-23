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

import jmud.engine.netio.JMudClient;

/**
 * Just a template. Can be deleted once the Job Repository has sufficient
 * samples to draw from.
 * 
 * @author David Loman
 * @version 0.1
 */

public class DisplayAboutScreenJob extends AbstractClientJob {

	public DisplayAboutScreenJob(JMudClient c) {
		super(c);
	}

    // ToDo: we should create this programmatically (i.e. provide title and text and create "screen" on the fly.
	@Override
	public final boolean doJob() {
		synchronized (this.c) {
			// Send client the Character Select Screen.
			this.c.sendCRLFs(2);
			this.c.sendTextLn("-----~----------------~------");
			this.c.sendTextLn("-      About This Mud       -");
			this.c.sendTextLn("-----~----------------~------");
			this.c.sendTextLn("-- Really neat and thought --");
			this.c.sendTextLn("-- provoking text.         --");
			this.c.sendTextLn("-----~----------------~------");
			this.c.sendCRLFs(2);
			
		}
		return true;
	}

}
