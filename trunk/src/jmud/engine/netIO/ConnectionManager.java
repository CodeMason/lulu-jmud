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
package jmud.engine.netIO;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;

/**
 * ConnectionManager is a Runnable class that manages all Connections.
 * ConnectionManager contains a single NIO selector and all routines for:
 * -Accepting new connections -Handling IO for existing connections
 * -Disconnecting connections.
 * 
 * We are opting for a single thread/single selector solution for many reasons,
 * all of which can be summed up here:
 * http://rox-xmlrpc.sourceforge.net/niotut/index.html
 * 
 * @author David Loman
 * @version 0.1
 */
public class ConnectionManager implements Runnable {

	private boolean runStatus = true;
	private boolean runCmd = true;
	private int maxAllowedConns = 1024;
	private Thread myThread;
	private Selector selector;
	private final List<ConnectionEvent> pendingEvents = new LinkedList<ConnectionEvent>();
	private final Map<SocketChannel, List<ByteBuffer>> socketChannelByteBuffers = new HashMap<SocketChannel, List<ByteBuffer>>();
	private final Map<SocketChannel, Connection> sockChanConnMap = new HashMap<SocketChannel, Connection>();

	/*
	 * 
	 * 
	 * Constructors
	 */

	/**
	 * ConnectionManager Constructor that allows the user to pick only which
	 * port to bind the listening socket to.
	 * 
	 * @param port
	 * @throws IOException
	 */
	public ConnectionManager(int port) throws IOException {
		this(InetAddress.getLocalHost(), port);
	}

	/**
	 * ConnectionManager Constructor that allows the user to pick which
	 * InetAddress AND port to bind the listening socket to.
	 * 
	 * @param hostAddress
	 * @param port
	 * @throws IOException
	 */
	public ConnectionManager(InetAddress hostAddress, int port) throws IOException {
		// Create a new Selector
		this.selector = SelectorProvider.provider().openSelector();

		// Create a new non-blocking Server channel
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);

		// Bind
		InetSocketAddress inetSocketAddress = new InetSocketAddress(hostAddress, port);
		serverChannel.socket().bind(inetSocketAddress);

		System.out.println("ConnectionManager is configured to listen at: " + hostAddress.getCanonicalHostName()
				+ " on port: " + port);

