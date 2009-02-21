package jmud.engine.attribute;

/**
 * Attribute is a data object that holds a name/value pair.
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
    * @param name the attribute name
    * @param value the attribute value
    */
   public Attribute(final String name, final Object value) {
		super(); // always call the super constructor... just good form! ;)
		this.name = name;
		this.value = value;
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
        BULK,
        BULK_CONTAINED,
        SLOT_TYPE,
        SLOT_TYPE_CONTAINED
    }

}
