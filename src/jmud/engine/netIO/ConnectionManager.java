package jmud.engine.netIO;

import jmud.engine.job.definitions.ProcessIncomingDataJob;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;

/**
 * ConnectionManager is a Runnable, singleton class that manages all
 * Connections. ConnectionManager contains a single NIO selector and all
 * routines for: -Accepting new connections -Handling IO for existing
 * connections -Disconnecting connections.
 *
 * We are opting for a single thread/single selector solution for many reasons,
 * all of which can be summed up here:
 * http://rox-xmlrpc.sourceforge.net/niotut/index.html
 *
 * @author David Loman
 * @version 0.1
 */
public class ConnectionManager implements Runnable{
    private static final class LazyLoader{
        private static final ConnectionManager LAZY_LOADED_INSTANCE = new ConnectionManager();

        private LazyLoader(){
            // Singleton
        }
    }

    public static ConnectionManager getLazyLoadedInstance(){
        return LazyLoader.LAZY_LOADED_INSTANCE;
    }

    private boolean isRunning = true;
    private boolean shouldRunCommand = true;
    private int connectionCount;
    private Thread myThread;
    private Selector selectorToMonitor;
    private final List<ConnectionEvent> pendingEvents = new LinkedList<ConnectionEvent>();
    private final Map<SocketChannel, List<ByteBuffer>> socketChannelByteBuffers = new HashMap<SocketChannel, List<ByteBuffer>>();
    private final Map<SocketChannel, Connection> socketChannelConnections = new HashMap<SocketChannel, Connection>();

    protected ConnectionManager(){
        //Singleton
    }

    private void acceptNewConnection(final SelectionKey key) throws IOException{
        SocketChannel socketChannel = getIncomingSocketChannel(key);
        configureSocketChannel(socketChannel);
        createAndRegisterConnection(socketChannel);

        System.out.println("ConnectionManager: Total Connections: " + this.socketChannelConnections.size());
    }

    private void createAndRegisterConnection(SocketChannel socketChannel){
        Connection connection = CreateNewConnection(socketChannel);
        this.socketChannelConnections.put(socketChannel, connection);
    }


    private void configureSocketChannel(SocketChannel sockChan) throws IOException{
        sockChan.configureBlocking(false);
        sockChan.register(this.selectorToMonitor, SelectionKey.OP_READ);
    }

    private SocketChannel getIncomingSocketChannel(SelectionKey key) throws IOException{
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        return serverSocketChannel.accept();
    }

    private Connection CreateNewConnection(final SocketChannel socketChannel){
        String s = "Connection:" + Integer.toString(this.getNewConnectionNumber());
        System.out.println("ConnectionManager: New Connection.  Name: " + s);

        Connection connection = new Connection(socketChannel, s);
        connection.setConnState(ConnectionState.ConnectedButNotLoggedIn);
        connection.sendSplashScreen();
        return connection;
    }

    /**
     * Disconnect by Key object.
     *
     * @param key the key to disconnect
     * @return true if the disconnect succeeded
     * @throws IOException
     */
    private boolean disconnect(final SelectionKey key) throws IOException{
        System.out.println("ConnectionManager.disconnect(SelectionKey): key=" + key.toString());
        this.disconnect((SocketChannel) key.channel());

        key.cancel();
        return key.isValid();
    }

    /**
     * Disconnect by SocketChannel.
     *
     * @param sockChan the SocketChannel to disconnect
     * @return true if the disconnect succeeded
     * @throws IOException
     */
    private boolean disconnect(final SocketChannel sockChan){
        System.out.println("ConnectionManager.disconnect(SocketChannel): sockChan="
                + sockChan.toString());

        // noinspection UnusedDeclaration
        Connection c = this.socketChannelConnections.remove(sockChan);
        // TODO What to do with the orphaned Connection? Probably perform Player
        // object look up and persist the data....
        // CM: I concur; we might put in some penalties too, so this should be
        // abstracted out
        // and made extendable with rules
        c.toString();
        try{
            sockChan.close();
        } catch(IOException e){
            System.err
                    .println("ConnectionManager.disconnect(SocketChannel): Failed to close socket connection.");
            e.printStackTrace();
        }
        this.socketChannelConnections.remove(sockChan);
        System.out.println("ConnectionManager: Total Connections: " + this.socketChannelConnections.size());
        return sockChan.isConnected();
    }

