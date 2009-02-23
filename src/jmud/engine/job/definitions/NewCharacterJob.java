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
 * Contains the routines for creating a new Character
 * 
 * @author David Loman
 * @version 0.1
 */

public class NewCharacterJob extends AbstractClientJob {

	public NewCharacterJob(JMudClient c) {
		super(c);
	}

	@Override
	public final boolean doJob() {
		synchronized (this.c) {

			// TODO finish the new character stuff
			this.c.sendCRLFs(2);
			this.c.sendTextLn("-----~----------------~-----");
			this.c.sendTextLn("     Character Creation");
			this.c.sendTextLn("-----~----------------~-----");
			this.c.sendCRLF();
			this.c.sendTextLn("New Character creation is currently unfinished.");
			this.c.sendCRLFs(2);
			
		}
		return true;
	}
}
