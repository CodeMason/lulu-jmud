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
package jmud.engine.netIO;

import jmud.engine.core.JMudStatics;
import jmud.engine.job.definitions.*;

public enum ConnectionState {
	// CONNECTED, GOODUNAME, LOGGEDIN, WIZLIST, WHO, ABOUT, CHARACTERSELECT,
	// NEWCHARACTER, DELETECHARACTER, INGAME

	DISCONNECTED {
		public AbstractJob createJob(Connection c) {
			synchronized (c) {
				c.setConnState(ConnectionState.CONNECTED);
			}
			return new SendTextToClientJob(c, JMudStatics.getSplashScreen());
		}
	},
	CONNECTED {
		public AbstractJob createJob(Connection c) {
			// We need to see what the data that came in was
			// It will represent the Menu Selection
			synchronized (c) {

				String data = c.getCmdBuffer().getNextCommand();

				if (data.equals("1")) {
					// Login
					c.sendText("\nUsername: ");
					c.setConnState(ConnectionState.GETUNAME);
					return new LoginValidateJob(c);
				} else if (data.equals("2")) {
					c.sendTextLn("Not enabled at this time...");
					return new SendTextToClientJob(c, JMudStatics.getSplashScreen());
				} else if (data.equals("3")) {
					c.sendTextLn("Not enabled at this time...");
					return new SendTextToClientJob(c, JMudStatics.getSplashScreen());
				} else if (data.equals("4")) {
					c.sendTextLn("Not enabled at this time...");
					return new SendTextToClientJob(c, JMudStatics.getSplashScreen());
				} else if (data.equals("5")) {
					return new DisconnectJob(c, "Bye!\n\n");
				} else {
					c.sendTextLn("Thats not a menu choice.");
					c.sendText("Make a selection: ");
					return new LoginValidateJob(c);
				}
			}
		}
	},
	GETUNAME {
		public AbstractJob createJob(Connection c) {
			return new LoginValidateJob(c);
		}
	},
	GETPASSWD {
		public AbstractJob createJob(Connection c) {
			return new LoginValidateJob(c);
		}
	},
	LOGGEDIN {
		public AbstractJob createJob(Connection c) {
			// We need to see what the data that came in was
			// It will represent the Menu Selection
			synchronized (c) {

				String data = c.getCmdBuffer().getNextCommand();
				String[] cmds = data.split(" ");
				
				if (cmds[0].toLowerCase().equals("new")) {
					c.setConnState(ConnectionState.NEWCHARACTER);
					return new NewCharacterJob(c);
					
				} else if (cmds[0].toLowerCase().equals("delete")) {
					if (cmds.length < 2) {
						return new SendTextToClientJob(c, "Delete who?");
					}
					c.setConnState(ConnectionState.DELETECHARACTER);
					return new DeleteCharacterJob(c,cmds[1]);
				} else if (cmds[0].toLowerCase().equals("exit")) {
					return new SendTextToClientJob(c, JMudStatics.getSplashScreen());
				} else {
					return new CharacterSelectJob(c, cmds[0]);
				}
			}
		}
	},
	WIZLIST {
		public AbstractJob createJob(Connection c) {
			return null;
		}
	},
	WHO {
		public AbstractJob createJob(Connection c) {
			return null;
		}
	},
	ABOUT {
		public AbstractJob createJob(Connection c) {
			return null;
		}
	},
	CHARACTERSELECT {
		public AbstractJob createJob(Connection c) {
			return null;
		}
	},
	NEWCHARACTER {
		public AbstractJob createJob(Connection c) {
			return null;
		}
	},
	DELETECHARACTER {
		public AbstractJob createJob(Connection c) {
			return null;
		}
	},
	INGAME {
		public AbstractJob createJob(Connection c) {
			return new CheckConnForCmdsJob(c);
		}
	};

	public abstract AbstractJob createJob(Connection c);
}
