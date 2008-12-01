package jmud.engine.attribute;

/**
 * Attribute is just a struct that holds a name/value pair.
 * @author david.h.loman
 */
public class Attribute {
   private String name = "";
   private Object value = null;

   public Attribute(final String name, final Object value) {
      super();
      this.name = name;
      this.value = value;
   }

   public final String getName() {
      return name;
   }

   public final Object getValue() {
      return value;
   }

}
