package jmud.test.event;

import jmud.engine.behavior.Behavior;
import jmud.engine.event.JMudEvent;
import jmud.engine.event.JMudEventType;
import jmud.engine.object.JMudObject;

/**
 *
 *
 * @author david.h.loman
 */
public class TestBehavior extends Behavior {
    private static boolean hasBehaviorBeenCalled;

    /**
	 * Default constructor.
	 */
	public TestBehavior(JMudObject owner) {
		super(owner);
		this.eventTypesHandled.add(JMudEventType.Test);

        clearBehaviorHasBeenCalledFlag();
    }

	/**
	 * @see jmud.engine.behavior.Behavior#behave()
	 * @return true
	 */

	@Override
	public final boolean targetBehavior() {
		JMudObject source = this.event.getSource();
		JMudObject target = this.event.getTarget();

        hasBehaviorBeenCalled = true;

		JMudEvent responseEvent = new JMudEvent(JMudEventType.Tested, target, source);
		responseEvent.submitSelf();

		return true;
	}

	@Override
	protected boolean ccBehavior() {
		// If I get a GetEvent, and I am not the target... I dont care! Ignore!
		return true;
	}

	@Override
	protected boolean sourceBehavior() {
		// loop back to ccBehavior()
		return this.ccBehavior();
	}

	/**
	 * @see jmud.engine.behavior.Behavior#clone()
	 * @return a new <code>GetBeHavior</code>
	 */
	@Override
	public final Behavior clone() {
		return new TestBehavior(this.owner);
	}

    private void clearBehaviorHasBeenCalledFlag(){
        hasBehaviorBeenCalled = false;
    }

    public static boolean hasBehaviorBeenCalled(){
        return hasBehaviorBeenCalled;
    }

}