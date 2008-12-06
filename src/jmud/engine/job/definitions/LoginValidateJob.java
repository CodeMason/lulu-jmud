package jmud.engine.job.definitions;

import jmud.engine.core.JMudStatics;
import jmud.engine.dbio.MysqlConnection;
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

public class LoginValidateJob extends AbstractJob {

	private String data = "";
	private Connection c = null;

	public LoginValidateJob(Connection c, String data) {
		super();
		this.c = c;
		this.data = (data == null ? "" : data); 
	}
	public LoginValidateJob(Connection c) {
		this(c, "");
	}

	@Override
	public final boolean doJob() {
		synchronized (this.c) {
            if (this.c.getLoginstate() == LoginState.Neither) {
				// just received the uName
				this.c.setUName(data);
				this.c.sendText("Password: ");
				this.c.setLoginstate(LoginState.uName);
			} else if (this.c.getLoginstate() == LoginState.uName) {
				this.c.setPassWd(data);
				// Here we are... validate password.
				int retVal = MysqlConnection.verifyLogin(this.c.getUName(), this.c.getPassWd());

				if (retVal != -1) {
					this.c.sendTextLn("Validated.");
					this.c.setConnState(ConnectionState.LoggedInToCharacterSelect);
					this.c.setLoginstate(LoginState.uNameAndPassword);
					this.c.setAccountID(retVal);
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

	public void sendLoginPrompt() {
		this.c.sendText(JMudStatics.SplashScreen);
	}
}
