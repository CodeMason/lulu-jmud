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

import jmud.engine.character.Character;
import jmud.engine.core.JMudStatics;
import jmud.engine.dbio.MysqlConnection;
import jmud.engine.netIO.Connection;
import jmud.engine.netIO.ConnectionState;

import java.util.Map;

/**
 * Just a template. Can be deleted once the Job Repository has sufficient
 * samples to draw from.
 * 
 * @author David Loman
 * @version 0.1
 */

public class CharacterSelectJob extends AbstractJob {

	private Connection conn = null;
	private String character;

	public CharacterSelectJob(Connection c, String character) {
		super();
		this.conn = c;
		this.character = character;
	}

	@Override
	public final boolean doJob() {
		// Map of character names to the Character object refs

		synchronized (this.conn) {
			Map<String, Character> chars = MysqlConnection.getCharactersByAccountID(this.conn.getAccountID());

			// Check to see if the character list has the character they
			// want
			if (chars.keySet().contains(this.character)) {
				// show selection and enter game
				this.conn.sendTextLn("You selected: " + this.character);
				this.conn.sendTextLn("Entering game...");
				this.conn.sendCRLFs(2);
				this.conn.setConnState(ConnectionState.INGAME);

				// Get the character object & pass it a reference to its
				// associated Connection object
				Character ch = chars.get(this.character);
				ch.setConnection(this.conn);

				ch.getConnection().sendText(JMudStatics.PROMPT);
			} else {
				// otherwise, show them the list again.
				this.conn.sendTextLn("'" + this.character + "' is not a valid character selection. Try again.");
				(new DisplayCharactersJob(this.conn)).selfSubmit();
			}
		}

		return true;
	}
}
