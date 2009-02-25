package jmud.engine.netio;

import jmud.engine.core.JMudStatics;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

public class CommandBuffer {
    private ArrayList<Character> charBuffer;
    private ArrayList<String> completeCmds;

    /**
     * Default constructor.
     */
    public CommandBuffer() {
        this.completeCmds = new ArrayList<String>();
        this.charBuffer = new ArrayList<Character>();
    }

    public void write(byte[] data) throws IOException {
        this.write(data, data.length);
    }

    public void write(byte[] bytes, int length) throws IOException {

        if (bytes.length < 1) {
            return;
        }

        if (length > bytes.length) {
            length = bytes.length;
        }

        char c = 0;
        char lastc = 0;
        
        for (int i = 0; i < length; ++i) {
            c = (char) bytes[i];

            //Convert NL to CR only if the last char wasn't a CR
            if (c == JMudStatics.NL) {
            	if (lastc != JMudStatics.CR) {
            		//if the last character wasn't a NL, make this one a CR
            		c = JMudStatics.CR;
            	} else {
            		//But if the last char WAS a nl, we 
            		//don't want to put two CR's in a row
            		continue;
            	}
            }
            
            synchronized (this.charBuffer) {
                this.charBuffer.add(c);
            }
            lastc = c;
        }

    }

    public void readFrom(SocketChannel socketChannel) throws ClosedChannelException {
        try {
            ByteBuffer eightBitCharBuffer = ByteBuffer.allocate(128);

            int read = socketChannel.read(eightBitCharBuffer);
            if (read == -1) {
                throw new IOException();
            }

            while (read > 0) {
                eightBitCharBuffer.flip();

                this.write(eightBitCharBuffer.array(), eightBitCharBuffer.limit());
                eightBitCharBuffer.clear();

                read = socketChannel.read(eightBitCharBuffer);
                if (read == -1) {
                    throw new IOException();
                }
            }

        } catch (IOException e) {
            throw new ClosedChannelException();
        }
    }

    public void readFrom(InputStream is) throws IOException {
        try {
            byte[] byteArray = new byte[is.available()];
            is.read(byteArray);

            this.write(byteArray);
        } catch (EOFException eofe) {
            // we're done
        }
    }

    public void parseCommands() {
        int crIndex;
        String cmd;

        synchronized (this.charBuffer) {
            crIndex = findCarriageReturnPos();

            if (isCommandComplete(crIndex)) {
                return;
            }

            while (!isCommandComplete(crIndex)) {

                cmd = getStringFromCharBuffer(crIndex);

                System.out.println("CommandBuffer.Parse() new command: \"" + cmd + "\"");

                synchronized (this.completeCmds) {
                    this.completeCmds.add(cmd);
                }

                crIndex = findCarriageReturnPos();
            }
        }
    }

    private String getStringFromCharBuffer(int crIndex) {
        char[] chars;
        //No need for crIndex + 1... 
        //we already know that last char is a \r
        chars = new char[crIndex];

        for (int i = 0; i < crIndex; ++i) {
            chars[i] = this.charBuffer.remove(0);
        }
        
        //now remove that stray \r
        if (this.charBuffer.get(0) == JMudStatics.CR) {
        	this.charBuffer.remove(0);
        }
        
        return new String(chars);
    }

    private int findCarriageReturnPos() {
        int crIndex;
        crIndex = this.charBuffer.indexOf(JMudStatics.CR);
        return crIndex;
    }

    
    //The verbage here is confusing:
    //If the command IS complete, then crIndex will not be -1
    //And the function will return false... ??
    private boolean isCommandComplete(int crIndex) {
        return crIndex == -1;
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
        if (this.hasNextCommand()) {
            synchronized (this.completeCmds) {
                return this.completeCmds.remove(0);
            }
        }
        return "";
    }

    public String toString() {
        String out = "CharacterBuffer is: " + this.charBuffer.size() + " characters long.\nCompleted Command Queue:\n";
        for (String s : this.completeCmds) {
            out += "\t-" + s.replace("\r", "\\r").replace("\n", "\\n") + "\n";
        }

        return out;
    }
}
