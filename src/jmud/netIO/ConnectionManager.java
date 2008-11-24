package jmud.netIO;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
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

public class ConnectionManager implements Runnable {
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

	// The buffer into which we'll read data when it's available
	private ByteBuffer readBuffer = ByteBuffer.allocate(8192);

	// A list of PendingChange instances
	private List<ConChangeRequest> pendingChanges = new LinkedList<ConChangeRequest>();

	// Maps a SocketChannel to a list of ByteBuffer instances
	private Map<SocketChannel, List<ByteBuffer>> pendingData = new HashMap<SocketChannel, List<ByteBuffer>>();

	// Maps a SocketChannel to a Connection
	private Map<SocketChannel, Connection> conns = new HashMap<SocketChannel, Connection>();
	private int portalCnt = 0;

	/*
	 * CONSTRUCTORS
	 */
	public ConnectionManager(InetAddress hostAddress, int port) throws IOException {
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
	 * Runnable Overrides
	 */
	@Override
	public void run() {
		this.runStatus = true;
		System.out.println("ConnectionManager: Running.");
		SelectionKey key = null;

		while (this.runCmd) {
			try {

				// Process any pending changes
				synchronized (this.pendingChanges) {

					Iterator<ConChangeRequest> changes = this.pendingChanges.iterator();

					while (changes.hasNext()) {
						ConChangeRequest change = (ConChangeRequest) changes.next();
						switch (change.type) {
						case ConChangeRequest.CHANGEOPS:
							// get a ref to the SelectionKey in the
							// SocketChannel in the ChangeRequest
							key = change.socket.keyFor(this.selector);

							if (key == null) {
								System.out.println(change.toString());
								if (!change.socket.isConnected()) {
									this.disconnect(change.socket);
								}
								continue;
							}

							if (!key.isValid()) {
								System.err.println("BAD KEY");
								continue;
							}

							key.interestOps(change.ops);
							break;
						case ConChangeRequest.REGISTER:
							change.socket.register(this.selector, change.ops);
							break;
						}
					}
					this.pendingChanges.clear();
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
						this.disconnect(key);
					}

					// Check what event is available and deal with it
					if (key.isAcceptable()) {
						this.accept(key);
					} else if (key.isReadable()) {
						this.read(key);
					} else if (key.isWritable()) {
						this.write(key);
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

		// SHutdown stuff:
		this.runStatus = false;

		Set<SocketChannel> ssc = this.conns.keySet();

		for (SocketChannel s : ssc) {
			try {
				s.close();
			} catch (IOException e) {
				System.err.println("SocketChannel.close() failed.");
			}
		}

		System.out.println("ConnectionManager: Shutdown.");
	}

	private void read(SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel) key.channel();

		// Clear out our read buffer so it's ready for new data
		this.readBuffer.clear();

		// Attempt to read off the channel
		int numRead;
		try {
			numRead = socketChannel.read(this.readBuffer);
		} catch (IOException e) {
			// The remote forcibly closed the connection, cancel
			// the selection key and close the channel.
			key.cancel();
			socketChannel.close();
			return;
		}

		if (numRead == -1) {
			// Remote entity shut the socket down cleanly. Do the
			// same from our end and cancel the channel.
			this.disconnect(key);
			return;
		}

		Connection p = this.conns.get(key.channel());
		if (p == null) {
			System.err.println("Null Connection on Lookup.");
			return;
		}

		byte[] readdata = this.readBuffer.array();

		// TODO FINISH LINKING THE DATA OUTPUT FROM ConMan

		return;
	}

	public void send(SocketChannel sockChan, byte[] data) {
		synchronized (this.pendingChanges) {
			// Indicate we want the interest ops set changed
			this.pendingChanges.add(new ConChangeRequest(sockChan, ConChangeRequest.CHANGEOPS,
					SelectionKey.OP_WRITE));

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

		// Finally, wake up our selecting thread so it can make the required
		// changes
		this.selector.wakeup();
	}

	private void write(SelectionKey key) throws IOException {
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

	private void accept(SelectionKey key) throws IOException {

		// For an accept to be pending the channel must be a server socket
		// channel.
		ServerSocketChannel servSockChan = (ServerSocketChannel) key.channel();

		// Accept the connection and make it non-blocking
		SocketChannel sockChan = servSockChan.accept();
		Socket sock = sockChan.socket();
		sockChan.configureBlocking(false);
		sock.toString();

		// Register the new SocketChannel with our Selector, indicating
		// we'd like to be notified when there's data waiting to be read
		sockChan.register(this.selector, SelectionKey.OP_READ);

		// Make and register a portal
		@SuppressWarnings("unused")
		Connection p = this.CreateNewConnection(sockChan);
	}

	public boolean disconnectFrom(String host) throws IOException {
		System.out.println("ConnectionManager: Inside DisconnectFrom(Connection)");

		synchronized (this.conns) {
			for (SocketChannel sc : this.conns.keySet()) {
				Connection p = this.conns.get(sc);
				// if (p.getRemoteHostName().equals(host)) {
				// return this.disconnectFrom(p);
				// }
			}
		}

		return false;
	}

	public boolean disconnectFrom(Connection p) throws IOException {

		// this.conns.remove(p.getSockChan());
		// this.getLocalMS().UnRegisterConnection(p);
		//
		// StdMsg smout =
		// this.getLocalMS().generateNewMsg(StdMsgTypes.mtGoodBye,
		// "Connection2ConnectionComms");
		// p.SendToRemHost(smout);
		// p.getSockChan().close();

		return false;
	}

	public boolean disconnectFrom(InetAddress ip, int Port) throws IOException {

		// for (Connection p : this.conns.values()) {
		// if (ip.equals(p.getSockChan().socket().getInetAddress())
		// && Port == p.getSockChan().socket().getPort()) {
		// return this.disconnectFrom(p);
		//				
		// }
		// }

		return false;
	}

	private boolean disconnect(SelectionKey key) throws IOException {
		System.out.println("ConnectionManager: Inside Disconnect(SelectionKey)");

		this.disconnect((SocketChannel) key.channel());

		key.channel().close();
		key.cancel();
		return key.isValid();
	}

	private boolean disconnect(SocketChannel sockChan) throws IOException {
		System.out.println("ConnectionManager: Inside Disconnect(SocketChannel)");
		System.err.println("Disconnect(SocketChannel)");

		Connection p = this.conns.remove(sockChan);
		// this.getLocalMS().UnRegisterConnection(p);

		return sockChan.isConnected();

	}

	private Connection CreateNewConnection(SocketChannel sockChan) {
		// Make and register a portal
		String s = "Connection:" + Integer.toString(this.getNewConnectionNumber());

		System.out.println("ConnectionManager: New portal.  Name: " + s);

		Connection c = new Connection(sockChan);
		this.conns.put(sockChan, c);
		return c;
	}

	private int getNewConnectionNumber() {
		// JUST incase we have a TON of connections....
		if (this.portalCnt == Integer.MAX_VALUE) {
			this.portalCnt = 0;
		}
		return this.portalCnt++;
	}

	public Thread getThread() {
		return this.myThread;
	}

	public boolean getRunStatus() {
		return this.runStatus;
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

	public boolean getRunCmd() {
		return this.runCmd;
	}

}
