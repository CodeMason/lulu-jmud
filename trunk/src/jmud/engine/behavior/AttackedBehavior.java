package jmud.engine.behavior;

import jmud.engine.event.JMudEventType;
import jmud.engine.object.JMudObject;

/**
 *
 * @author Dave Loman
 */
public class AttackedBehavior extends Behavior {

	/**
	 * Default constructor.
	 */
	public AttackedBehavior(JMudObject owner) {
		super(owner);
		this.eventTypesHandled.add(JMudEventType.Attacked);
	}

	/**
	 * Just print out that we got here for now
	 *
	 * @see Behavior#behave()
	 * @return true
	 */
	@Override
	public final boolean targetBehavior() {

		String text = "YOU attack " + this.event.getSource().getHumanReadableName() + "!!\n";
		this.event.getTarget().sendToConsole(text);
		return true;
	}

	@Override
	protected boolean ccBehavior() {
		String text = this.event.getTarget().getHumanReadableName() + " attacks " + this.event.getSource().getHumanReadableName() + "!!";
		this.owner.sendToConsole(text);
		return true;
	}

	@Override
	protected boolean sourceBehavior() {

		String text = this.event.getTarget().getHumanReadableName() + " attacks YOU!!!1!11!!!!";
		this.event.getSource().sendToConsole(text);

		return true;
	}
}