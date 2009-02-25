package jmud.netIO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DummyClient {
	private Socket sock;
	private DataInputStream dis;
	private DataOutputStream dos;
	
	public DummyClient() {		
	}

	public void close() throws IOException {
		sock.close();
		this.dis = null;
		this.dos = null;
	}

	public boolean connect(String addy, int port) {
		try {
			
			this.sock = new Socket(addy, port);
			this.dis = new DataInputStream(this.sock.getInputStream());
			this.dos = new DataOutputStream(this.sock.getOutputStream());

		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public Socket getSock() {
		return sock;
	}

	public DataInputStream getDis() {
		return dis;
	}

	public DataOutputStream getDos() {
		return dos;
	}

	
	
}
