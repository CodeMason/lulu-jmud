package jmud.netIO;

/**
 * ConnectionManager is a Runnable class that manages all Connections.
 * ConnectionManager contains a single NIO selector and all routines for
 * Accepting new connections and handling IO for existing connections.
 * 
 * Opting for a single thread solution for many reasons, all of which can
 * be summed up here:
 * http://rox-xmlrpc.sourceforge.net/niotut/index.html
 * 
 *
 * @author David Loman
 * @version 0.1
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jmud.job.CheckConnBufferForValidCmd_Job;

public class ConnectionManager implements Runnable {
	/* 
	 * ********************************************
	 * Singleton Implementation
	 * ********************************************
	 */	
	/**
	 * Protected constructor is sufficient to suppress unauthorized calls to the
	 * constructor
	 */
	protected ConnectionManager() {
	}

	/**
	 * ConnectionManagerHolder is loaded on the first execution of
	 * ConnectionManager.getInstance() or the first access to
	 * ConnectionManagerHolder.INSTANCE, not before.
	 */
	private static class ConnectionManagerHolder {
		private final static ConnectionManager INSTANCE = new ConnectionManager();
	}

	public static ConnectionManager getInstance() {
		return ConnectionManagerHolder.INSTANCE;
	}



	/* 
	 * ********************************************
	 * Concrete Class Implementation
	 * ********************************************
	 */		
	
	
	private Thread myThread;
	private boolean runStatus = true;
	private boolean runCmd = true;

	// The host:port combination to listen on
	private InetAddress hostAddress;
	private int port;

	// The channel on which we'll accept connections
	private ServerSocketChannel serverChannel;

	// The selector we'll be monitoring
	private Selector selector;

	// A list of pending events 
	private List<ConnEvent> pendingEvents = new LinkedList<ConnEvent>();

	// Maps a SocketChannel to a list of ByteBuffer instances
	private Map<SocketChannel, List<ByteBuffer>> pendingData = new HashMap<SocketChannel, List<ByteBuffer>>();

	// Maps a SocketChannel to a Connection
	private Map<SocketChannel, Connection> connMap = new HashMap<SocketChannel, Connection>();
	private int connCnt = 0;

	
	/* 
	 * ********************************************
	 * Initializer
	 * ********************************************
	 */	
	
	public void init(InetAddress hostAddress, int port) throws IOException {
		this.hostAddress = hostAddress;
		this.port = port;

		// Init the Selector:
		this.selector = SelectorProvider.provider().openSelector();

		// Create a new non-blocking server socket channel
		this.serverChannel = ServerSocketChannel.open();
		this.serverChannel.configureBlocking(false);

		// Bind the server socket to the specified address and port
		InetSocketAddress isa = new InetSocketAddress(this.hostAddress, this.port);
		this.serverChannel.socket().bind(isa);

		// Register the server socket channel, indicating an interest in
		// accepting new connections
		this.serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);

	}

	
	/* 
	 * ********************************************
	 * Runnable.run() Routine
	 * ********************************************
	 */
	@Override
	public void run() {
		this.runStatus = true;
		SelectionKey key = null;

		System.out.println("ConnectionManager: Running.");
		while (this.runCmd) {
			try {

				// Process any pending events
				synchronized (this.pendingEvents) {

					Iterator<ConnEvent> events = this.pendingEvents.iterator();

					while (events.hasNext()) {
						ConnEvent event = (ConnEvent) events.next();
						switch (event.type) {
						case ConnEvent.CHANGEOPS:
							// get a reference to the SelectionKey in the
							// SocketChannel in the ChangeRequest
							key = event.socket.keyFor(this.selector);

							if (key == null) {
								System.out.println(event.toString());
								if (!event.socket.isConnected()) {
									this.disconnect(event.socket);
								}
								continue;
							}

							if (!key.isValid()) {
								System.err.println("BAD KEY");
								continue;
							}

							key.interestOps(event.ops);
							break;
						case ConnEvent.REGISTER:
							event.socket.register(this.selector, event.ops);
							break;
						}
					}
					this.pendingEvents.clear();
				}

				// Wait for an event one of the registered channels
				// THIS BLOCKS
				// Set the runStatus flag to false while blocked
				this.runStatus = false;
				this.selector.select();
				this.runStatus = true;

				// Iterate over the set of keys for which events are available
				Iterator<SelectionKey> selectedKeys = this.selector.selectedKeys().iterator();
				while (selectedKeys.hasNext()) {
					key = selectedKeys.next();
					selectedKeys.remove();

					if (!key.isValid()) {
						// if its a bad key, then attempt to drop the connection
						// TODO Do we want to actually do this here? TESTPOINT
						this.disconnect(key);
					}

					// Check what event is available and deal with it
					if (key.isAcceptable()) {
						this.acceptNewConnection(key);
						
					} else if (key.isReadable()) {
						this.readIncoming(key);
						
					} else if (key.isWritable()) {
						this.writeOutgoing(key);
						
					} else {
						System.err.println("Unhandled keystate: " + key.toString());
					}
				}
			} catch (CancelledKeyException cke) {
				System.err.print(cke.getMessage());
				try {
					this.disconnect(key);
				} catch (IOException e) {
					System.err.println("During CancelledKeyException, a key failed to disconnect.");
				}
			} catch (Exception e) {
				System.err.println(key.toString());
			}
		}
		// End the main run loop.
		System.out.println("ConnectionManager: Shutting down...");

		// SHutdown stuff:
		this.runStatus = false;

		Set<SocketChannel> ssc = this.connMap.keySet();

		for (SocketChannel s : ssc) {
			try {
				s.close();
			} catch (IOException e) {
				System.err.println("SocketChannel.close() failed.");
			}
		}

		System.out.println("ConnectionManager: Shutdown.");
	}

	
	
	/* 
	 * ********************************************
	 * I/O Routines
	 * ********************************************
	 */
	private void readIncoming(SelectionKey key) throws IOException {

		// Obtain handle on the passed SocketChannel from 'key'
		SocketChannel sockChan = (SocketChannel) key.channel();

		// Look up to see if we have a mapping to a connection:
		Connection c = this.connMap.get(sockChan);

		if (c == null) {
			// Deal with null Connection by creating new connection
			this.CreateNewConnection(sockChan);
		}

		/*
		 * Attempt to read data from the SocketChannel to the Connection's
		 * ByteBuffer. If the socket channel activates it's key but doesn't send
		 * any data it means that the connection was dropped.
		 */
		if (sockChan.read(c.getReadBuffer()) < 0) {
			System.out.println("CommandListenerThread: Lost connection");

			// close the dropped connection
			this.disconnect(sockChan);
			return;
		}

		// Now submit a job to see if this connection has a valid command stored
		// yet.
		CheckConnBufferForValidCmd_Job job = new CheckConnBufferForValidCmd_Job(c);

		// Submit next Job
		job.submitSelf();

		return;
	}

	/**
	 * This method is called externally, passing in the SocketChannel to be written to 
	 * and the data to be written.  This method sets up a ConnEvent to be processed by
	 * the selector prior to sending the data.
	 * 
	 * @param sockChan
	 * @param data
	 */
	public void send(SocketChannel sockChan, byte[] data) {
		synchronized (this.pendingEvents) {
			// Indicate we want the interest ops set changed
			this.pendingEvents.add(new ConnEvent(sockChan, ConnEvent.CHANGEOPS, SelectionKey.OP_WRITE));

			// And queue the data we want written
			synchronized (this.pendingData) {
				List<ByteBuffer> queue = this.pendingData.get(sockChan);

				// if the retrieved Queue is null, instantiate a new one.
				if (queue == null) {
					queue = new ArrayList<ByteBuffer>();
					this.pendingData.put(sockChan, queue);
				}
				queue.add(ByteBuffer.wrap(data));
			}
		}

		/*
		 * Finally, wake up our selecting thread
		 * so it can make the required changes
		 */
		this.selector.wakeup();
	}

	/**
	 * This method is called by the run() method after the selector is unblocked.  This method performs
	 * the actual data write to the SocketChannel.  Also, the associated Key is set back to OP_READ so
	 * we don't waste CPU cycles while it waits for more data to write when there isnt any coming!
	 * 
	 * @param key
	 * @throws IOException
	 */
	private void writeOutgoing(SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel) key.channel();

		synchronized (this.pendingData) {
			List<ByteBuffer> queue = this.pendingData.get(socketChannel);

			// Write until there's not more data ...
			while (!queue.isEmpty()) {
				ByteBuffer buf = (ByteBuffer) queue.get(0);
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
	 * ********************************************
	 * New Connection Routines
	 * ********************************************
	 */
	private void acceptNewConnection(SelectionKey key) throws IOException {

		// For an accept to be pending the channel must be a server socket
		// channel.
		ServerSocketChannel servSockChan = (ServerSocketChannel) key.channel();

		// Accept the connection and make it non-blocking
		SocketChannel sockChan = servSockChan.accept();
		sockChan.configureBlocking(false);

		// Register the new SocketChannel with our Selector, indicating
		// we'd like to be notified when there's data waiting to be read
		sockChan.register(this.selector, SelectionKey.OP_READ);

		// Make and register a new Connection Object
		Connection c = this.CreateNewConnection(sockChan);
		this.connMap.put(sockChan, c);
	}

	private Connection CreateNewConnection(SocketChannel sockChan) {
		// Make and register a Connection
		String s = "Connection:" + Integer.toString(this.getNewConnectionNumber());

		System.out.println("ConnectionManager: New Connection.  Name: " + s);

		Connection c = new Connection(sockChan);
		this.connMap.put(sockChan, c);
		return c;
	}

	private int getNewConnectionNumber() {
		//TODO the Max Connections isn't handled correctly.  Need to block new connections.
		
		// JUST in case we have a TON of connections....
		if (this.connCnt == Integer.MAX_VALUE) {
			this.connCnt = 0;
		}
		return this.connCnt++;
	}

	
	/* 
	 * ********************************************
	 * Disconnect Routines
	 * ********************************************
	 */
	
	/**
	 * Disconnect by Connection object.
	 * 
	 * @param c
	 * @return
	 * @throws IOException
	 */
	public boolean disconnectFrom(Connection c) throws IOException {
		System.out.println("ConnectionManager.disconnect(Connection): c=" + c.toString());
		SocketChannel sockChan = c.getSc();
		return this.disconnect(sockChan);
	}

	/**
	 * Disconnect by Key object
	 * 
	 * @param key
	 * @return
	 * @throws IOException
	 */
	private boolean disconnect(SelectionKey key) throws IOException {
		System.out.println("ConnectionManager.disconnect(SelectionKey): key=" + key.toString());
		this.disconnect((SocketChannel) key.channel());

		key.cancel();
		return key.isValid();
	}

	/**
	 * Disconnect by SocketChannel
	 * 
	 * @param sockChan
	 * @return
	 * @throws IOException
	 */
	private boolean disconnect(SocketChannel sockChan) throws IOException {
		System.err.println("ConnectionManager.disconnect(SocketChannel): sockChan=" + sockChan.toString());

		@SuppressWarnings("unused")
		Connection c = this.connMap.remove(sockChan);
		// TODO What to do with the orphaned Connection? Probably perform Player
		// object look up and persist the data....

		sockChan.close();
		return sockChan.isConnected();
	}


	
	
	
	/* 
	 * ********************************************
	 * Runnable Implementation Routines
	 * ********************************************
	 */
	public Thread getThread() {
		return this.myThread;
	}

	public boolean getRunStatus() {
		return this.runStatus;
	}

	public boolean getRunCmd() {
		return this.runCmd;
	}

	public void start() {
		System.out.println("ConnectionManager: Received Startup Command.");
		this.runCmd = true;
		this.runStatus = true;
		this.myThread = new Thread(this, "ConnectionManager-Thread");
		this.myThread.start();
	}

	public void stop() {
		System.out.println("ConnectionManager: Received Shutdown Command.");
		this.runCmd = false;
		this.selector.wakeup();
	}


}
