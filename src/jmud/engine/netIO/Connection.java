package jmud.engine.netIO;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import jmud.engine.core.JMudStatics;

/**
 * The Connection class represents a single connection to/from a user. The
 * Connection class is the single, high level point of access for sending data
 * to a user. This abstraction of functionality away from the ConnectionManager
 * simplifies use of the netIO package.
 * 
 * @author David Loman
 * @version 0.1
 */
public class Connection {

	/**
	 * The AccountID associated with this Connection
	 */
	private int accountID = 0;

	/**
	 * The username associated with this Connection
	 */
	private String uName = "";

	/**
	 * The password associated with this Connection
	 */
	private String passWd = "";

	/**
	 * Number of login Attempts
	 */
	private int logAttempts = 0;

	/**
	 * The current Login State associated with this Connection
	 */
	private LoginState loginstate = LoginState.Neither;

	/**
	 * The name associated with this Connection.  This is not to be confused with
	 * the username.
	 */
	private final String name;

	/**
	 * Supplies the data that is read into the buffer.
	 */
	private final SocketChannel sc;

	/**
	 * The buffer into which we'll read incoming data when it's available.
	 */
	private ByteBuffer readBuffer = ByteBuffer.allocate(JMudStatics.CONNECTION_READ_BUFFER_SIZE);

	/**
	 * This Connection object's Connection State
	 */
	private ConnectionState connState = ConnectionState.NotConnected;

	/**
	 * Explicit constructor.
	 * 
	 * @param inSc SocketChannel used for communications
	 * @param name Name to identify this object by.
	 */
	public Connection(final SocketChannel inSc, final String name) {
		this.sc = inSc;
		this.connState = ConnectionState.NotConnected;
		this.name = name;
	}

	/*                                                   */
	/*                                                   */
	/* Getter's n' Setters */
	/*                                                   */
	/*                                                   */

	/**
	 * @return this connection object's read buffer
	 */
	public final ByteBuffer getReadBuffer() {
		return readBuffer;
	}

	/**
	 * @return this Connection object's data socket
	 */
	public final SocketChannel getSc() {
		return sc;
	}
/**
 * @return the AccountID associated with this Connection.
 */
	public int getAccountID() {
		return accountID;
	}

/**
 * Set the AccountID associated with this Connection
 * @param accountID
 */
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	/**
	 * @return the Connection State associated with this Connection
	 */
	public ConnectionState getConnState() {
		return connState;
	}

	/**
	 * Set this Connection object's Connection State.
	 * 
	 * @param connState
	 */
	public void setConnState(ConnectionState connState) {
		this.connState = connState;
	}

	/**
	 * @return this Connection object's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the username associated with this Connection.
	 */
	public String getUName() {
		return uName;
	}

	/**
	 * Set the username associated with this Connection.
	 * 
	 * @param uName
	 */
	public void setUName(String uName) {
		this.uName = uName;
	}

	/**
	 * @return the password associated with this Connection.
	 */
	public String getPassWd() {
		return passWd;
	}

	/**
	 * Set the password associated with this Connection
	 * 
	 * @param passWd
	 */
	public void setPassWd(String passWd) {
		this.passWd = passWd;
	}

	/**
	 * @return the number of Login attempts for this connection.
	 */
	public int getLogAttempts() {
		return logAttempts;
	}

	/**
	 * Set this Connection object's logAttempts;
	 * 
	 * @param logAttempts
	 */
	public void setLogAttempts(int logAttempts) {
		this.logAttempts = logAttempts;
	}

	/**
	 * Increment this Connection object's logAttempts;
	 * 
	 * @param logAttempts
	 */
	public void incrLogAttempts() {
		++this.logAttempts;
	}

	/**
	 * @return the Login State associated with this Connection
	 */
	public LoginState getLoginstate() {
		return loginstate;
	}

	/**
	 * Set this Connection object's Login State
	 * 
	 * @param loginstate
	 */
	public void setLoginstate(LoginState loginstate) {
		this.loginstate = loginstate;
	}

	/*
	 * Data IO
	 */

	/**
	 * Force this connection to close.
	 */
	public void disconnect() {
		ConnectionManager.getInstance().disconnectFrom(this);
	}

	/**
	 * Send the text, without a CRLF, to the client.
	 * @param text
	 */
	public void sendText(String text) {
		// Attach the SocketChannel and send the text on its way!
		ConnectionManager.getInstance().send(this.sc, text);
	}

	/**
	 * Send the text, with a CRLF, to the client.
	 * @param text
	 */
	public void sendTextLn(String text) {
		// Attach the SocketChannel and send the text on its way!
		this.sendText(text + JMudStatics.CRLF);
	}

	/**
	 * Send a CRLF to the client.
	 * @param text
	 */
	public void sendCRLF() {
		this.sendText(JMudStatics.CRLF);
	}

	/**
	 * Send multiple CRLFs to the client.
	 * @param text
	 */
	public void sendCRLFs(int numberOfCRLFs) {
		String out = "";
		for (int i = 0; i < numberOfCRLFs; ++i) {
			out += JMudStatics.CRLF;
		}
		this.sendText(out);
	}
	
	/**
	 * Send the mud prompt to the client.
	 * @param text
	 */
	public void sendPrompt() {
		this.sendText(JMudStatics.PROMPT);
	}


}
