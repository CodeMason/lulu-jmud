package jmud.engine.netIO;

import java.nio.channels.SocketChannel;

/**
 * @author David Loman
 */
public class ConnEvent {
   public static final int REGISTER = 1;
   public static final int CHANGEOPS = 2;

   public SocketChannel socket;
   public int type;
   public int ops;

   public ConnEvent(final SocketChannel inSocket, final int inType,
         final int inOps) {
      this.socket = inSocket;
      this.type = inType;
      this.ops = inOps;
   }
}
