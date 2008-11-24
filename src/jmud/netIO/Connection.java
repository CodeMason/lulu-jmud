package jmud.netIO;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * AcceptThread spawns a ConnectionAttempt object for each attempted connection.
 * ConnectionAttempt object will spawn ConnectionAttemptJob objects which will attempt
 * to authenticate against the DB and return a boolean.
 * ConnectionAttempt objects will track the number of times an account has attempted to
 * log in and will handle the text sending to the client.
 *
 * Once the ConnectionAttempt object has attained a successful Authentication from the DB via
 * ConnectionAttempJob objects, a Player object is spawned and added to the PlayerManager.
 *
 * @author David Loman
 * @version 0.1

 */
public class Connection {
	private SocketChannel sc;

	// The buffer into which we'll read incoming data when it's available
	private ByteBuffer readBuffer = ByteBuffer.allocate(8192);

	// The buffer into which we'll write outgoing data when it's available
	private ByteBuffer writeBuffer = ByteBuffer.allocate(8192);

	public Connection(SocketChannel sc) {
		this.sc = sc;
	}

	public SocketChannel getSc() {
		return sc;
	}

	public ByteBuffer getReadBuffer() {
		return readBuffer;
	}

	public ByteBuffer getWriteBuffer() {
		return writeBuffer;
	}

}
