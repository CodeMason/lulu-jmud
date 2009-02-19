/**
 * This file is part of Lulu's JMud.
 *
 *  Lulu's JMud is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Lulu's JMud is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Lulu's JMud.  If not, see <http://www.gnu.org/licenses/>.
 */
package jmud.engine.job.definitions;

import jmud.engine.core.JMudStatics;
import jmud.engine.dbio.MysqlConnection;
import jmud.engine.netIO.Connection;
import jmud.engine.netIO.ConnectionState;

/**
 * Just a template. Can be deleted once the Job Repository has sufficient
 * samples to draw from.
 * 
 * @author David Loman
 * @version 0.1
 */

public class LoginValidateJob extends AbstractJob {
	private Connection c = null;

	public LoginValidateJob(Connection c) {
		super();
		this.c = c;
	}

	@Override
	public final boolean doJob() {
		synchronized (this.c) {

			// Check for a valid command
			if (this.c.getCmdBuffer().hasNextCommand() == false) {
				return false;
			}

			// data is either uname or passwd
			String data = this.c.getCmdBuffer().getNextCommand();

			if (this.c.getConnState() == ConnectionState.GETUNAME) {
				// We are validating the uName
				this.c.setUName(data);
				this.c.sendText("Password: ");
				this.c.setConnState(ConnectionState.GETPASSWD);
				return true;
			} else if (this.c.getConnState() == ConnectionState.GETPASSWD) {
				// We are validating the Passwd

				this.c.setPassWd(data);
				int accntID = MysqlConnection.verifyLogin(this.c.getUName(), this.c.getPassWd());

				if (accntID >= 0) {
					this.c.sendTextLn("Validated. (" + Integer.toString(accntID) + ")");
					this.c.setAccountID(accntID);
					this.c.setConnState(ConnectionState.LOGGEDIN);

					// Fire off the next Job:
					(new DisplayCharactersJob(this.c)).selfSubmit();

				} else {
					this.c.sendCRLF();
					this.c.setUName("");
					this.c.setPassWd("");
					this.c.setConnState(ConnectionState.CONNECTED);
					this.c.incrementLoginAttempts();

					this.c.sendTextLn("Username and password do not match ("
							+ (JMudStatics.MAX_LOGIN_ATTEMPTS - this.c.getLoginAttempts()) + " tries left.)");

					if (this.c.getLoginAttempts() >= JMudStatics.MAX_LOGIN_ATTEMPTS) {
						this.c.sendTextLn("\n\nNumber of tries exceeded.  Goodbye!\n\n");
						this.c.sendCRLFs(2);
						this.c.disconnect();
					} else {
						this.sendLoginPrompt();
					}
				}

				return true;
			} else {
				// Something *very* bad happened...
				System.err.println("Bad ConnectionState for a LoginValidateJob.");
				return false;
			}
		}
	}

	public void sendLoginPrompt() {
		this.c.sendText(JMudStatics.getSplashScreen());
	}

}
