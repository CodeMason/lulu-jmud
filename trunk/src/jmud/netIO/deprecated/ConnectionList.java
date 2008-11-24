package jmud.netIO.deprecated;

import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A list of SocketChannels and their associated Selector
 *
 * Provides an easy way to group connections with a Selector.
 *
 * Notes: Create a ConnectionList with a Selector and then use
 *        push, remove, and remove first to add or remove connected
 *        SocketChannels and attach/detach them to/from their Selector.
 *
 * @author Chris maguire
 */
public class ConnectionList<T> {
    private LinkedList<T> connections = new LinkedList<T>();
    private Selector selectorToNotify;

    /*
    * Construct a new Connection connections which will
    * attach SocketChannels to the specified Selector
    */
    public ConnectionList(Selector sel) {
        this.selectorToNotify = sel;
    }

    /*
    * Add a SocketChannel to the connections and
    * wake up the selector.
    *
    * How does the SocketChannel get registered with the Selector?
    */
    public synchronized void push(T newlyConnectedChannel) {
        connections.add(newlyConnectedChannel);
        selectorToNotify.wakeup();
    }

    /*
    * Remove and return the first SocketChannel in the list of connections.
    *
    * How does the SocketChannel get unregistered from the Selector?
    */
    public synchronized T removeFirst() {
        return connections.isEmpty() ? null : connections.removeFirst();
    }

    /*
    * Remove a specified SocketChannel from the list of connections
    *
    * How does the SocketChannel get unregistered from the Selector?
    */
    public synchronized void remove(T connection) {
        Iterator i = connections.iterator();
        while(i.hasNext()) {
            if(i.next() == connection) {
                i.remove();
            }
        }
    }
}