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
 * Contains the routines for creating a new Character
 * 
 * @author David Loman
 * @version 0.1
 */

public class NewCharacterJob extends AbstractJob {

	private String data = "";
	private Connection c = null;

	public NewCharacterJob(Connection c, String data) {
		super();
		this.c = c;
		this.data = data + "";
	}

	public NewCharacterJob(Connection c ) {
		this(c,"");
	}
	@Override
	public final boolean doJob() {
		
		//TODO finish the new character stuff
		this.c.sendCRLFs(2);
		this.c.sendTextLn("-----~----------------~-----" );
		this.c.sendTextLn("     Character Creation" );
		this.c.sendTextLn("-----~----------------~-----" );
		this.c.sendCRLF();
		this.c.sendTextLn("New Character creation is currently unfinished."  );
		this.c.sendCRLFs(2);
		this.c.setConnState(ConnectionState.SELECTING_CHARACTER);
		submitJob(new CharacterSelectJob(this.c, ""));

		
		synchronized (this.c) {

			if (this.data.length() == 0) {
				// must be newly sent to the New Character Screen
			} else {
				// Lets see if we can process the command they want
			}

		}
		return true;
	}

}
