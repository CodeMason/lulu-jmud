package jmud.engine.job.definitions;

import jmud.engine.core.JMudStatics;
import jmud.engine.netIO.Connection;
import jmud.engine.netIO.ConnectionState;
import jmud.engine.netIO.LoginState;

/**
 * Just a template. Can be deleted once the Job Repository has sufficient
 * samples to draw from.
 * 
 * @author David Loman
 * @version 0.1
 */

public class HandleLoginJob extends AbstractJob {

	private String data = "";
	private Connection c = null;

	public HandleLoginJob(Connection c, String data) {
		super();
		this.c = c;
		this.data = data + "";
	}
	HandleLoginJob(Connection c) {
		this(c, "");
	}

	@Override
	public final boolean doJob() {
		synchronized (this.c) {
			if (this.c.getLoginstate() == LoginState.Neither && data.length() == 0) {
				// We must be sending this to the user the first time
				this.sendLoginPrompt();
			} else if (this.c.getLoginstate() == LoginState.Neither && data.length() != 0) {
				// just received the uName
				this.c.setUName(data);
				this.c.sendText("Password: ");
				this.c.setLoginstate(LoginState.uName);
			} else if (this.c.getLoginstate() == LoginState.uName) {
				this.c.setPassWd(data);
				// Here we are... validate password.
				if (this.validatePassWd()) {
					this.c.sendTextLn("Validated.");
					this.c.setConnState(ConnectionState.LoggedInToCharacterSelect);
					this.c.setLoginstate(LoginState.uNameAndPassword);
					CharacterSelectJob csj = new CharacterSelectJob(this.c);
					csj.submitSelf();
				} else {
					this.c.sendTextLn("Username and password do not match.");
					this.c.sendCRLFs(2);
					this.c.setUName("");
					this.c.setPassWd("");
					this.c.setLoginstate(LoginState.Neither);
					this.c.incrLogAttempts();

					if (this.c.getLogAttempts() >= JMudStatics.MAX_LOGIN_ATTEMPTS) {
						this.c.sendTextLn("Number of tries exceeded.  Goodbye!");
						this.c.sendCRLFs(2);
						this.c.disconnect();
					} else {
						this.sendLoginPrompt();
					}
				}
			}
		}
		return false;
	}

	private boolean validatePassWd() {
		// TODO hook in the DB query here.

		// Temporary validation
		if (this.c.getUName().equals(JMudStatics.hardcodeUName)
				&& this.c.getPassWd().equals(JMudStatics.hardcodePasswd)) {
			return true;
		} else {
			return false;
		}
	}
	public void sendLoginPrompt() {
		this.c.sendText(JMudStatics.SplashScreen);
	}
}
