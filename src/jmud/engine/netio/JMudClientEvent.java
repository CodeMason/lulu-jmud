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
package jmud.engine.netio;

import java.nio.channels.SocketChannel;

/**
 * @author David Loman
 */
public class JMudClientEvent{

    public SocketChannel socket;
    public EventType eventType;
    public int ops;

    public JMudClientEvent(SocketChannel inSocket, EventType inType, int inOps){
        this.socket = inSocket;
        this.eventType = inType;
        this.ops = inOps;
    }

    public static enum EventType {
        REGISTER,
        CHANGEOPS
    }
}
