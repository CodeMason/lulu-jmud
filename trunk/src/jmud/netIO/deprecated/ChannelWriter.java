package jmud.netIO.deprecated;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/*
 * ChannelWriter.java
 *
 * Created on ?
 *
 * History
 *
 * Programmer:     Change:                                           Date:
 * ----------------------------------------------------------------------------------
 * Chris M         Cleaned up comments                               Feb 14, 2007
 */

/**
 * Writes messages to socket channels
 *
 * @author Chris Maguire
 * @version 0.1
 */
public class ChannelWriter {

    private static final String COMMAND_PARSE_ERROR_MESSAGE = "Command error, could not parse command: ";
    private static final String ERROR_MESSAGE = "Encountered an error\n\r";

    private static final Charset ascii = Charset.forName("US-ASCII");

    public void sendMessage(String strMessage, SocketChannel socketChannel) throws Exception {
        // DEBUG:
        //System.out.println(strMessage);

        // make a message
        CharBuffer chars = CharBuffer.allocate(strMessage.length());
        chars.put(strMessage);
        chars.flip();
        // Translate the Unicode characters into ASCII bytes.
        ByteBuffer buffer = ascii.newEncoder().encode(chars);
        buffer.rewind();
        socketChannel.write(buffer);
    }

    public void sendError(String strCmd, SocketChannel socketChannel) throws Exception {
        System.out.println("Tick: run(): sending command error");

        sendMessage(COMMAND_PARSE_ERROR_MESSAGE + strCmd + " \n\r", socketChannel);
    }

    public void sendError(SocketChannel socketChannel) throws Exception {
        System.out.println("CommandListenerThread: general error");

        sendMessage(ERROR_MESSAGE, socketChannel);
    }

}
