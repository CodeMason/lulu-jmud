package jmud.engine.character;

import jmud.engine.dbio.Persistable;
import jmud.engine.netio.JMudClient;
import jmud.engine.object.JMudObject;

/**
 * <code>Character</code> objects should only represent the data that pertains
 * to an individual character... attribute, name, description, etc.
 * 
 * @author david.h.loman
 */
public class PlayerCharacter extends JMudObject implements Persistable {

	private JMudClient c = null;
	private int ownerAccountID;
	
	/**
	 * Explicit constructor.
	 * 
	 * @param iID
	 *            the ID
	 * @param inName
	 *            the character's name
	 * @param desc
	 *            the description of the character
	 */
	public PlayerCharacter(final String inName) {
		super(inName);

	}

	public JMudClient getConnection() {
		return c;
	}

	public void setConnection(JMudClient conn) {
		this.c = conn;
	}

	public String getPlayerCharactersName() {
		return this.getHumanReadableName();
	}
	
	/**
	 * @return the ownerAccountID
	 */
	public int getOwnerAccountID() {
		return ownerAccountID;
	}

	/**
	 * Override the sendToConsole nature of the baseJMudObject and instead, link
	 * it to the Connection
	 */
	@Override
	public void sendToConsole(String text) {
		if (this.c != null) {
			this.c.sendTextLn(text);
		} else {
			//Send it to.... where?!?
		}
	}

	@Override
	public boolean save() {
		// TODO Finish PlayerCharacter.save()
		return false;
	}

}