    /**
     * Disconnect by Connection object.
     *
     * @param connection The connection to disconnect
     * @return true if the disconnection succeeded
     * @throws IOException
     */
    public final boolean disconnectFrom(final Connection connection){
        System.out.println("ConnectionManager.disconnect(Connection): c=" + connection.toString());

        return connection.disconnect();
    }

    /**
     * @return the number of current connections.
     */
    private int getNewConnectionNumber(){
        // TODO the Max Connections isn't handled correctly. Need to block new
        // connections.

        // JUST in case we have a TON of connections....
        if(this.connectionCount == Integer.MAX_VALUE){
            this.connectionCount = 0;
        }
        return this.connectionCount++;
    }

    /**
     * @return the ConnectionManager's main loop run command
     */
    public final boolean getShouldRunCommand(){
        return this.shouldRunCommand;
    }

    /**
     * @return the ConnectionManager's main loop run status
     */
    public final boolean getRunning(){
        return this.isRunning;
    }

    /**
     * @return the Thread this ConnectionManager's is running in.
     */
    public final Thread getThread(){
        return this.myThread;
    }

    /**
     * Initialize the ConnectionManager to these values:
     *
     * @param hostAddress
     * @param port
     * @throws IOException
     */
    public final void init(InetAddress hostAddress, int port) throws IOException{
        this.selectorToMonitor = SelectorProvider.provider().openSelector();
        createBindAndRegisterServerSocketChannel(hostAddress, port);
    }

    private void createBindAndRegisterServerSocketChannel(InetAddress hostAddress, int port) throws IOException{
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress(hostAddress, port);
        serverChannel.socket().bind(inetSocketAddress);
        serverChannel.register(this.selectorToMonitor, SelectionKey.OP_ACCEPT);
    }

    /**
     * Read all data present on key.
     *
     * @param key
     */
    private void readIncoming(final SelectionKey key){

        SocketChannel sockChan = (SocketChannel) key.channel();

        Connection c = getOrCreateConnection(sockChan);

        if(isConnectionLostAndDisconnected(c)){
            return;
        }

        processIncomingData(c);
    }

    private Connection getOrCreateConnection(SocketChannel socketChannel){
        Connection connection = this.socketChannelConnections.get(socketChannel);
        if(connection == null){
            connection = this.CreateNewConnection(socketChannel);
        }
        return connection;
    }

    public boolean isConnectionLostAndDisconnected(Connection connection){
        try{
            if(connection.isConnectionLost()){
                System.out.println("CommandListenerThread: Lost connection");

                connection.disconnect();
                return true;
            }
        } catch(IOException e){
            e.printStackTrace();
        }

        return false;
    }

    private void processIncomingData(Connection c){
        ProcessIncomingDataJob pidj = new ProcessIncomingDataJob(c);
        pidj.submitSelf();
    }

    /**
     * @see java.lang.Runnable#run()
     */
    @Override
    public final void run(){
        this.isRunning = true;

        System.out.println("ConnectionManager: Running.");

        while(this.shouldRunCommand){
            processPendingEvents();

            blockForSelectOnRegisteredChannels();

            handleSelectorKeyEvents();
        }

        System.out.println("ConnectionManager: Shutting down...");

        shutDown();

        System.out.println("ConnectionManager: Shutdown.");
    }

