package jmud.engine.netIO;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

import jmud.engine.core.JMudStatics;

public class CommandBuffer {

	private ArrayList<Character> charBuf;
	private ArrayList<String> completeCmds;

	/**
	 * Default constructor.
	 */
	public CommandBuffer() {
		this.completeCmds = new ArrayList<String>();
		this.charBuf = new ArrayList<Character>();
	}

	public void write(byte[] data) throws IOException {
		this.write(data, data.length);
	}

	public void write(byte[] data, int dataLen) throws IOException {

		// ID-10-T check
		if (data.length < 1) {
			return;
		}
		if (dataLen > data.length) {
			dataLen = data.length;
		}

		char c;

		for (int i = 0; i < dataLen; ++i) {
			c = (char) data[i]; // Here we simply cast our 8bit Char to a 16-bit
			// char

			synchronized (this.charBuf) {
				this.charBuf.add(c);
			}
		}

	}

	public void write(SocketChannel sc) throws ClosedChannelException {
		try {
			// Byte here represents a 8Bit char
			ByteBuffer bb = ByteBuffer.allocate(128);

			int read = sc.read(bb);
			if (read == -1) {
				throw new IOException();
			}
			while (read > 0) {
				bb.flip();

				this.write(bb.array(), bb.limit());
				bb.clear();
				read = sc.read(bb);
				if (read == -1) {
					throw new IOException();
				}
			}

		} catch (IOException e) {
			// Transform the IOException into a ClosedChannelExcpetion and throw
			// it.
			throw new ClosedChannelException();
		}
	}

	public void write(InputStream is) throws IOException {
		DataInputStream dis;
		char c;

		try {
			dis = new DataInputStream(is);
			while (dis.available() >= 2) {
				c = dis.readChar();
				System.out.println("CommandBuffer.write() new char: '" + c + "'");
				synchronized (this.charBuf) {
					this.charBuf.add(c);
				}
			}
		} catch (EOFException eofe) {
			// Read all there is to read!
			return;
		}
	}

	public void parseBuffer() {
		int crIndex;
		int i;
		String cmd;
		char[] ca;

		// Obtain lock on the charBuf
		synchronized (this.charBuf) {
			// Check to see if there is a CR:
			crIndex = this.charBuf.indexOf(JMudStatics.CR);

			// no complete command yet.
			if (crIndex == -1) {
				return;
			}

			while (crIndex != -1) {

				// Now check to see if there its a CRLF or just a CR:
				// Make sure that crIndex isn't at the end of the array
				// (prevents array index overrun)
				if (crIndex < (this.charBuf.size() - 1)) {
					// And if crIndex + 1 is a LF, then delete it.
					if (this.charBuf.get(crIndex + 1) == JMudStatics.LF) {
						this.charBuf.remove(crIndex + 1);
					}
				}

				ca = new char[crIndex + 1];

				for (i = 0; i <= crIndex; ++i) {
					ca[i] = this.charBuf.remove(0);
				}

				cmd = new String(ca);
				cmd = cmd.replace(JMudStatics.CR + "", "");
				cmd = cmd.replace(JMudStatics.LF + "", "");
				
				System.out.println("CommandBuffer.Parse() new command: '" + cmd.replace("\r", "").replace("\n", "") + "'");
				// Lock on the command list:
				synchronized (this.completeCmds) {
					this.completeCmds.add(cmd);
				}

				// prep for next loop
				crIndex = this.charBuf.indexOf(JMudStatics.CR);
			}
		}
	}

	public int cmdCount() {
		synchronized (this.completeCmds) {
			return this.completeCmds.size();
		}
	}

	public boolean hasNextCommand() {
		return (this.cmdCount() > 0);
	}

	public String getNextCommand() {
		String s = "";
		if (this.hasNextCommand()) {
			synchronized (this.completeCmds) {
				s += this.completeCmds.remove(0);
			}
		}
		return s;
	}
	
	public String toString() {
		String out = "";
		out+= "CharacterBuffer is: " + this.charBuf.size() + " characters long.\n";
		out += "Completed Command Queue:\n";
		for (String s : this.completeCmds) {
			out += "\t-" + s.replace("\r", "\\r").replace("\n","\\n") + "\n";
		}
				
		return out;
	}

}
