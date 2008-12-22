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

/**
 * Just a template. Can be deleted once the Job Repository has sufficient
 * samples to draw from.
 *
 * @author David Loman
 * @version 0.1
 */

public class SendCharacterPromptJob extends AbstractJob {

	private String data = "";
	private Connection c = null;

	public SendCharacterPromptJob(Connection c, String data) {
		super();
		this.c = c;
		this.data = (data == null ? "" : data);
	}

    public SendCharacterPromptJob(Connection c) {
		this(c, "");
	}

	@Override
	public final boolean doJob() {
		synchronized (this.c) {
           
		}
		return false;
	}

	public void sendLoginPrompt() {
		this.c.sendText(JMudStatics.SplashScreen);
	}
}