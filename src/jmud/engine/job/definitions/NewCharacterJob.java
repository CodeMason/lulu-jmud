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
		// TODO hook in the DB query here.

		
		//TODO finish the new character stuff
		this.c.sendCRLFs(2);
		this.c.sendTextLn("-----~----------------~-----" );
		this.c.sendTextLn("     Character Creation" );
		this.c.sendTextLn("-----~----------------~-----" );
		this.c.sendCRLF();
		this.c.sendTextLn("New Character creation is currently unfinished."  );
		this.c.sendCRLFs(2);
		this.c.setConnState(ConnectionState.LoggedInToCharacterSelect);
		CharacterSelectJob csj = new CharacterSelectJob(this.c, "");
		csj.submitSelf();

		
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
