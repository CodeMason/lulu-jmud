package jmud.test.event;

import jmud.engine.behavior.Behavior;
import jmud.engine.event.JMudEvent;
import jmud.engine.event.JMudEventType;
import jmud.engine.object.JMudObject;

/**
 * A Behavior class that flags itself as having been called
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

	/**
	 * @see jmud.engine.behavior.Behavior#clone()
	 * @return a new <code>GetBeHavior</code>
	 */
	@Override
	public final TestBehavior clone(){
        return new TestBehavior(this.owner);
	}

    private void setOwner(JMudObject owner){
        this.owner = owner;
    }

    private void clearBehaviorHasBeenCalledFlag(){
        hasBehaviorBeenCalled = false;
    }

    public static boolean hasBehaviorBeenCalled(){
        return hasBehaviorBeenCalled;
    }

}