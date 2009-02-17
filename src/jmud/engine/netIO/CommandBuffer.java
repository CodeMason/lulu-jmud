package jmud.engine.netIO;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import jmud.engine.core.JMudStatics;

public class CommandBuffer {
	private final static int DEFAULTCAPACITY = 10240;
	private ByteBuffer byteBuf;
	private String strBuf = "";
	private ArrayList<String> completeCmds;

	/**
	 * Default constructor. Sizes the internal ByteByffer to 10240 bytes
	 */
	public CommandBuffer() {
		this(CommandBuffer.DEFAULTCAPACITY);
	}

	/**
	 * Specific Size Constructor. Sizes the internal ByteByffer to
	 * <i>capacity</i> bytes.
	 * 
	 * @param capacity
	 */
	public CommandBuffer(int capacity) {
		this.completeCmds = new ArrayList<String>();

		if (capacity > 0) {
			this.byteBuf = ByteBuffer.allocate(capacity);
		} else {
			this.byteBuf = ByteBuffer.allocate(CommandBuffer.DEFAULTCAPACITY);
		}
	}

	public void writeBytes(byte[] data) {
		if (data.length > this.byteBuf.capacity()) {
			//expand the byteBuf.
			this.byteBuf = ByteBuffer.allocate(data.length);
		}
		
		try {
			this.byteBuf.put(data);
			this.parseBB();
		} catch (BufferOverflowException boe) {
			boe.printStackTrace();
		}
	}

	public void writeBytes(SocketChannel sc) {
		int bRead = 0;
		try {
			do {
				bRead = sc.read(this.byteBuf);
				this.parseBB();
			} while (bRead >= CommandBuffer.DEFAULTCAPACITY);

		} catch (BufferOverflowException boe) {
			boe.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int cmdCount() {
		return this.completeCmds.size();
	}

	public boolean hasNextCommand() {
		return (this.cmdCount() > 0);
	}

	public String getNextCommand() {
		String s = null;
		if (this.hasNextCommand()) {
			s = this.completeCmds.remove(0);
		}
		return s;
	}

	private void parseBB() {

		try {
			// convert the bytebuffer to a string;
			CharBuffer cb = Charset.forName("ISO-8859-1").newDecoder().decode(byteBuf);

			// append the string onto the strBuf
			this.strBuf.concat(cb.toString());

			// clear out our ByteBuffer
			byteBuf.clear();

			// Loop and extract Commands
			while (this.strBuf.contains(JMudStatics.CRLF)) {
				this.extractNextCmd();
			}

		} catch (CharacterCodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void extractNextCmd() {
		int stopPos = this.strBuf.indexOf(JMudStatics.CRLF);

		// strange... no CRLF in the string.
		if (stopPos == -1) {
			return;
		}
		// Pull out the complete command
		String cmd = this.strBuf.substring(0, stopPos);

		// add it to the Array
		this.completeCmds.add(cmd);

		// delete it from strBuf
		this.strBuf = this.strBuf.substring(stopPos + JMudStatics.CRLF.length());

	}

}
