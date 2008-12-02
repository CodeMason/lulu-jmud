package jmud.engine.attribute;

/**
 * Attribute is a data object that holds a name/value pair.
 * @author david.h.loman
 */
public class Attribute {
   /**
    * The attribute name.
    */
   private String name = "";
   /**
    * The attribute value.
    */
   private Object value = null;

   /**
    * Explicit constructor.
    * @param inName
    *           the attribute name
    * @param inValue
    *           the attribute value
    */
   public Attribute(final String inName, final Object inValue) {
      super();
      this.name = inName;
      this.value = inValue;
   }

   /**
    * @return the name of the attribute
    */
   public final String getName() {
      return name;
   }

   /**
    * @return the value of the attribute
    */
   public final Object getValue() {
      return value;
   }

}
