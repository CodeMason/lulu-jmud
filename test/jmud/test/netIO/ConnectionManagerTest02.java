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
package jmud.test.netIO;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import jmud.engine.commands.definitions.QuitCommand;
import jmud.engine.job.JobManager;
import jmud.engine.netIO.ConnectionManager;
import jmud.test.TestUtil;

public class ConnectionManagerTest02 {

   /**
    * This test was designed to test the login, character select, new character
    * and game entrance functions....
    * @param args
    * @throws IOException
    * @throws UnknownHostException
    */
   public static void main(String[] args) throws UnknownHostException,
         IOException {

      final int stayAlive = 1000 * 120;

      // initialize JobManager with only 1 worker
      JobManager.getLazyLoadedInstance().init(1);

      // initialize ConnMan
      ConnectionManager.getLazyLoadedInstance().init(
            InetAddress.getLocalHost(), 54321);
      ConnectionManager.getLazyLoadedInstance().start();

      // Lets get that Quit command in there.
      QuitCommand qc = new QuitCommand(); // use the null null constructor
      qc.selfRegister();

      TestUtil.pause(stayAlive);

      System.out.println("Test timeout reached(" + (stayAlive / 1000)
            + " secs).  Shutting down.");
      ConnectionManager.getLazyLoadedInstance().stop();
      JobManager.getLazyLoadedInstance().stopAllWorkers();
   }

}