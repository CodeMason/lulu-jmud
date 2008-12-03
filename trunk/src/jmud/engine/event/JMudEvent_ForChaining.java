package jmud.engine.event;

import jmud.engine.behavior.Behavior;
import jmud.engine.object.JMudObject;

import java.util.*;

/**
 * This is a synthesis learning exercise, relax. :)
 * I overrode JMudEvent so that I could still work with the existing framework
 *
 * I want to work out how an event model would work where:
 *
 * a) The target gets the event
 * b) Any subscribers get the event
 * c) Room entries are handled by some sort of "BroadcastBehavior" (e.g. I enter a room and fire off an ImHereEvent)
 *     - MOBs would have to be programmed (a couple Behaviors, based on, well, different behavior) to respond some how:
 *         - Attack
 *         - Follow
 *         - Talk to
 *         - All of the above
 */
public class JMudEvent_ForChaining extends JMudEvent {

    private final transient JMudObject source;
	private final transient JMudObject target;

	public JMudEvent_ForChaining(final JMudEventType eventType, final JMudObject source, final JMudObject target) {
        super(eventType, source, target);
		this.source = source;
		this.target = target;
	}

	@Override
	public final boolean doJob() {

		// Build objects to send Event to List:
		Set<JMudObject> interestedObjects = new HashSet<JMudObject>();

        // the target is always interested
        interestedObjects.add(this.target);

        // Get anything registered for the targets event
        //    I suppose we could allow registering to be told when something is the source of an event
        //    e.g. if a Character attacks something, an interested NPC might be watching where the player was the SOURCE of the attack,
        //         not the target  ... "Hey, no fighting in my store [out comes shotgun]"
        interestedObjects.addAll(JMudEventRegistrar.getInstance().getTargetJMudObjectBySourceAndEvent(this.target, this.getEventType(), JMudEventParticipantRole.TARGET));
        interestedObjects.addAll(JMudEventRegistrar.getInstance().getTargetJMudObjectBySourceAndEvent(this.source, this.getEventType(), JMudEventParticipantRole.SOURCE));

		boolean isJobFinished = true;

		for (JMudObject object : interestedObjects) {

			// Now Handle the Target
			List<Behavior> behaviors = object.getBehaviors(this.getEventType());
            List<Behavior> nonTargetBehaviors = object.getNonTargetBehaviors(target, this.getEventType());

            if(behaviors == null){
                behaviors = new ArrayList<Behavior>();
            }

            if(nonTargetBehaviors != null){
                behaviors.addAll(nonTargetBehaviors);
            }

            synchronized (System.out) {
				System.out.println("(" + this.getID() + ") JMudEvent.doJob(): Found " + behaviors.size() + " behaviors of type "
						+ this.getEventType() + " from " + object.toStringShort() + " to run.");
			}

			if (!behaviors.isEmpty()) {
				for (Behavior b : behaviors) {
					Behavior newB = b.clone();
					newB.setEvent(this);

					synchronized (System.out) {
						System.out.println("(" + this.getID() + ") Behavior Cloning: " + b.toString()
								         + " was cloned into: " + newB.toString());
					}
					newB.submitSelf();
				}

			} else {
				//Set flag to false to show that this event did NOT evoke behavior from every object.
				isJobFinished = false;
			}
		}
		return isJobFinished;
	}
}