package jmud.engine.behavior;

/**
 * A Behavior class that is implemented by ALL JMudObjects to ensure there is a
 * base level of behavior from any/all JMudObjects.
 */
public class BaseJMudObjectBehavior extends Behavior {

	/**
	 * Default constructor.
	 */
	public BaseJMudObjectBehavior() {
		super();
	}

	/**
	 * perform this Behavior's behavior and return the resultant event.
	 */

	@Override
	public final boolean behave() {

		// Send off events here!!

		return true;
	}

	@Override
	public final Behavior clone() {
		return new GetBehavior();

	}

}
