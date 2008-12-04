package jmud.engine.netIO;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import jmud.engine.job.definitions.CheckConnBufferForValidCmd_Job;

/**
 * AcceptThread spawns a ConnectionAttempt object for each attempted connection.
 * ConnectionAttempt object will spawn ConnectionAttemptJob objects which will
 * attempt to authenticate against the DB and return a boolean.
 * ConnectionAttempt objects will track the number of times an account has
 * attempted to log in and will handle the text sending to the client. Once the
 * ConnectionAttempt object has attained a successful Authentication from the DB
 * via ConnectionAttempJob objects, a Player object is spawned and added to the
 * PlayerManager.
 * 
 * @author David Loman
 * @version 0.1
 */
public class Connection {
	/**
	 * Default buffer size.
	 */
	private static final int DEFAULT_BUFFER_SIZE = 8192;

	/**
	 * Supplies the data that is read into the buffer.
	 */
	private final SocketChannel sc;

	/**
	 * The buffer into which we'll read incoming data when it's available.
	 */
	private ByteBuffer readBuffer = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE);

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
	public Connection(final SocketChannel inSc) {
		this.sc = inSc;
		this.connState = ConnectionState.NotConnected;
	}

	/**
	 * @return the read buffer
	 */
	public final ByteBuffer getReadBuffer() {
		return readBuffer;
	}

	/**
	 * @return the data socket
	 */
	public final SocketChannel getSc() {
		return sc;
	}

	/**
	 * @return this connection's state.
	 */

	public ConnectionState getConnState() {
		return connState;
	}

	/*
	 * Data IO
	 */

	public void processIncoming() {
		if (this.connState == ConnectionState.NotConnected) {
			// Shouldn't be here!

		} else if (this.connState == ConnectionState.ConnectedButNotLoggedIn) {
			// Send login screen

		} else if (this.connState == ConnectionState.LoggedInToCharacterSelect) {
			// Send Character Select

		} else if (this.connState == ConnectionState.LoggedInToGameServer) {
			// Build command (Use a Job so we don't bog down the CommMan thread
			// with non-related work.
			CheckConnBufferForValidCmd_Job job = new CheckConnBufferForValidCmd_Job(this);

			// Submit next Job
			job.submitSelf();

		} else {
			// Shouldn't be here!
		}
	}

	public void sendText(String text) {
		// Attach the SocketChannel and send the text on its way!
		ConnectionManager.getInstance().send(this.sc, text);
	}

	/*
	 * Login Sequence Methods
	 */

}
