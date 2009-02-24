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
import jmud.engine.config.JMudConfig;
import jmud.engine.netio.JMudClient;
import jmud.engine.netio.JMudClientState;

/**
 * Just a template. Can be deleted once the Job Repository has sufficient
 * samples to draw from.
 * 
 * @author David Loman
 * @version 0.1
 */

public class LoginJob extends AbstractClientJob {
	public LoginJob(JMudClient c) {
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
			
			if (this.c.getConnState() == JMudClientState.GETUNAME) {
				// We are validating the uName

				//Load the Account from the Database
				a = AccountManager.getInstance().loadAccount(data);
				
				if (a == null) {
					//There is no account with that username
					this.c.sendText("There is no account with that username.");
					this.c.changeConnState(JMudClientState.CONNECTED);
					return false;
				}
				
				//Give the Connection a reference to the loaded account;
				this.c.setAccount(a);
				
				this.c.sendText("Password: ");
				this.c.changeConnState(JMudClientState.GETPASSWD);
				return true;
				
				
			} else if (this.c.getConnState() == JMudClientState.GETPASSWD) {
				// We are validating the Passwd
				a = this.c.getAccount();
				
				if (data.equals(a.getPassWd())) {
					//We are validated

					//DEBUG
					this.c.sendTextLn("Validated. (AccountID=" + Integer.toString(a.getAccountID()) + ")");
					
					a.resetLoginAttempts();
					this.c.changeConnState(JMudClientState.CHARACTERMANAGE);

			
					return true;
				} else {
					a.incrementLoginAttempts();
					a.save();
					AccountManager.getInstance().unloadAccont(a);
					
					//Pull data from config
					String s = JMudConfig.getInstance().getConfigElement("maxLoginAttempts");
					int max = Integer.parseInt(s);
					
					this.c.sendCRLF();
					this.c.sendTextLn("Username and password do not match ("
							+ (max - a.getLoginAttempts()) + " tries left.)");

					if (a.getLoginAttempts() >= max) {
						this.c.sendTextLn("\n\nNumber of tries exceeded.");
						this.c.sendCRLFs(2);
						this.c.changeConnState(JMudClientState.DISCONNECTED);
					} else {
						this.c.changeConnState(JMudClientState.CONNECTED);
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
