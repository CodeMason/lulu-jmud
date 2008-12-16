package jmud.engine.job.definitions;

import jmud.engine.core.JMudStatics;
import jmud.engine.netIO.Connection;

/**
 * Just a template. Can be deleted once the Job Repository has sufficient
 * samples to draw from.
 *
 * @author David Loman
 * @version 0.1
 */

public class SendCharacterPromptJob extends AbstractJob {

	private String data = "";
	private Connection c = null;

	public SendCharacterPromptJob(Connection c, String data) {
		super();
		this.c = c;
		this.data = (data == null ? "" : data);
	}

    public SendCharacterPromptJob(Connection c) {
		this(c, "");
	}

	@Override
	public final boolean doJob() {
		synchronized (this.c) {
           
		}
		return false;
	}

	public void sendLoginPrompt() {
		this.c.sendText(JMudStatics.SplashScreen);
	}
}