		// register an interest in Accepting new connections.
		serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);
	}

	/*
	 * 
	 * 
	 * Accept / New Connection FNs
	 */

	private void acceptNewConnection(final SelectionKey key) throws IOException {
		// For an accept to be pending, the key contains a reference to the
		// ServerSocketChannel
		ServerSocketChannel ssc = (ServerSocketChannel) key.channel();

		// Accept
		SocketChannel sc = ssc.accept();

		// Configure
		this.createNewConnection(sc);

		System.out.println("ConnectionManager: Total Connections now: " + this.sockChanConnMap.size());
	}

	private Connection createNewConnection(SocketChannel sc) throws IOException {
		// configure socket channel
		sc.configureBlocking(false);
		sc.register(this.selector, SelectionKey.OP_READ);

		// make new connection
		Connection c = new Connection(this, sc);
		c.changeConnState(ConnectionState.CONNECTED);

		System.out.println("ConnectionManager: New Connection. ID: " + c.getConnectionID());

		// Map the SocketChannel to the Connection
		this.sockChanConnMap.put(sc, c);

		// Return a handle to the Connection object.
		return c;
	}

	/*
	 * 
	 * 
	 * Disconnect / Destroy Connection FNs
	 */

	/**
	 * Disconnect by Key object.
	 * 
	 * @param key
	 *            the key to disconnect
	 * @return true if the disconnect succeeded
	 * @throws IOException
	 */
	private boolean disconnect(final SelectionKey key) {
		System.out.println("ConnectionManager.disconnect(SelectionKey): key=" + key.toString());
		this.disconnect((SocketChannel) key.channel());

		key.cancel();
		return key.isValid();
	}

	/**
	 * Disconnect by SocketChannel.
	 * 
	 * @param sockChan
	 *            the SocketChannel to disconnect
	 * @return true if the disconnect succeeded
	 * @throws IOException
	 */
	private boolean disconnect(final SocketChannel sockChan) {
		System.out.println("ConnectionManager.disconnect(SocketChannel): sockChan=" + sockChan.toString());
		return this.disconnect(this.sockChanConnMap.get(sockChan));
	}

	/**
	 * Disconnect by SocketChannel and Connection.
	 * 
	 * @param sockChan
	 *            the SocketChannel to disconnect
	 * @return true if the disconnect succeeded
	 * @throws IOException
	 */
	public boolean disconnect(final Connection c) {
		System.out.println("ConnectionManager.disconnect(Connection): c=" + c.toString());
		this.sockChanConnMap.remove(c.getSocketChannel());

		// TODO What to do with the orphaned Connection? Probably perform Player
		// object look up and persist the data....
		// CM: I concur; we might put in some penalties too, so this should be
		// abstracted out and made extendible with rules
		// DHL: I am thinking a PersistAndRemoveConnectionJob would be good
		// here.

		c.toString();
		try {
			c.getSocketChannel().close();
		} catch (IOException e) {
			System.err.println("ConnectionManager.disconnect(Connection): Failed to close socket connection.");
			e.printStackTrace();
		}

		System.out.println("ConnectionManager.disconnect(Connection): Total Connections: "
				+ this.sockChanConnMap.size());
		return c.getSocketChannel().isConnected();
	}

	/*
	 * 
	 * 
	 * Connection ID FNs
	 */

	/**
	 * @return Whether or not this ConnMan can accept more connections
	 */
	public boolean canAcceptConnection() {
		return (this.sockChanConnMap.size() < this.maxAllowedConns);
	}

	/**
	 * @return a randomly generated UUID if this connection manager can accept
	 *         connections, otherwise return null;
	 */
	public UUID getNewConnectionID() {
		if (!this.canAcceptConnection()) {
			return null;
		} else {
			return UUID.randomUUID();
		}
	}

	/*
	 * 
	 * 
	 * Data IO
	 */

	/**
	 * Read all data present on key.
	 * 
	 * @param key
	 * @throws IOException
	 */
	private void read(final SelectionKey key) {
		SocketChannel sc = (SocketChannel) key.channel();
		Connection c = this.sockChanConnMap.get(sc);

		// Check to see if we got a handle on a Connection object
		if (c == null) {
			// Strange... no connection associated with that SocketChannel
			try {
				c = this.createNewConnection(sc);
			} catch (IOException e) {
				System.err.println("Failed to create new Connection in ConnectionManager.readIncoming()");
			}
		}

		synchronized (c) {
			// Lets make the selector thread do *some* work.
			c.handleInputFromClient();
		}
	}

	/**
	 * This method is called externally, passing in the SocketChannel to be
	 * written to and the data to be written. This method simply converts a
	 * String to a Byte[] and sends the data on.
	 * 
	 * @param sockChan
	 *            the SocketChannel to be written to
	 * @param text
	 *            the String to send
	 */
	public final void send(final SocketChannel sockChan, String text) {
		this.send(sockChan, text.getBytes());
	}

	/**
	 * This method is called externally, passing in the SocketChannel to be
	 * written to and the data to be written. This method sets up a ConnEvent to
	 * be processed by the selector prior to sending the data.
	 * 
	 * @param sockChan
	 *            the SocketChannel to be written to
	 * @param data
	 *            the data to send
	 */
	public final void send(SocketChannel sockChan, byte[] data) {
		synchronized (this.pendingEvents) {
			// Indicate we want the interest ops set changed
			this.pendingEvents.add(new ConnectionEvent(sockChan, ConnectionEvent.EventType.CHANGEOPS,
					SelectionKey.OP_WRITE));

			// And queue the data we want written
			synchronized (this.socketChannelByteBuffers) {
				List<ByteBuffer> queue = this.socketChannelByteBuffers.get(sockChan);

				// if the retrieved Queue is null, instantiate a new one.
				if (queue == null) {
					queue = new ArrayList<ByteBuffer>();
					this.socketChannelByteBuffers.put(sockChan, queue);
				}
				queue.add(ByteBuffer.wrap(data));
			}
		}
		this.selector.wakeup();
	}

	/**
	 * This method is called by the run() method after the selector is
	 * unblocked. This method performs the actual data write to the
	 * SocketChannel. Also, the associated Key is set back to OP_READ so we
	 * don't waste CPU cycles while it waits for more data to write when there
	 * isn't any coming!
	 * 
	 * @param key
	 *            the key whose SocketChannel we're writing to
	 * @throws IOException
	 *             if unable to write to a SocketChannel
	 */
	private void write(final SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel) key.channel();

		synchronized (this.socketChannelByteBuffers) {
			List<ByteBuffer> queue = this.socketChannelByteBuffers.get(socketChannel);

			// Write until there's not more data ...
			while (!queue.isEmpty()) {
				ByteBuffer buf = queue.get(0);
				socketChannel.write(buf);
				if (buf.remaining() > 0) {
					// ... or the socket's buffer fills up
					break;
				}
				queue.remove(0);
			}

			if (queue.isEmpty()) {
				// We wrote away all data, so we're no longer interested
				// in writing on this socket. Switch back to waiting for
				// data.
				key.interestOps(SelectionKey.OP_READ);
			}
		}
	}

	/*
	 * 
	 * 
	 * Main Loop And Loop Controls
	 */

	/**
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public final void run() {
		this.runStatus = true;
		System.out.println("ConnectionManager: Running.");
		while (this.runCmd) {
			this.processPendingEvents();
			this.blockForSelectOnRegisteredChannels();
			this.handleNewEvents();
		}
		System.out.println("ConnectionManager: Shutting down...");
		this.shutdown();
		System.out.println("ConnectionManager: Shutdown.");
	}

	private void shutdown() {
		this.runStatus = false;
		try {
			for (SocketChannel s : this.sockChanConnMap.keySet()) {
				try {
					s.close();
				} catch (IOException e) {
					System.err.println("ConnectionManager.shutdown() -> SocketChannel.close() failed.");
				}
			}
			this.selector.close();
			this.sockChanConnMap.clear();
			this.socketChannelByteBuffers.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method sets up the thread to run, loads in the ConnectionManager,
	 * sets the ThreadRun Command to true and starts the thread.
	 */
	public final void start() {
		System.out.println("ConnectionManager: Received Startup Command.");
		this.runCmd = true;
		this.runStatus = true;
		this.myThread = new Thread(this, "ConnectionManager-Thread");
		this.myThread.start();
	}

	/**
	 * Sets the thread's run command to false and wakes the selector.
	 */
	public final void stop() {
		System.out.println("ConnectionManager: Received Shutdown Command.");
		this.runCmd = false;
		this.selector.wakeup();
	}

	private void processPendingEvents() {
		synchronized (this.pendingEvents) {
			SelectionKey selKey;

			for (ConnectionEvent connEvent : this.pendingEvents) {
				switch (connEvent.eventType) {
				case CHANGEOPS:
					selKey = connEvent.socket.keyFor(this.selector);

					if (selKey == null) {
						System.out.println(connEvent.toString());
						if (!connEvent.socket.isConnected()) {
							this.disconnect(connEvent.socket);
						}
						continue;
					}

					if (!selKey.isValid()) {
						continue;
					}

					selKey.interestOps(connEvent.ops);
					break;

				// case REGISTER:
				// System.out.println("Register");
				// try {
				// connEvent.socket.register(this.selector, connEvent.ops);
				// } catch (ClosedChannelException e) {
				// System.err.println("Exception: " + e.getMessage());
				// }
				// break;
				}
			}
			this.pendingEvents.clear();
		}
	}

	private void blockForSelectOnRegisteredChannels() {
		this.runStatus = false;
		try {
			this.selector.select();
		} catch (IOException e) {
			System.err.println("Exception: " + e.getMessage());
		}
		this.runStatus = true;
	}

	private void handleNewEvents() {
		SelectionKey key;

		Iterator<SelectionKey> selectedKeys = this.selector.selectedKeys().iterator();
		while (selectedKeys.hasNext()) {
			key = selectedKeys.next();
			selectedKeys.remove();

			try {
				if (!key.isValid()) {
					// disconnect(key);
					continue;
				} else if (key.isAcceptable()) {
					acceptNewConnection(key);
				} else if (key.isReadable()) {
					read(key);
				} else if (key.isWritable()) {
					write(key);

				} else {
					System.err.println("Unhandled keystate: " + key.toString());
				}
			} catch (CancelledKeyException cke) {
				System.err.print(cke.getMessage());
				this.disconnect(key);
			} catch (IOException e) {
				System.err.println("Exception: " + key.toString());
			}
		}
	}

	/*
	 * 
	 * 
	 * ConnectionManager Status getters
	 */

	/**
	 * @return the ConnectionManager's main loop run command
	 */
	public final boolean getRunCmd() {
		return this.runCmd;
	}

	/**
	 * @return the ConnectionManager's main loop run status
	 */
	public final boolean getRunStatus() {
		return this.runStatus;
	}

	/**
	 * @return the Thread this ConnectionManager's is running in.
	 */
	public final Thread getThread() {
		return this.myThread;
	}

}
