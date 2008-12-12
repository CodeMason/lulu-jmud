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

	private static class LazyLoader{
		private static final JMudEventRegistrar LAZY_LOADED_INSTANCE = new JMudEventRegistrar();
	}

	public static JMudEventRegistrar getLazyLoadedInstance() {
		return LazyLoader.LAZY_LOADED_INSTANCE;
	}

	private final Map<UUID, JMudEventSubscription> eventSubscriptionsByUUID = Collections.synchronizedMap(new HashMap<UUID, JMudEventSubscription>());
	private final Map<JMudObject, List<JMudEventSubscription>> eventSubscriptionsBySourceObject = Collections.synchronizedMap(new HashMap<JMudObject, List<JMudEventSubscription>>());
	private final Map<JMudEventType, List<JMudEventSubscription>> eventTypeSubscriptions = Collections.synchronizedMap(new EnumMap<JMudEventType, List<JMudEventSubscription>>(JMudEventType.class));
	private final Map<JMudObject, List<JMudEventSubscription>> eventSubscriptionsByCCObject = Collections.synchronizedMap(new HashMap<JMudObject, List<JMudEventSubscription>>());

	protected JMudEventRegistrar() {
        // Singleton
    }

    public final JMudEventSubscription getSubscription(UUID uuid){
        return this.eventSubscriptionsByUUID.get(uuid);
    }

    public final List<JMudEventSubscription> getSubscriptions(JMudEventType eventType){
        List<JMudEventSubscription> subscriptionsByEventType = this.eventTypeSubscriptions.get(eventType);
        return subscriptionsByEventType != null ? subscriptionsByEventType : Collections.synchronizedList(new ArrayList<JMudEventSubscription>());
    }

    public final List<JMudEventSubscription> getSubscriptions(JMudObject sourceObject, JMudEventType eventType, JMudEventParticipantRole participantRole){

        Set<JMudEventSubscription> matchingEventSubscriptions = new HashSet<JMudEventSubscription>(this.getSubscriptionsBySourceObject(sourceObject));
        matchingEventSubscriptions.retainAll(this.getSubscriptions(eventType));

        return filterSubscriptionsByParticipantRole(matchingEventSubscriptions, participantRole);
    }

    private List<JMudEventSubscription> getSubscriptions(JMudObject object, Map<JMudObject, List<JMudEventSubscription>> objectSubscriptions){
        List<JMudEventSubscription> existingSubscriptions = objectSubscriptions.get(object);
        return Collections.synchronizedList(existingSubscriptions != null ? existingSubscriptions : new ArrayList<JMudEventSubscription>());
    }

    public final List<JMudEventSubscription> getSubscriptionsByCCObject(final JMudObject ccObject){
        return this.eventSubscriptionsByCCObject.get(ccObject);
    }

    public final List<JMudEventSubscription> getSubscriptionsBySourceObject(final JMudObject sourceObject){
        List<JMudEventSubscription> subscriptionsBySource = this.eventSubscriptionsBySourceObject.get(sourceObject);
        return Collections.synchronizedList(subscriptionsBySource != null ? subscriptionsBySource : new ArrayList<JMudEventSubscription>());
    }

    private List<JMudEventSubscription> filterSubscriptionsByParticipantRole(Set<JMudEventSubscription> matchingEventSubscriptions, JMudEventParticipantRole participantRole){
        List<JMudEventSubscription> subscriptionsMatchingParticipantRole = new ArrayList<JMudEventSubscription>();

        for(JMudEventSubscription subscription : matchingEventSubscriptions){
            if(subscription.getRole().equals(participantRole)){
                subscriptionsMatchingParticipantRole.add(subscription);
            }
        }

        return subscriptionsMatchingParticipantRole;
    }

    public final List<JMudObject> getTargetObjects(JMudObject source, JMudEventType eventType){
        return this.getCCObjects(source, eventType, JMudEventParticipantRole.SOURCE);
    }

    private List<JMudObject> getCCObjects(JMudObject source, JMudEventType eventType, JMudEventParticipantRole roleJMud){

        List<JMudObject> targetObjects = new ArrayList<JMudObject>();
        List<JMudEventSubscription> subscriptions = this.getSubscriptions(source, eventType, roleJMud);

        for(JMudEventSubscription subscription : subscriptions){
            targetObjects.add(subscription.getCCTarget());
        }
        return targetObjects;
    }

    public final void registerSubscription(final JMudEventSubscription subscription){
        this.registerSubscriptionByUUID(subscription);
        this.registerSubscriptionByCCObject(subscription);
        this.registerEventSubscriptionByType(subscription);
        this.registerSubscriptionBySourceObject(subscription);
    }

    private void registerSubscriptionByUUID(JMudEventSubscription eventSubscription){
        this.eventSubscriptionsByUUID.put(eventSubscription.getSubscriptionID(), eventSubscription);
    }

    private void registerEventSubscriptionByType(final JMudEventSubscription newEventSubscription) {
		List<JMudEventSubscription> existingSubscriptions = getSubscriptions(newEventSubscription.getEventType());
        existingSubscriptions.add(newEventSubscription);
        this.eventTypeSubscriptions.put(newEventSubscription.getEventType(), existingSubscriptions);
	}

	private void registerSubscriptionBySourceObject(final JMudEventSubscription subscription) {
		this.registerSubscriptionByObject(subscription.getParticipant(), subscription, this.eventSubscriptionsBySourceObject);
	}

	private void registerSubscriptionByCCObject(final JMudEventSubscription subscription) {
		this.registerSubscriptionByObject(subscription.getCCTarget(), subscription, this.eventSubscriptionsByCCObject);
	}

    private void registerSubscriptionByObject(JMudObject keyObject, final JMudEventSubscription subscriptionToAdd, final Map<JMudObject, List<JMudEventSubscription>> objectSubscriptions){
        List<JMudEventSubscription> existingObjectSubscriptions = getSubscriptions(keyObject, objectSubscriptions);
        existingObjectSubscriptions.add(subscriptionToAdd);
        objectSubscriptions.put(keyObject, existingObjectSubscriptions);
    }

    public final void unregisterSubscription(final JMudEventSubscription subscriptionToUnregister){
        this.unregisterSubscriptionByUUID(subscriptionToUnregister);
        this.unregisterSubscriptionBySourceObject(subscriptionToUnregister);
        this.unregisterSubscriptionByCCObject(subscriptionToUnregister);
        this.unregisterSubscriptionByEventType(subscriptionToUnregister);
    }

    private boolean unregisterSubscriptionByObject(final JMudEventSubscription subscriptionToRemove, Map<JMudObject, List<JMudEventSubscription>> objectSubscriptions) {
		List<JMudEventSubscription> existingSubscriptions = objectSubscriptions.get(subscriptionToRemove.getParticipant());

        return existingSubscriptions == null || existingSubscriptions.remove(subscriptionToRemove);
	}

    private boolean unregisterSubscriptionByEventType(final JMudEventSubscription subscription) {
		List<JMudEventSubscription> existingSubscriptions = this.eventTypeSubscriptions.get(subscription.getEventType());

        // ToDo CM: What could the calling method do if it was false, ... it wouldn't know why
        return existingSubscriptions == null || existingSubscriptions.remove(subscription);
	}

    // ToDo CM: unit test
	private boolean unregisterSubscriptionBySourceObject(final JMudEventSubscription subscription) {
		return this.unregisterSubscriptionByObject(subscription, this.eventSubscriptionsBySourceObject);
	}

    // ToDo CM: unit test
	private boolean unregisterSubscriptionByCCObject(final JMudEventSubscription jmes) {
		return this.unregisterSubscriptionByObject(jmes, this.eventSubscriptionsByCCObject);
	}

    // ToDo CM: unit test
    private void unregisterSubscriptionByUUID(final JMudEventSubscription subscription) {
		this.unregisterSubscriptionByUUID(subscription.getSubscriptionID());
	}

	private void unregisterSubscriptionByUUID(final UUID uuid) {
		this.eventSubscriptionsByUUID.remove(uuid);
	}

}
