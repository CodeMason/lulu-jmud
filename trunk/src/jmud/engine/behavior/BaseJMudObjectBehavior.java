package jmud.engine.behavior;

import jmud.engine.object.JMudObject;

/**
 * A Behavior class that is implemented by ALL JMudObjects to ensure there is a
 * base level of behavior from any/all JMudObjects.
 */
public class BaseJMudObjectBehavior extends Behavior {

	public BaseJMudObjectBehavior(JMudObject owner) {
		super(owner);
	}

	/**
	 * perform this Behavior's behavior and return the resultant event.
	 */

	@Override
	public final boolean targetBehavior() {
		return true;
	}

	@Override
	protected boolean ccBehavior() {
		return true;
	}

	@Override
	protected boolean sourceBehavior() {
		// loop back to ccBehavior()
		return this.ccBehavior();
	}

	@Override
	public final Behavior clone() {
		return new BaseJMudObjectBehavior(this.owner);

	}

}
