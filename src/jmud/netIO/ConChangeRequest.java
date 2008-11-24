package jmud.netIO;

import java.nio.channels.SocketChannel;

public class ConChangeRequest {
  public static final int REGISTER = 1;
  public static final int CHANGEOPS = 2;
  
  public SocketChannel socket;
  public int type;
  public int ops;
  
  public ConChangeRequest(SocketChannel socket, int type, int ops) {
    this.socket = socket;
    this.type = type;
    this.ops = ops;
  }
}