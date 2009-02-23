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

public enum JMudClientState {
	// CONNECTED, GOODUNAME, LOGGEDIN, WIZLIST, WHO, ABOUT, CHARACTERSELECT,
	// NEWCHARACTER, DELETECHARACTER, INGAME

	DISCONNECTED {
		@Override
		public void runEnterJob(JMudClient c, JMudClientState fromState) {
			// AutoForward to runStateJob
			JMudClientState.DISCONNECTED.runStateJob(c);
		}

		@Override
		public void runStateJob(JMudClient c) {
			DisconnectJob dj = new DisconnectJob(c, "Bye bye!!");
			dj.selfSubmit();
		}

		@Override
		public void runExitJob(JMudClient c, JMudClientState toState) {
			synchronized (c) {
				c.sendText(JMudStatics.getSplashScreen());
			}
		}
	},
	CONNECTED {
		@Override
		public void runEnterJob(JMudClient c, JMudClientState fromState) {
			synchronized (c) {
				c.sendText(JMudStatics.MAIN_MENU);
			}
		}

		@Override
		public void runStateJob(JMudClient c) {
			synchronized (c) {
				// We need to see what the data that came in was
				// It will represent the Menu Selection
				String data = c.getCmdBuffer().getNextCommand();

				if ("1".equals(data)) {
					// Login
					c.sendText("\nUsername: ");

					// Trigger state change actions
					c.changeConnState(JMudClientState.GETUNAME);

				} else if ("2".equals(data)) {
					// View WizList
					c.changeConnState(JMudClientState.WIZLIST);

				} else if ("3".equals(data)) {
					// View WhoList
					c.changeConnState(JMudClientState.WHO);

				} else if ("4".equals(data)) {
					// View About This Mud
					c.changeConnState(JMudClientState.ABOUT);

				} else if ("5".equals(data)) {
					// Disconnect
					// Trigger state change actions
					c.changeConnState(JMudClientState.DISCONNECTED);

				} else {
					c.sendCRLFs(2);
					c.sendTextLn("Thats not a menu choice.");

					// Re-Enter this state
					JMudClientState.CONNECTED.runEnterJob(c, JMudClientState.CONNECTED);
				}
			}
		}

		@Override
		public void runExitJob(JMudClient c, JMudClientState toState) {
			// Disconnect Safety
			if (toState == JMudClientState.DISCONNECTED) {
				return;
			}
			// No Event
		}
	},
	GETUNAME {
		@Override
		public void runEnterJob(JMudClient c, JMudClientState fromState) {
			// AutoForward to runStateJob
			JMudClientState.GETUNAME.runStateJob(c);
		}

		@Override
		public void runStateJob(JMudClient c) {
			LoginJob j = new LoginJob(c);
			j.selfSubmit();
		}

		@Override
		public void runExitJob(JMudClient c, JMudClientState toState) {
			// Disconnect Safety
			if (toState == JMudClientState.DISCONNECTED) {
				return;
			}
			// No Event

		}
	},
	GETPASSWD {
		@Override
		public void runEnterJob(JMudClient c, JMudClientState fromState) {
			// AutoForward to runStateJob
			JMudClientState.GETPASSWD.runStateJob(c);
		}

		@Override
		public void runStateJob(JMudClient c) {
			LoginJob j = new LoginJob(c);
			j.selfSubmit();
		}

		@Override
		public void runExitJob(JMudClient c, JMudClientState toState) {
			// Disconnect Safety
			if (toState == JMudClientState.DISCONNECTED) {
				return;
			}
			// No Event
		}
	},
	WIZLIST {
		@Override
		public void runEnterJob(JMudClient c, JMudClientState fromState) {
			AbstractJob j = new DisplayWizListJob(c);
			j.selfSubmit();

			j = new SendTextToClientJob(c, "Press RETURN to continue...");
			j.selfSubmit();
			// Dont auto forward
		}

		@Override
		public void runStateJob(JMudClient c) {
			// Read the 'return' out of the connections CommandBuffer
			c.getCmdBuffer().getNextCommand();

			c.changeConnState(JMudClientState.CONNECTED);
		}

		@Override
		public void runExitJob(JMudClient c, JMudClientState toState) {
			// Disconnect Safety
			if (toState == JMudClientState.DISCONNECTED) {
				return;
			}
			// No Event
		}
	},
	WHO {
		@Override
		public void runEnterJob(JMudClient c, JMudClientState fromState) {
			AbstractJob j = new DisplayWhoListJob(c);
			j.selfSubmit();

			j = new SendTextToClientJob(c, "Press RETURN to continue...");
			j.selfSubmit();
			// Dont auto forward
		}

		@Override
		public void runStateJob(JMudClient c) {
			// Read the 'return' out of the connections CommandBuffer
			c.getCmdBuffer().getNextCommand();

			c.changeConnState(JMudClientState.CONNECTED);
		}

		@Override
		public void runExitJob(JMudClient c, JMudClientState toState) {
			// Disconnect Safety
			if (toState == JMudClientState.DISCONNECTED) {
				return;
			}
			// No Event
		}
	},
	ABOUT {
		@Override
		public void runEnterJob(JMudClient c, JMudClientState fromState) {
			AbstractJob j = new DisplayAboutScreenJob(c);
			j.selfSubmit();

			j = new SendTextToClientJob(c, "Press RETURN to continue...");
			j.selfSubmit();
			// Dont auto forward
		}

		@Override
		public void runStateJob(JMudClient c) {
			// Read the 'return' out of the connections CommandBuffer
			c.getCmdBuffer().getNextCommand();

			c.changeConnState(JMudClientState.CONNECTED);
		}

		@Override
		public void runExitJob(JMudClient c, JMudClientState toState) {
			// Disconnect Safety
			if (toState == JMudClientState.DISCONNECTED) {
				return;
			}
			// No Event
		}
	},
	CHARACTERMANAGE {
		@Override
		public void runEnterJob(JMudClient c, JMudClientState fromState) {
			c.sendCRLFs(2);
			AbstractJob j = new DisplayCharactersJob(c);
			j.selfSubmit();
		}

		@Override
		public void runStateJob(JMudClient c) {
			synchronized (c) {
				AbstractJob j;
				// We need to see what the data that came in was
				// It will represent the Menu Selection
				String data = c.getCmdBuffer().getNextCommand();
				String[] cmds = data.split(" ");

				if (cmds[0].toLowerCase().equals("new")) {
					// Make a new Character
					c.changeConnState(JMudClientState.NEWCHARACTER);

				} else if (cmds[0].toLowerCase().equals("delete")) {
					// Delete a character
					if (cmds.length < 2) {
						c.sendCRLFs(2);
						c.sendTextLn("That name is not recognized.\n");
						JMudClientState.CHARACTERMANAGE.runEnterJob(c, JMudClientState.CHARACTERMANAGE);
						return;
					}

					// Manually Execute a DeleteCharacterJob to keep things
					// synced up. We want the Delete Character event to happen
					// before the Character Display
					c.sendCRLFs(2);
					j = new DeleteCharacterJob(c, cmds[1]);
					boolean deleted = j.doJob();

					if (deleted) {
						c.sendTextLn("Deleted that character.\n");
					} else {
						c.sendTextLn("Hrm, couldn't find that character.\n");
					}

					// Now refresh the character List
					JMudClientState.CHARACTERMANAGE.runEnterJob(c, JMudClientState.CHARACTERMANAGE);
					return;
				} else if (cmds[0].toLowerCase().equals("exit")) {
					// Exit back to Main Menu.
					c.changeConnState(JMudClientState.CONNECTED);
					return;
				} else if (cmds[0].length() == 0) {
					c.sendCRLFs(2);
					c.sendTextLn("Thats not a menu choice.");

					// Re-Enter this state
					JMudClientState.CHARACTERMANAGE.runEnterJob(c, JMudClientState.CHARACTERMANAGE);
					return;
				} else {
					// Try to look up a character and load it ingame.
					String pcName = cmds[0];

					// Manually Execute a load
					j = new LoadCharacterJob(c, pcName);
					boolean loaded = j.doJob();

					if (loaded) {
						c.changeConnState(JMudClientState.INGAME);
					} else {
						c.sendCRLFs(2);
						c.sendTextLn("Hrm, couldn't find that character.\n");

						// Re-Enter this state
						JMudClientState.CHARACTERMANAGE.runEnterJob(c, JMudClientState.CHARACTERMANAGE);

					}
				}

			}
		}

		@Override
		public void runExitJob(JMudClient c, JMudClientState toState) {
			// Disconnect Safety
			if (toState == JMudClientState.DISCONNECTED) {
				return;
			}

			// Don't forget to clean up the login info on our way out!
			if (toState == JMudClientState.CONNECTED) {
				LogoutJob lj = new LogoutJob(c);
				lj.selfSubmit();
			}

		}
	},
	NEWCHARACTER {
		@Override
		public void runEnterJob(JMudClient c, JMudClientState fromState) {
			AbstractJob j = new NewCharacterJob(c);
			j.selfSubmit();

			j = new SendTextToClientJob(c, "Press RETURN to continue...");
			j.selfSubmit();
			// Dont auto forward
		}

		@Override
		public void runStateJob(JMudClient c) {
			// Read the 'return' out of the connections CommandBuffer
			c.getCmdBuffer().getNextCommand();

			c.changeConnState(JMudClientState.CHARACTERMANAGE);
		}

		@Override
		public void runExitJob(JMudClient c, JMudClientState toState) {
			// Disconnect Safety
			if (toState == JMudClientState.DISCONNECTED) {
				return;
			}
			// No Event
		}
	},

