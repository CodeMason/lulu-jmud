package jmud.engine.event;

import jmud.engine.object.JMudObject;

import java.util.*;

/**
 * This serves as a registrar for JMudEventSubscription objects It is
 * synchronized in anyway as so far as the Maps and Lists are concerned.
 * Synchronization of the individual JMudEvent objects and JMudObject objects
 * must be done externally.
 *
 * @author David Loman
 */

public class JMudEventRegistrar {
	/**
	 * Holder is loaded on the first execution of
	 * EventRegistrar.getInstance() or the first access to
	 * Holder.INSTANCE, not before.
	 */
	private static class Holder {
		private static final JMudEventRegistrar INSTANCE = new JMudEventRegistrar();
	}

	public static JMudEventRegistrar getInstance() {
		return Holder.INSTANCE;
	}

	private final Map<UUID, JMudEventSubscription> uuidMap = Collections
			.synchronizedMap(new HashMap<UUID, JMudEventSubscription>());

	/*
	 * Concrete Class Implementation
	 */

	private final Map<JMudObject, List<JMudEventSubscription>> sourceMap = Collections
			.synchronizedMap(new HashMap<JMudObject, List<JMudEventSubscription>>());

	private final Map<JMudEventType, List<JMudEventSubscription>> eventTypeMap = Collections.synchronizedMap(new EnumMap<JMudEventType, List<JMudEventSubscription>>(JMudEventType.class));

	private final Map<JMudObject, List<JMudEventSubscription>> ccTargetMap = Collections.synchronizedMap(new HashMap<JMudObject, List<JMudEventSubscription>>());

	/*
	 * Singleton Implementation
	 */
	/**
	 * Protected constructor is sufficient to suppress unauthorized calls to the
	 * constructor
	 */
	protected JMudEventRegistrar() {
	}

	public void init() {

	}

	/*
	 * Common Src/Tgt map IO
	 */
	private void addToCommonMap(JMudObject keyObject, final JMudEventSubscription subscription, final Map<JMudObject, List<JMudEventSubscription>> map) {
		// first, get the ArrayList keyed to the JMudObject:
		List<JMudEventSubscription> subscriptions = map.get(keyObject);

		// check to see if there was a mapping!
		if (subscriptions == null) {
			// if not then make a new ArrayList
			subscriptions = Collections.synchronizedList(new ArrayList<JMudEventSubscription>());

			// Now that we have a valid list, add in our incoming
			// JMudEventSubscription
			subscriptions.add(subscription);

			// Now map the List into the map by source:
            // ToDo CM: Huh? aren't we adding to the specified map?
            //this.sourceMap.put(keyObject, subscriptions);
            map.put(keyObject, subscriptions);
        } else {
			// we have a good ArrayList returned:
			subscriptions.add(subscription);
		}
	}

	/*
	 * Event map IO
	 */
	private void addToEventTypeMap(final JMudEventSubscription jmes) {
		// first, get the ArrayList keyed to the JMudEventType:
		List<JMudEventSubscription> al = this.eventTypeMap.get(jmes.getEventType());

		// check to see if there was a mapping!
		if (al == null) {
			// if not then make a new ArrayList
			al = Collections.synchronizedList(new ArrayList<JMudEventSubscription>());

			// Now that we have a valid list, add in our incoming
			// JMudEventSubscription
			al.add(jmes);

			// Now map the List into the map by source:
			this.eventTypeMap.put(jmes.getEventType(), al);
		} else {
			// we have a good ArrayList returned:
			al.add(jmes);
		}
	}

	/*
	 * Source map IO
	 */
	private void addToSourceMap(final JMudEventSubscription subscription) {
		this.addToCommonMap(subscription.getParticipant(), subscription, this.sourceMap);
	}

	/*
	 * Target map IO
	 *
	 * ToDO CM: grab the participant and map the subscription to it?
	 */
	private void addToCCTargetMap(final JMudEventSubscription subscription) {
		this.addToCommonMap(subscription.getCCTarget(), subscription, this.ccTargetMap);
	}

	public final JMudEventSubscription getSubscriptionByUUID(final UUID uuid) {
		return this.uuidMap.get(uuid);
	}

	public final List<JMudEventSubscription> getSubscriptionsByEventType(final JMudEventType jmet) {
		return this.eventTypeMap.get(jmet);
	}

