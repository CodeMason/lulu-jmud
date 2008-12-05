package jmud.engine.netIO;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import jmud.engine.core.JMudStatics;
import jmud.engine.job.definitions.BuildCmdFromStringJob;
import jmud.engine.job.definitions.CharacterSelectJob;
import jmud.engine.job.definitions.HandleLoginJob;
import jmud.engine.job.definitions.NewCharacterJob;

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
	 * Connection uName
	 */
	private String uName = "";

	/**
	 * Connection passWd
	 */
	private String passWd = "";

	/**
	 * Number of login Attempts
	 */
	private int logAttempts = 0;

	/**
	 * Field to store the LoginState
	 */
	private LoginState loginstate = LoginState.Neither;

	/**
	 * Connection name
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
	 * This connection's State
	 */
	private ConnectionState connState = ConnectionState.NotConnected;

	/**
	 * Explicit constructor.
	 * 
	 * @param inSc
	 *            the source data
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
	 * @return this connection object's data socket
	 */
	public final SocketChannel getSc() {
		return sc;
	}

	/**
	 * @return this connection object's state.
	 */
	public ConnectionState getConnState() {
		return connState;
	}

	/**
	 * Set this Connection object's state
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
	 * @return this Connection object's uName.
	 */
	public String getUName() {
		return uName;
	}

	/**
	 * Set this Connection object's uName;
	 * 
	 * @param uName
	 */
	public void setUName(String uName) {
		this.uName = uName;
	}

	/**
	 * @return this Connection object's passWd.
	 */
	public String getPassWd() {
		return passWd;
	}

	/**
	 * Set this Connection object's passWd;
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
	 * @return this Connection object's state.
	 */
	public LoginState getLoginstate() {
		return loginstate;
	}

	/**
	 * Set this Connection object's loginstate
	 * 
	 * @param loginstate
	 */
	public void setLoginstate(LoginState loginstate) {
		this.loginstate = loginstate;
	}

	/*
	 * Data IO
	 */

	public void disconnect() {
		ConnectionManager.getInstance().disconnectFrom(this);
	}

	public void sendText(String text) {
		// Attach the SocketChannel and send the text on its way!
		ConnectionManager.getInstance().send(this.sc, text);
	}

	public void sendTextLn(String text) {
		// Attach the SocketChannel and send the text on its way!
		this.sendText(text + JMudStatics.CRLF);
	}

	public void sendCRLF() {
		this.sendText(JMudStatics.CRLF);
	}

	public void sendCRLFs(int numberOfCRLFs) {
		String out = "";
		for (int i = 0; i < numberOfCRLFs; ++i) {
			out += JMudStatics.CRLF;
		}
		this.sendText(out);
	}

}