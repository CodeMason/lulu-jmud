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

import jmud.engine.account.Account;
import jmud.engine.account.AccountManager;
import jmud.engine.core.JMudStatics;
import jmud.engine.netIO.Connection;
import jmud.engine.netIO.ConnectionState;

/**
 * Just a template. Can be deleted once the Job Repository has sufficient
 * samples to draw from.
 * 
 * @author David Loman
 * @version 0.1
 */

public class LoginJob extends AbstractConnectionJob {
	public LoginJob(Connection c) {
		super(c);
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

			Account a;
			
			if (this.c.getConnState() == ConnectionState.GETUNAME) {
				// We are validating the uName

				//Load the Account from the Database
				a = AccountManager.getInstance().loadAccount(data);
				
				if (a == null) {
					//There is no account with that username
					this.c.sendText("There is no account with that username.");
					this.c.changeConnState(ConnectionState.CONNECTED);
					return false;
				}
				
				//Give the Connection a reference to the loaded account;
				this.c.setAccount(a);
				
				this.c.sendText("Password: ");
				this.c.changeConnState(ConnectionState.GETPASSWD);
				return true;
				
				
			} else if (this.c.getConnState() == ConnectionState.GETPASSWD) {
				// We are validating the Passwd
				a = this.c.getAccount();
				
				if (data.equals(a.getPassWd())) {
					//We are validated

					//DEBUG
					this.c.sendTextLn("Validated. (AccountID=" + Integer.toString(a.getAccountID()) + ")");
					
					a.resetLoginAttempts();
					this.c.changeConnState(ConnectionState.CHARACTERMANAGE);

			
					return true;
				} else {
					a.incrementLoginAttempts();
					a.save();
					AccountManager.getInstance().unloadAccont(a);
										
					this.c.sendCRLF();
					this.c.sendTextLn("Username and password do not match ("
							+ (JMudStatics.MAX_LOGIN_ATTEMPTS - a.getLoginAttempts()) + " tries left.)");

					if (a.getLoginAttempts() >= JMudStatics.MAX_LOGIN_ATTEMPTS) {
						this.c.sendTextLn("\n\nNumber of tries exceeded.");
						this.c.sendCRLFs(2);
						this.c.changeConnState(ConnectionState.DISCONNECTED);
					} else {
						this.c.changeConnState(ConnectionState.CONNECTED);
					}
					return false;
				}
			} else {
				// Something *very* bad happened...
				System.err.println("Bad ConnectionState for a LoginJob.");
				return false;
			}
		}
	}
}
