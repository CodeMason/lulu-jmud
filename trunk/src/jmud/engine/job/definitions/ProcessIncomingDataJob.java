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

import jmud.engine.netIO.Connection;
import jmud.engine.netIO.ConnectionState;

public class ProcessIncomingDataJob extends AbstractJob {

	private Connection connection = null;

	public ProcessIncomingDataJob(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public final boolean doJob() {
		synchronized (this.connection) {
            ConnectionState connectionState = connection.getConnState();
            String data;

            if(!connection.isCommandComplete()){
                return false;
            }

            data = connection.getAndClearCommand();

            return createAndSubmitAppropriateCommand(connectionState, data);
		}
	}

    private boolean createAndSubmitAppropriateCommand(ConnectionState connectionState, String data){
        submitJob(connectionState.createCommandFromString(this.connection, data));
        return true;
    }
}
