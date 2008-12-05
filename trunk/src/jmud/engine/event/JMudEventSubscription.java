package jmud.engine.event;

import jmud.engine.object.JMudObject;

import java.util.UUID;

/**
 * This serves as a data class for linking both target and source
 * <code>JMudObjects</code> with a <code>JMudEvent</code>. It is *NOT*
 * synchronized in anyway.
 *
 * @author David Loman
 */
public class JMudEventSubscription {

	/**
	 * If this JMudObject...
	 */
	private JMudObject participant = null;

    /**
     * ... in this role (Target or Source) ...
     */
    private JMudEventParticipantRole roleJMud = null;

    /**
	 * Generates/Receives this JMudEvent...
	 */
	private JMudEventType eventType = null;

	/**
	 * Then also send the event to this JMudObject...
	 */
	private JMudObject ccTarget = null;

	/**
	 * UUID.
	 */
	UUID SubscriptionID = UUID.randomUUID();

    public JMudEventSubscription(final JMudEventType eventType, final JMudObject participant, final JMudObject target){
        this(eventType, participant, target, JMudEventParticipantRole.SOURCE);
    }

    public JMudEventSubscription(final JMudEventType eventType, final JMudObject participant, final JMudObject target, JMudEventParticipantRole roleJMud) {
		this.eventType = eventType;
		this.participant = participant;
		this.ccTarget = target;
        this.roleJMud = roleJMud;
    }

	public final JMudEventType getEventType() {
		return eventType;
	}

	public final JMudObject getParticipant() {
		return participant;
	}

	public final UUID getSubscriptionID() {
		return SubscriptionID;
	}

	public final JMudObject getCCTarget() {
		return ccTarget;
	}

    public JMudEventParticipantRole getRole(){
        return roleJMud;
    }
}
