package jmud.engine.behavior;

import jmud.engine.event.JMudEvent;
import jmud.engine.event.JMudEventType;
import jmud.engine.job.definitions.AbstractJob;
import jmud.engine.object.JMudObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Following the command design pattern [GoF], a <code>Behavior</code> is a
 * discrete portion of logic that can be associated with a
 * <code>JMudObject</code> and called in response to a <code>JMudEvent</code>.
 *
 * <code>Behaviors</code> should check that they are handling the right event. Each
 * <code>Behavior</code> might list a top level event class that it handles.
 * ToDo: How will a <code>Behavior</code> get what objects it needs to work on?
 * Should we always pass in the source event and target object?
 */
public abstract class Behavior extends AbstractJob {

	protected List<JMudEventType> eventTypesHandled = Collections.synchronizedList(new ArrayList<JMudEventType>());

	//Always initialize non-final's to something, even if its null.
	protected JMudEvent event = null;
	protected JMudObject owner = null;

	/**
	 * Default constructor.
	 */
	public Behavior(JMudObject owner) {
		this.owner = owner;
	}

    /** return the owner of this behavior */
    public JMudObject getOwner(){
        return owner;
    }

	/**
	 * perform this <code>Behavior's</code> behavior.
	 *
	 * @return true if behavior completes successfully
	 */
	protected boolean behave() {
		if (!canHandleEvent(event)) {
			return false;
		}

		if (this.owner == this.event.getTarget()) {
			return this.targetBehavior();
		} else if (this.owner == this.event.getSource()) {
			return this.sourceBehavior();
		} else  {
			return this.ccBehavior();
		}
	}

    protected boolean canHandleEvent(JMudEvent event){
        return event != null && this.eventTypesHandled.contains(this.event.getEventType());
    }

    /**
	 * perform this <code>Behavior's</code> behavior if this behavior belongs to the
	 * event's target object
	 *
	 * @return true if behavior completes successfully
	 */
	protected boolean targetBehavior(){
        return true;
    }


	/**
	 * perform this <code>Behavior's</code> behavior if this behavior belongs to the
	 * event's source object
	 *
	 * @return true if behavior completes successfully
	 */
	protected boolean sourceBehavior(){
        return true;
    }

	/**
	 * perform this <code>Behavior's</code> behavior if this behavior belongs to a
	 * JMudObject other than the event's target or source object
	 *
	 * @return true if behavior completes successfully
	 */
	protected boolean ccBehavior(){
        return true;
    }

	/**
	 * @see jmud.engine.job.definitions.AbstractJob#doJob()
	 * @return true if <code>doJob</code>, and thus, <code>behave</code>
	 *         complete successfully
	 */
	@Override
	public final boolean doJob() {
		return this.behave();
	}

	/**
	 * @return the <code>JMudEvent</code>
	 */
	public final JMudEvent getEvent() {
		return event;
	}

	/**
	 * @return the list of event types handled
	 */
	public final List<JMudEventType> getEventTypesHandled() {
		return eventTypesHandled;
	}

	/**
	 * @param inEvent the set event
	 */
	public final void setEvent(final JMudEvent inEvent) {
		this.event = inEvent;
	}

    public Behavior clone(){
        return null;
    }

    @Override
	public String toString() {
		StringBuilder out = new StringBuilder("BehaviorID:  " + this.getUUID());

		if (this.event != null) {
            out.append("\t (")
               .append(this.event.toString())
               .append(")");
		} else {
			out.append("\t No Attached event.");
		}

		return out.toString();
	}
}
