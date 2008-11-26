package jmud.engine.object;

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