	public final List<JMudEventSubscription> getSubscriptionsBySourceAndEventType(JMudObject source, JMudEventType jmet, EventParticipantRole role) {

		// first get the set of matches to target:
		//List<JMudEventSubscription> subscriptions = this.getSubscriptionsByCCTarget(source);
        List<JMudEventSubscription> subscriptions = this.getSubscriptionsBySource(source);

        if (subscriptions == null) {
			return null;
		}

		// Then filter that set by Source:
		List<JMudEventSubscription> out = new ArrayList<JMudEventSubscription>();
		for (JMudEventSubscription subscription : subscriptions) {
			// Match my UUID
			if (subscription.getEventType() == jmet && subscription.getRole().equals(role)) {
				out.add(subscription);
			}
		}

		return out;
	}


    public final List<JMudObject> getTargetJMudObjectBySourceAndEvent(JMudObject source, JMudEventType eventType){

       return this.getTargetJMudObjectBySourceAndEvent(source, eventType, EventParticipantRole.SOURCE);
    }

    public final List<JMudObject> getTargetJMudObjectBySourceAndEvent(JMudObject source, JMudEventType eventType, EventParticipantRole role) {

		List<JMudEventSubscription> subscriptions = this.getSubscriptionsBySourceAndEventType(source, eventType, role);

		List<JMudObject> targetObjects = new ArrayList<JMudObject>();

		if (subscriptions != null) {
			for (JMudEventSubscription subscription : subscriptions) {
				targetObjects.add(subscription.getCCTarget());
			}
		}
		return targetObjects;
	}

	/*
	 * UUID map IO
	 */
	private void addToUUIDMap(JMudEventSubscription jmes) {
		this.uuidMap.put(jmes.getSubscriptionID(), jmes);
	}

	public final List<JMudEventSubscription> getSubscriptionsBySource(final JMudObject jmo) {
		return this.sourceMap.get(jmo);
	}

	public final List<JMudEventSubscription> getSubscriptionsByCCTarget(final JMudObject jmo) {
		return this.ccTargetMap.get(jmo);
	}

	/*
	 * Subscription IO
	 */
	public final void registerSubscription(final JMudEventSubscription subscription) {
		// Add to uuidMap:
		this.addToUUIDMap(subscription);

		// Add to targetMap:
		this.addToCCTargetMap(subscription);

		// Add to eventTypeMap:
		this.addToEventTypeMap(subscription);

		// Add to sourceMap:
		this.addToSourceMap(subscription);

	}

	private boolean remFromCommonMap(final JMudEventSubscription jmes,
			final Map<JMudObject, List<JMudEventSubscription>> map) {
		// first, get the ArrayList keyed to the source JMudObject:
		List<JMudEventSubscription> al = map.get(jmes.getParticipant());

		// check to see if there was a mapping!
		if (al == null) {
			// if not then looks like we are all good to go.
			return true;

		} else {
			// we have a good ArrayList returned:
			return al.remove(jmes);
		}
	}

	private boolean remFromEventTypeMap(final JMudEventSubscription jmes) {
		// first, get the ArrayList keyed to the source JMudObject:
		List<JMudEventSubscription> al = this.eventTypeMap.get(jmes.getEventType());

		// check to see if there was a mapping!
		if (al == null) {
			// if not then looks like we are all good to go.
			return true;

		} else {
			// we have a good ArrayList returned:
			return al.remove(jmes);
		}
	}

	private boolean remFromSourceMap(final JMudEventSubscription jmes) {
		return this.remFromCommonMap(jmes, this.sourceMap);
	}

	private boolean remFromCCTargetMap(final JMudEventSubscription jmes) {
		return this.remFromCommonMap(jmes, this.ccTargetMap);
	}

	private JMudEventSubscription remFromUUIDMap(final JMudEventSubscription jmes) {
		return this.remFromUUIDMap(jmes.getSubscriptionID());
	}

	private JMudEventSubscription remFromUUIDMap(final UUID uuid) {
		return this.uuidMap.remove(uuid);
	}

	public final void unregisterSubscription(final JMudEventSubscription jmes) {
		// Remove from uuidMap:
		this.remFromUUIDMap(jmes);

		// Remove from targetMap:
		this.remFromCCTargetMap(jmes);

		// Remove from eventTypeMap:
		this.remFromEventTypeMap(jmes);

		// Remove from sourceMap:
		this.remFromSourceMap(jmes);
	}

}
