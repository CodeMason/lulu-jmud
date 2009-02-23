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

import jmud.engine.character.PlayerCharacter;
import jmud.engine.dbio.util.SqlConnHelpers;
import jmud.engine.netIO.JMudClient;

import java.util.Map;

/**
 * Just a template. Can be deleted once the Job Repository has sufficient
 * samples to draw from.
 * 
 * @author David Loman
 * @version 0.1
 */

public class DisplayCharactersJob extends AbstractClientJob {

	public DisplayCharactersJob(JMudClient c) {
		super(c);
	}

	@Override
	public final boolean doJob() {
		synchronized (this.c) {
			// Map of character names to the Character object refs
			Map<String, PlayerCharacter> chars = SqlConnHelpers.getCharactersByAccountID(this.c.getAccount().getAccountID());

			// Send client the Character Select Screen.
			this.c.sendCRLFs(2);
			this.c.sendTextLn("-----~--------------~-----");
			this.c.sendTextLn("     Character Select");
			this.c.sendTextLn("-----~--------------~-----");

			for (String s : chars.keySet()) {
				this.c.sendTextLn("- " + s);
			}
			this.c.sendTextLn("-----~--------------~-----");
			this.c.sendTextLn("Please type:\n\t-The name of the character you wish to use,\n\t"
					+ "-'New' to make a new character,\n\t"
					+ "-'Delete charname' to delete a character\n\t" 
					+ "-'Exit' to logout and return to the Main menu.");
			this.c.sendText("Make a selection: ");
		}
		return true;
	}

}
