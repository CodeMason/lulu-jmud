package jmud.engine.behavior;

import jmud.engine.object.JMudObject;

/**
 * Simple representation of a Behavior. This is a Singleton
 * because I can't see any need for having multiple instances, although
 * I did make the behave method synchronized.
 *
 * Any object interested in handling the returned SuccessEvent would
 * register a Behavior for it (e.g. SendSuccessMessageToPlayer); any
 * object without a registered Behavior would just ignore it.
 */
public class SimpleBehavior extends Behavior {
 
	public SimpleBehavior() {
		super();
	}

	/**
     * perform this Behavior's behavior and return the resultant
     * event.
     *
     * @param event the event the behavior is responding too
     * @return the resulting EventObject
     */
 
	@Override
	public boolean behave() {
		JMudObject source = this.event.getSource();
		JMudObject targets = this.event.getTarget();
	
		//Send off events here!!
		
		return true;
	}

	@Override
	public Behavior clone() {
		return new GetBehavior();

	}


}
