package jmud.engine.job.definitions;

import jmud.engine.core.JMudStatics;
import jmud.engine.netIO.Connection;

/**
 * Display a splash screen to the user with a login prompt
 *
 * @author Chris Maguire
 * @date December 5, 2008
 */

public class SplashScreenJob extends AbstractJob {

	private Connection c = null;

	public SplashScreenJob(Connection c) {
		super();
		this.c = c;
	}

	@Override
	public final boolean doJob() {
		synchronized (this.c) {
			this.sendLoginPrompt();
        }
		return true;
	}

	public void sendLoginPrompt() {
		this.c.sendText(JMudStatics.SplashScreen);
	}
}