package jmud.engine.event;

import jmud.engine.object.JMudObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This serves as a registrar for JMudEventSubscription objects It is
 * synchronized in anyway as so far as the Maps and Lists are concerned.
 * Synchronization of the individual JMudEvent objects and JMudObject objects
 * must be done externally.
 * 
 * @author David Loman
 * 
 */

public class JMudEventRegistrar {
	/*
	 * 
	 * Singleton Implementation
	 */
	/**
	 * Protected constructor is sufficient to suppress unauthorized calls to the
	 * constructor
	 */
	protected JMudEventRegistrar() {
	}

	/**
	 * JobManagerHolder is loaded on the first execution of
	 * JobManager.getInstance() or the first access to
	 * JobManagerHolder.INSTANCE, not before.
	 */
	private static class Holder {
		private static final JMudEventRegistrar INSTANCE = new JMudEventRegistrar();
	}

	public static JMudEventRegistrar getInstance() {
		return Holder.INSTANCE;
	}

	/*
	 * 
	 * Concrete Class Implementation
	 */

	private final Map<UUID, JMudEventSubscription> uuidMap = Collections
			.synchronizedMap(new HashMap<UUID, JMudEventSubscription>());

	private final Map<JMudObject, List<JMudEventSubscription>> sourceMap = Collections
			.synchronizedMap(new HashMap<JMudObject, List<JMudEventSubscription>>());

	private final Map<JMudEventType, List<JMudEventSubscription>> eventMap = Collections
			.synchronizedMap(new HashMap<JMudEventType, List<JMudEventSubscription>>());

	private final Map<JMudObject, List<JMudEventSubscription>> targetMap = Collections
			.synchronizedMap(new HashMap<JMudObject, List<JMudEventSubscription>>());

	/*
	 * Subscription IO
	 */
	public void registerSubscription(JMudEventSubscription jmes) {
		// Add to uuidMap:
		this.addToUUIDMap(jmes);

		// Add to targetMap:
		this.addToTargetMap(jmes);

		// Add to eventMap:
		this.addToEventMap(jmes);

		// Add to sourceMap:
		this.addToSourceMap(jmes);

	}

	public void unregisterSubscription(JMudEventSubscription jmes) {
		// Remove from uuidMap:
		this.remFromUUIDMap(jmes);

		// Remove from targetMap:
		this.remFromTargetMap(jmes);

		// Remove from eventMap:
		this.remFromEventMap(jmes);

		// Remove from sourceMap:
		this.remFromSourceMap(jmes);
	}

	public JMudEventSubscription getSubscriptionByUUID(UUID uuid) {
		return this.uuidMap.get(uuid);
	}

	public List<JMudEventSubscription> getSubscriptionsByTarget(JMudObject jmo) {
		return this.targetMap.get(jmo);
	}

	public List<JMudEventSubscription> getSubscriptionsBySource(JMudObject jmo) {
		return this.sourceMap.get(jmo);
	}

	public List<JMudEventSubscription> getSubscriptionsByEvent(JMudEvent jme) {
		return this.eventMap.get(jme);
	}

	/*
	 * UUID map IO
	 */
	private void addToUUIDMap(JMudEventSubscription jmes) {
		this.uuidMap.put(jmes.getSubscriptionID(), jmes);
	}

	private JMudEventSubscription remFromUUIDMap(JMudEventSubscription jmes) {
		return this.remFromUUIDMap(jmes.getSubscriptionID());
	}

	private JMudEventSubscription remFromUUIDMap(UUID uuid) {
		return this.uuidMap.remove(uuid);
	}

	/*
	 * Target map IO
	 */
	private void addToTargetMap(JMudEventSubscription jmes) {
		this.addToCommonMap(jmes, this.targetMap);
	}

	private boolean remFromTargetMap(JMudEventSubscription jmes) {
		return this.remFromCommonMap(jmes, this.targetMap);
	}

	/*
	 * Source map IO
	 */
	private void addToSourceMap(JMudEventSubscription jmes) {
		this.addToCommonMap(jmes, this.sourceMap);
	}

	private boolean remFromSourceMap(JMudEventSubscription jmes) {
		return this.remFromCommonMap(jmes, this.sourceMap);
	}

	/*
	 * Common Src/Tgt map IO
	 */
	private void addToCommonMap(JMudEventSubscription jmes,
			Map<JMudObject, List<JMudEventSubscription>> map) {
		// first, get the ArrayList keyed to the JMudObject:
		List<JMudEventSubscription> al = map.get(jmes.getSource());

		// check to see if there was a mapping!
		if (al == null) {
			// if not then make a new ArrayList
			al = Collections.synchronizedList(new ArrayList<JMudEventSubscription>());

			// Now that we have a valid list, add in our incoming
			// JMudEventSubscription
			al.add(jmes);

			// Now map the List into the map by source:
			this.sourceMap.put(jmes.getSource(), al);
		} else {
			// we have a good ArrayList returned:
			al.add(jmes);
		}
	}

	private boolean remFromCommonMap(JMudEventSubscription jmes,
			Map<JMudObject, List<JMudEventSubscription>> map) {
		// first, get the ArrayList keyed to the source JMudObject:
		List<JMudEventSubscription> al = map.get(jmes.getSource());

		// check to see if there was a mapping!
		if (al == null) {
			// if not then looks like we are all good to go.
			return true;

		} else {
			// we have a good ArrayList returned:
			return al.remove(jmes);
		}
	}

	/*
	 * Event map IO
	 */
	private void addToEventMap(JMudEventSubscription jmes) {
		// first, get the ArrayList keyed to the JMudEventType:
		List<JMudEventSubscription> al = this.eventMap.get(jmes.getEventType());

		// check to see if there was a mapping!
		if (al == null) {
			// if not then make a new ArrayList
			al = Collections.synchronizedList(new ArrayList<JMudEventSubscription>());

			// Now that we have a valid list, add in our incoming
			// JMudEventSubscription
			al.add(jmes);

			// Now map the List into the map by source:
			this.eventMap.put(jmes.getEventType(), al);
		} else {
			// we have a good ArrayList returned:
			al.add(jmes);
		}
	}

	private boolean remFromEventMap(JMudEventSubscription jmes) {
		// first, get the ArrayList keyed to the source JMudObject:
		List<JMudEventSubscription> al = this.eventMap.get(jmes.getEventType());

		// check to see if there was a mapping!
		if (al == null) {
			// if not then looks like we are all good to go.
			return true;

		} else {
			// we have a good ArrayList returned:
			return al.remove(jmes);
		}
	}

}
