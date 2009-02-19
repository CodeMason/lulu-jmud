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
package jmud.engine.behavior;

import jmud.engine.event.JMudEvent;
import jmud.engine.event.JMudEventType;
import jmud.engine.object.JMudObject;

/**
 * Added this to try out my event trigger mechanism
 *
 * @author Chris Maguire
 */
public class AttackBehavior extends Behavior {

	/**
	 * Default constructor.
	 */
	public AttackBehavior(JMudObject owner) {
		super(owner);
		// Register a Behavior Object of this type to respond to a
		// EventType.Get
		this.eventTypesHandled.add(JMudEventType.Attack);
	}

	/**
	 * @see Behavior#behave()
	 * @return true
	 */
	@Override
	public final boolean targetBehavior() {
		// prep the 'response' JMudEvent
		JMudEvent jme = new JMudEvent(JMudEventType.Attacked, this.event.getTarget(), this.event.getSource());

		jme.selfSubmit();

		return true;
	}

	/**
	 * @see Behavior#clone()
	 * @return a new <code>GetBeHavior</code>
	 */
	@Override
	public final Behavior clone() {
		return new AttackBehavior(this.owner);
	}

}