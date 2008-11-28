package jmud.engine.attribute;

/**
 * Attribute is just a struct that holds a name/value pair
 * 
 * @author david.h.loman
 *
 */
public class Attribute {
	private String name = "";
	private Object value = null;

	public Attribute(String name, Object value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

}
