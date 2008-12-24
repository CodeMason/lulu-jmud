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

import jmud.engine.commands.AbstractCommand;
import jmud.engine.commands.CommandFactory;
import jmud.engine.netIO.Connection;

/**
 * Checks a connection's ByteBuffer to see if a valid command has been stored
 * yet. If there has, then this Job will submit a DetermineWhichCommand_Job.
 *
 * @author David Loman
 * @version 0.1
 */

public class BuildCmdFromStringJob extends AbstractDataJob {
	private final Connection connection;

    public BuildCmdFromStringJob(Connection connection, String data) {
		this.connection = connection;
		this.data = data;
	}

	@Override
	public final boolean doJob() {
		this.connection.sendTextLn("Recieved a command: " + this.data);

		String[] ca = this.data.split(" ");

		AbstractCommand ac = CommandFactory.getLazyLoadedInstance().getAbstractCommand(ca[0]);
		if (ac != null) {
			AbstractCommand nac = ac.clone(connection, ca);
			submitJob(nac);
            
            this.connection.sendCRLF();

            sendCharacterPrompt();

            return true;
		} else {
			this.connection.sendCRLF();

            sendCharacterPrompt();

            return false;
		}
	}

    private void sendCharacterPrompt(){
        submitJob(new SendCharacterPromptJob(this.connection));
    }

    public Connection getConnection() {
		return connection;
	}
}
