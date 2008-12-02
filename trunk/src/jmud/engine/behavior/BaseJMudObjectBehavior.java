package jmud.engine.behavior;

import jmud.engine.object.JMudObject;

/**
 * A Behavior class that is implemented by ALL JMudObjects to ensure there is a
 * base level of behavior from any/all JMudObjects.
 */
public class BaseJMudObjectBehavior extends Behavior {

	/**
	 * Default constructor.
	 */
	public BaseJMudObjectBehavior(JMudObject owner) {
		super(owner);
	}

	/**
	 * perform this Behavior's behavior and return the resultant event.
	 */

	@Override
	public final boolean ownerBehavior() {

		// Send off events here!!

		return true;
	}
	@Override
	protected boolean ccBehavior() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public final Behavior clone() {
		return new BaseJMudObjectBehavior(this.owner);

	}

}
