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
package jmud;

import jmud.engine.commands.CommandManager;
import jmud.engine.commands.definitions.QuitCommand;
import jmud.engine.job.JobManager;
import jmud.engine.netIO.JMudClientManager;

import java.io.IOException;
import java.net.UnknownHostException;

public class JMudTest01 {

	/**
	 *
	 * This test was designed to test the login, character select, new character and game entrance functions....
	 *
	 * // ToDo CM: writing a unit test for this is going to fun. :(
     *
	 * @param args
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {

		final long stayAlive = 1000 * 60 * 5L;
		System.out.println("Test started with a autostop timer = " + (stayAlive / 1000) + " sec(s).");

		//initialize JobManager with only 1 worker
		JobManager.getLazyLoadedInstance().init(10);

		//Initialize single instance and register:
		QuitCommand qc = new QuitCommand();
		CommandManager.getLazyLoadedInstance().registerCommand(qc);
		
	      // initialize and start ConnMan
		JMudClientManager cm = new JMudClientManager(54321);
		cm.start();

		TestUtil.pause(stayAlive);

		System.out.println("Test timeout reached(" + (stayAlive / 1000) + " secs).  Shutting down.");
		cm.stop();
		JobManager.getLazyLoadedInstance().stopAllWorkers();
	}

}
