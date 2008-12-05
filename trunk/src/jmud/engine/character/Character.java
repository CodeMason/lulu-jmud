package jmud.engine.character;

import jmud.engine.netIO.Connection;
import jmud.engine.object.JMudObject;

/**
 * <code>Character</code> objects should only represent the data that pertains
 * to an individual character... attribute, name, description, etc.
 * 
 * @author david.h.loman
 */
public class Character extends JMudObject{

	private Connection connection = null;

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
	public Character(final String inName) {
		super(inName);

	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Override the sendToConsole nature of the baseJMudObject and
	 * instead, link it to the Connection
	 */
	@Override
	public void sendToConsole(String text) {
		this.connection.sendTextLn(text);
	}



}
