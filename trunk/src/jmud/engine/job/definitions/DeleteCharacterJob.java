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

import jmud.engine.netIO.Connection;
import jmud.engine.netIO.ConnectionState;

/**
 * Contains the routines for Deleting a  Character
 * 
 * @author David Loman
 * @version 0.1
 */

public class DeleteCharacterJob extends AbstractJob {

	private Connection conn = null;
	private String character;

	public DeleteCharacterJob(Connection c, String character) {
		super();
		this.conn = c;
		this.character = character;
	}

	@Override
	public final boolean doJob() {
		synchronized (this.conn) {
			// TODO finish the delete character stuff
			this.conn.sendCRLFs(2);
			this.conn.sendTextLn("-----~----------------~-----");
			this.conn.sendTextLn("     Character Deletion");
			this.conn.sendTextLn("-----~----------------~-----");
			this.conn.sendCRLF();
			this.conn.sendTextLn("Character deletionion is currently unfinished.");
			this.conn.sendCRLFs(2);
			
			this.conn.sendText("Ran DeleteCharacterJob for: "  + this.character);		

			this.conn.setConnState(ConnectionState.CHARACTERSELECT);
			this.conn.getConnState().createJob(this.conn).selfSubmit();
		}
		return true;
	}
}
