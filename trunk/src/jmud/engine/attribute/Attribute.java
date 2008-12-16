package jmud.engine.attribute;

/**
 * Attribute is a data object that holds a name/value pair.
 *
 * @author Dave Loman (CM: oooh, Dave _H_ Loman, ... I thought it was the other Dave Loman :D)
 * Yeah yeah, it auto filled the value :/ Fixt
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
    * @param inName the attribute name
    * @param inValue the attribute value
    */
   public Attribute(final String inName, final Object inValue) {
		super(); // always call the super constructor... just good form! ;)
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

    public enum AttributeType{
        OBJECT_BULK,
        CONTAINING_BULK,
        OBJECT_SLOT_TYPE,
        CONTAINING_SLOT_TYPE
    }

}
