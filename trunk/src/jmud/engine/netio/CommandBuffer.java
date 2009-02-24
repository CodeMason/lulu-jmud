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

        char c;

        for (int i = 0; i < length; ++i) {
            c = (char) bytes[i];

            synchronized (this.charBuffer) {
                this.charBuffer.add(c);
            }
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
        DataInputStream inputStream;
        char input;

        try {
            inputStream = new DataInputStream(is);
            while (inputStream.available() >= 2) {
                input = inputStream.readChar();

                System.out.println("CommandBuffer.write() new char: '" + input + "'");

                synchronized (this.charBuffer) {
                    this.charBuffer.add(input);
                }
            }
        } catch (EOFException eofe) {
            // we're done
        }
    }

    public void parseCommands() {
        int crIndex;
        String cmd;
        char[] chars;

        synchronized (this.charBuffer) {
            crIndex = findCarriageReturnPos();

            if (isCommandComplete(crIndex)) {
                return;
            }

            while (!isCommandComplete(crIndex)) {

                if (!isLastChar(crIndex) && nextCharIsLF(crIndex)) {
                    deleteNextChar(crIndex);
                }

                cmd = removeCRLF(getStringFromCharBuffer(crIndex));

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
        chars = new char[crIndex + 1];

        for (int i = 0; i <= crIndex; ++i) {
            chars[i] = this.charBuffer.remove(0);
        }
        return new String(chars);
    }

    private int findCarriageReturnPos() {
        int crIndex;
        crIndex = this.charBuffer.indexOf(JMudStatics.CR);
        return crIndex;
    }

    private String removeCRLF(String cmd) {
        cmd = cmd.replace(String.valueOf(JMudStatics.CR), "");
        cmd = cmd.replace(String.valueOf(JMudStatics.LF), "");
        return cmd;
    }

    private void deleteNextChar(int crIndex) {
        this.charBuffer.remove(crIndex + 1);
    }

    private boolean nextCharIsLF(int crIndex) {
        return this.charBuffer.get(crIndex + 1) == JMudStatics.LF;
    }

    private boolean isLastChar(int crIndex) {
        return crIndex == (this.charBuffer.size());
    }

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
        String out = "CharacterBuffer is: " + this.charBuffer.size() + " characters long.\n Completed Command Queue:\n";
        for (String s : this.completeCmds) {
            out += "\t-" + s.replace("\r", "\\r").replace("\n", "\\n") + "\n";
        }

        return out;
    }
}