    private void shutDown(){
        this.isRunning = false;

        closeSocketChannels();

        try{
            this.selectorToMonitor.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void closeSocketChannels(){
        for(SocketChannel s : this.socketChannelConnections.keySet()){
            try{
                s.close();
            } catch(IOException e){
                System.err.println("SocketChannel.close() failed.");
            }
        }
    }

    private void handleSelectorKeyEvents(){
        SelectionKey key;
        Iterator<SelectionKey> selectedKeys = this.selectorToMonitor.selectedKeys().iterator();
        while(selectedKeys.hasNext()){
            key = selectedKeys.next();
            selectedKeys.remove();

            try{
                if(!key.isValid()){
                    disconnect(key);
                }else if(key.isAcceptable()){
                    acceptNewConnection(key);
                } else if(key.isReadable()){
                    readIncoming(key);
                } else if(key.isWritable()){
                    writeOutgoing(key);

                } else{
                    System.err.println("Unhandled keystate: " + key.toString());
                }
            } catch(CancelledKeyException cke){
                System.err.print(cke.getMessage());
                try{
                    this.disconnect(key);
                } catch(IOException e){
                    System.err.println("During CancelledKeyException, a key failed to disconnect.");
                }
            }catch(IOException e){
                System.err.println("Exception: " + key.toString());
            }
        }
    }

    private void blockForSelectOnRegisteredChannels(){
        this.isRunning = false;

        try{
            this.selectorToMonitor.select();
        }catch(IOException e){
            System.err.println("Exception: " + e.getMessage());
        }

        this.isRunning = true;
    }

    private void processPendingEvents(){
        synchronized(this.pendingEvents){
            SelectionKey selectionKey;

            for(ConnectionEvent connectionEvent : this.pendingEvents){
                switch(connectionEvent.eventType){
                    case CHANGEOPS:
                        selectionKey = connectionEvent.socket.keyFor(this.selectorToMonitor);

                        if(selectionKey == null){
                            System.out.println(connectionEvent.toString());
                            if(!connectionEvent.socket.isConnected()){
                                this.disconnect(connectionEvent.socket);
                            }
                            continue;
                        }

                        if(!selectionKey.isValid()){
                            continue;
                        }

                        selectionKey.interestOps(connectionEvent.ops);
                        break;

                    case REGISTER:
                        try{
                            connectionEvent.socket.register(this.selectorToMonitor, connectionEvent.ops);
                        }catch(ClosedChannelException e){
                            System.err.println("Exception: " + e.getMessage());
                        }
                        break;
                }
            }
            this.pendingEvents.clear();
        }
    }

    /**
     * This method is called externally, passing in the SocketChannel to be
     * written to and the data to be written. This method simply converts a
     * String to a Byte[] and sends the data on.
     *
     * @param sockChan the SocketChannel to be written to
     * @param text     the String to send
     */
    public final void send(final SocketChannel sockChan, String text){
        this.send(sockChan, text.getBytes());
    }

    /**
     * This method is called externally, passing in the SocketChannel to be
     * written to and the data to be written. This method sets up a ConnEvent to
     * be processed by the selector prior to sending the data.
     *
     * @param sockChan the SocketChannel to be written to
     * @param data     the data to send
     */
    public final void send(SocketChannel sockChan, byte[] data){
        synchronized(this.pendingEvents){
            // Indicate we want the interest ops set changed
            this.pendingEvents.add(new ConnectionEvent(sockChan, ConnectionEvent.EventType.CHANGEOPS, SelectionKey.OP_WRITE));

            // And queue the data we want written
            synchronized(this.socketChannelByteBuffers){
                List<ByteBuffer> queue = this.socketChannelByteBuffers.get(sockChan);

                // if the retrieved Queue is null, instantiate a new one.
                if(queue == null){
                    queue = new ArrayList<ByteBuffer>();
                    this.socketChannelByteBuffers.put(sockChan, queue);
                }
                queue.add(ByteBuffer.wrap(data));
            }
        }

        this.selectorToMonitor.wakeup();
    }

    /**
     * This method sets up the thread to run, loads in the ConnectionManager,
     * sets the ThreadRun Command to true and starts the thread.
     */
    public final void start(){
        System.out.println("ConnectionManager: Received Startup Command.");
        this.shouldRunCommand = true;
        this.isRunning = true;
        this.myThread = new Thread(this, "ConnectionManager-Thread");
        this.myThread.start();
    }

    /**
     * Sets the thread's run command to false and wakes the selector.
     */
    public final void stop(){
        System.out.println("ConnectionManager: Received Shutdown Command.");
        this.shouldRunCommand = false;
        this.selectorToMonitor.wakeup();
    }

    /**
     * This method is called by the run() method after the selector is
     * unblocked. This method performs the actual data write to the
     * SocketChannel. Also, the associated Key is set back to OP_READ so we
     * don't waste CPU cycles while it waits for more data to write when there
     * isn't any coming!
     *
     * @param key the key whose SocketChannel we're writing to
     * @throws IOException if unable to write to a SocketChannel
     */
    private void writeOutgoing(final SelectionKey key) throws IOException{
        SocketChannel socketChannel = (SocketChannel) key.channel();

        synchronized(this.socketChannelByteBuffers){
            List<ByteBuffer> queue = this.socketChannelByteBuffers.get(socketChannel);

            // Write until there's not more data ...
            while(!queue.isEmpty()){
                ByteBuffer buf = queue.get(0);
                socketChannel.write(buf);
                if(buf.remaining() > 0){
                    // ... or the socket's buffer fills up
                    break;
                }
                queue.remove(0);
            }

            if(queue.isEmpty()){
                // We wrote away all data, so we're no longer interested
                // in writing on this socket. Switch back to waiting for
                // data.
                key.interestOps(SelectionKey.OP_READ);
            }
        }
    }

}
