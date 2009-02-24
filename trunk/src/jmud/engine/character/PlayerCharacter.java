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
	 * @param name the character's name
	 */
	public PlayerCharacter(final String name) {
		super();
		this.setDisplayedName(name);

	}

	public JMudClient getConnection() {
		return c;
	}

	public void setConnection(JMudClient conn) {
		this.c = conn;
	}

	public String getPlayerCharactersName() {
		return this.getDisplayedName();
	}

	/**
	 * @return the ownerAccountID
	 */
	public int getOwnerAccountID() {
		return ownerAccountID;
	}

	public boolean save() {
		// TODO Finish PlayerCharacter.save()
		return false;
	}

	public void sendTextToObject(String text) {
		if (this.c != null) {
			this.c.sendTextLn(text);
		} else {

			System.out.println(text);
		}
	}

}
