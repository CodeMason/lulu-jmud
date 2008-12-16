package jmud.engine.netIO;

import java.nio.channels.SocketChannel;

/**
 * @author David Loman
 */
public class ConnectionEvent{

    public SocketChannel socket;
    public EventType eventType;
    public int ops;

    public ConnectionEvent(SocketChannel inSocket, EventType inType, int inOps){
        this.socket = inSocket;
        this.eventType = inType;
        this.ops = inOps;
    }

    public static enum EventType{
        REGISTER,
        CHANGEOPS
    }
}
