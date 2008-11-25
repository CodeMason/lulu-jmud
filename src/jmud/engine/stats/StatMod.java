package jmud.engine.stats;

/**
 * Represents a modification to a Stat. Contains a name for ease of look up and
 * a category for Mod filtering.
 * 
 * @author David Loman
 * @version 0.1
 */

public class StatMod {
	private String name = "";
	private int value;
	private String category = "";

	public StatMod(String category, String name, int value) {
		super();
		this.category = category;
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
