package jmud.engine.job.definitions;

import java.util.ArrayList;

import jmud.engine.character.Character;
import jmud.engine.character.CharacterManager;
import jmud.engine.core.JMudStatics;
import jmud.engine.netIO.Connection;
import jmud.engine.netIO.ConnectionState;
import jmud.engine.netIO.LoginState;

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
		// TODO hook in the DB query here.  Look up will

		// Temporary character select based on Statics
		ArrayList<String> chars = new ArrayList<String>();
		for (String s : JMudStatics.characters) {
			chars.add(s);
		}

		synchronized (this.c) {

			if (this.data.length() == 0) {
				// must be newly sent to the Character Select Screen

				this.c.sendCRLFs(2);
				this.c.sendTextLn("-----~--------------~-----");
				this.c.sendTextLn("     Character Select");
				this.c.sendTextLn("-----~--------------~-----");

				for (int i = 0; i < chars.size(); ++i) {
					this.c.sendTextLn(i + ") " + chars.get(i));
				}
				this.c.sendTextLn("-----~--------------~-----");
				this.c.sendText("Please type the name of the character you wish to use, "
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
					HandleLoginJob hlj = new HandleLoginJob(this.c);
					hlj.submitSelf();

				} else {
					// Check to see if the character list has the character they
					// want
					if (chars.contains(data)) {
						// show selection and enter game
						this.c.sendTextLn("You selected: " + data);
						this.c.sendTextLn("Entering game...");
						this.c.sendCRLFs(2);
						this.c.setConnState(ConnectionState.LoggedInToGameServer);
						
						//Get the character object & pass it a reference to its associated Connection object
						Character ch = CharacterManager.getInstance().loadCharacterFromDB(data);						
						ch.setConnection(this.c);
						
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
