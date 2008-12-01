package jmud.engine.event;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jmud.engine.behavior.Behavior;
import jmud.engine.job.definitions.AbstractJob;
import jmud.engine.object.JMudObject;

public class JMudEvent extends AbstractJob {

   private UUID eventID = null;
   private JMudEventType eventType = null;

   private transient final JMudObject source;
   private transient final JMudObject target;

   /**
    * Generic map to handle any/all String named data that needs to accompany
    * the Event.
    */
   private Map<String, Object> dataMap = null;

   public JMudEvent(final JMudEventType eventType, final JMudObject source,
         final JMudObject target) {
      super();
      this.eventID = UUID.randomUUID();
      this.eventType = eventType;
      this.source = source;
      this.target = target;

      this.dataMap = Collections.synchronizedMap(new HashMap<String, Object>());
   }

   @Override
   public final boolean doJob() {

      List<Behavior> behs = this.target.getBehaviors(this);

      synchronized (System.out) {
         System.out.println("JMudEvent.doJob():  EventType: "
               + this.getEventType() + " Source: " + this.source.getName()
               + " Target: " + this.target.getName());
      }

      if (behs != null) {
         for (Behavior b : behs) {
            Behavior newB = b.clone();
            newB.setEvent(this);
            newB.submitSelf();
         }
         return true;
      } else {
         return false;
      }
   }

   public final Map<String, Object> getDataMap() {
      return dataMap;
   }

   public final UUID getEventID() {
      return this.eventID;
   }

   public final JMudEventType getEventType() {
      return eventType;
   }

   /**
    * The object on which the Event initially occurred.
    * @return The object on which the Event initially occurred.
    */
   public final JMudObject getSource() {
      return this.source;
   }

   /**
    * The objects on which the Event is targeted.
    * @return The object on which the Event initially occurred.
    */
   public final JMudObject getTarget() {
      return this.target;
   }

}