	// DELETECHARACTER {
	// @Override
	// public void runEnterJob(Connection c, ConnectionState fromState) {
	// System.err.println("Returning a NULL object!");
	// return null;
	// }
	//
	// @Override
	// public void runStateJob(Connection c) {
	// }
	//
	// @Override
	// public void runExitJob(Connection c, ConnectionState toState) {
	// // Disconnect Safety
	// if (toState == ConnectionState.DISCONNECTED) {
	// return;
	// }
	//
	// }
	// },
	INGAME {
		@Override
		public void runEnterJob(JMudClient c, JMudClientState fromState) {
			// Put anything that needs to be done Prior to World Entry here.
			c.sendCRLFs(2);
			c.sendTextLn("Entering the world of jMUD...\n\n");
			c.sendPrompt();

			// AutoForward
			JMudClientState.INGAME.runStateJob(c);
		}

		@Override
		public void runStateJob(JMudClient c) {
			
			//TODO Finish the Command routing! 
			//Temporary Code Stub
			if (c.getCmdBuffer().hasNextCommand()) {
				String cmdStr = c.getCmdBuffer().getNextCommand();
//				c.sendTextLn("Oh yeah?!  Well " + cmdStr + " to you too pal!");
//				
//				String cmd = cmdStr.split(" ")[0];
				
				LookupCommandJob j = new LookupCommandJob(c,cmdStr.split(" "));
				j.selfSubmit();
				
			}
			
			
			
		}

		@Override
		public void runExitJob(JMudClient c, JMudClientState toState) {
			// Disconnect Safety
			if (toState == JMudClientState.DISCONNECTED) {
				return;
			}

			c.sendTextLn("Leaving the world of jMUD...\n\n");
		}
	};

	public abstract void runExitJob(JMudClient c, JMudClientState toState);

	public abstract void runStateJob(JMudClient c);

	public abstract void runEnterJob(JMudClient c, JMudClientState fromState);
}
