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

import jmud.engine.job.definitions.*;

public enum ConnectionState{
    DISCONNECTED{
        public AbstractJob createCommandFromString(Connection connection, String command){
            throw new RuntimeException("An attempt was made to processIncoming() while ConnectionState == DISCONNECTED");
        }
    },
    LOGGED_OUT{
        public AbstractJob createCommandFromString(Connection connection, String command){
            return new LoginValidateJob(connection, command);
        }
    },
    SELECTING_CHARACTER{
        public AbstractJob createCommandFromString(Connection connection, String command){
            return new CharacterSelectJob(connection, command);
        }
    },
    CREATING_CHARACTER{
        public AbstractJob createCommandFromString(Connection connection, String command){
            return new NewCharacterJob(connection, command);
        }
    },
    LOGGED_IN{
        public AbstractJob createCommandFromString(Connection connection, String command){
            return new BuildCmdFromStringJob(connection, command);
        }
    };

    public abstract AbstractJob createCommandFromString(Connection connection, String command);
}
