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
import jmud.engine.netIO.LoginState;

import java.util.Map;

/**
 * Just a template. Can be deleted once the Job Repository has sufficient
 * samples to draw from.
 *
 * @author David Loman
 * @version 0.1
 */

public class CharacterSelectJob extends AbstractJob {

	private String data = "";
	private Connection c = null;

	public CharacterSelectJob(Connection c, String data) {
		super();
		this.c = c;
		this.data = data + "";
	}

	public CharacterSelectJob(Connection c) {
		this(c, "");
	}

	@Override
	public final boolean doJob() {
		//Map of character names to the Character object refs
		Map<String, Character> chars = MysqlConnection.getCharactersByAccountID(this.c.getAccountID());

		synchronized (this.c) {

			if (this.data.length() == 0) {
				// must be newly sent to the Character Select Screen

				this.c.sendCRLFs(2);
				this.c.sendTextLn("-----~--------------~-----");
				this.c.sendTextLn("     Character Select");
				this.c.sendTextLn("-----~--------------~-----");

				for (String s : chars.keySet()) {
					this.c.sendTextLn("- " + s);
				}
				this.c.sendTextLn("-----~--------------~-----");
				this.c.sendText("Please connectionType the name of the character you wish to use, "
						+ "'New Character' to make a new character, " + "or 'Quit': ");
			} else {
				// Differentiate between character select, quit or new character
				if (data.toLowerCase().equals("new character")) {
					this.c.setConnState(ConnectionState.LoggedInToNewCharacter);
					NewCharacterJob ncj = new NewCharacterJob(this.c, data);
					ncj.submitSelf();

				} else if (data.toLowerCase().equals("quit")) {
					this.c.setConnState(ConnectionState.ConnectedButNotLoggedIn);
					this.c.setLoginstate(LoginState.Neither);
					this.c.sendCRLFs(2);
					LoginValidateJob hlj = new LoginValidateJob(this.c);
					hlj.submitSelf();

				} else {
					// Check to see if the character list has the character they
					// want
					if (chars.keySet().contains(data)) {
						// show selection and enter game
						this.c.sendTextLn("You selected: " + data);
						this.c.sendTextLn("Entering game...");
						this.c.sendCRLFs(2);
						this.c.setConnState(ConnectionState.LoggedInToGameServer);

						//Get the character object & pass it a reference to its associated Connection object
						Character ch = chars.get(data);
						ch.setConnection(this.c);

						ch.getConnection().sendText(JMudStatics.PROMPT);
					} else {
						// otherwise, show them the list again.
						this.c.sendTextLn("'" + data + "' is not a valid character selection. Try again.");
						CharacterSelectJob csj = new CharacterSelectJob(this.c);
						csj.submitSelf();

					}
				}
			}

		}
		return false;
	}

}
