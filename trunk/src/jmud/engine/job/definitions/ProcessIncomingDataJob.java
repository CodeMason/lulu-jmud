package jmud.engine.job.definitions;

import jmud.engine.core.JMudStatics;
import jmud.engine.netIO.Connection;
import jmud.engine.netIO.ConnectionState;

import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * Just a template. Can be deleted once the Job Repository has sufficient
 * samples to draw from.
 *
 * @author David Loman
 * @version 0.1
 */

public class ProcessIncomingDataJob extends AbstractJob {

	private Connection c = null;

	public ProcessIncomingDataJob(Connection c) {
		super();
		this.c = c;
	}

	@Override
	public final boolean doJob() {
		synchronized (this.c) {
            int bufferPosition = 0;

            boolean isCommandComplete = false;
            // decode the buffer
            String data = "";
			Charset cs = Charset.forName("ISO-8859-1");
			CharsetDecoder dec = cs.newDecoder();

			try {
                bufferPosition = this.c.getReadBuffer().position();
                this.c.getReadBuffer().flip();

				// Build a string out of the connections ByteBuffer.
				CharBuffer cb = dec.decode(this.c.getReadBuffer());
				data = cb.toString();

                // check if they've hit [enter]; they're client might send every char as it's typed,
                // in which case we'll wait till it's all entered.
                isCommandComplete = data.contains(JMudStatics.CRLF);

                // If the string contains a \r\n then its a complete command
				// which it damned well better because of the 8192 char limit!!!
				if ((data.length() - data.replace(JMudStatics.CRLF, "").length()) == 0) {

				}
				data = data.replace(JMudStatics.CRLF, "");

			} catch (CharacterCodingException e) {
				System.err.println("Connection: CharacterCodingException in ByteBuffer of: "
						+ this.c.getSc().socket().getInetAddress().toString());
				e.printStackTrace();
				return false;
			} catch (Exception e) {
				System.err.println("Connection: Exception in ByteBuffer of: "
						+ this.c.getSc().socket().getInetAddress().toString());
				e.printStackTrace();
				return false;
			}

			System.out.println("From " + this.c.getName() + ": " + data + "(" + data.length() + ")");
            if(isCommandComplete){
                this.c.getReadBuffer().clear();
            }else{
                // leave the data in the buffer but put the limit and position back where they were
                this.c.getReadBuffer().limit(JMudStatics.CONNECTION_READ_BUFFER_SIZE);
                this.c.getReadBuffer().position(bufferPosition);
                return true;
            }

            // Now that we have a valid string, lets route it!

			if (this.c.getConnState() == ConnectionState.NotConnected) {
				// Shouldn't be here!
				System.err.println("An attempt was made to processIncoming() while ConnectionState == NotConnected");
				return false;

			} else if (this.c.getConnState() == ConnectionState.ConnectedButNotLoggedIn) {
				// Spin a HandleLoginJob
				LoginValidateJob hlj = new LoginValidateJob(this.c, data);
				hlj.submitSelf();

			} else if (this.c.getConnState() == ConnectionState.LoggedInToCharacterSelect) {
				// Spin a CharacterSelectJob
				CharacterSelectJob csj = new CharacterSelectJob(this.c, data);
				csj.submitSelf();

			} else if (this.c.getConnState() == ConnectionState.LoggedInToNewCharacter) {
				// Spin a NewCharacterJob
				NewCharacterJob ncj = new NewCharacterJob(this.c, data);
				ncj.submitSelf();

			} else if (this.c.getConnState() == ConnectionState.LoggedInToGameServer) {

				// Build next Job
				BuildCmdFromStringJob job = new BuildCmdFromStringJob(this.c, data);

				// Submit next Job
				job.submitSelf();

			} else {
                // ToDo CM: What are these broken lines about? Is this a style?
                // Shouldn't be here!
				System.err
						.println("An attempt was made to processIncoming() while ConnectionState was in an unknown state.");
				return false;
			}
		}
		return true;
	}

}