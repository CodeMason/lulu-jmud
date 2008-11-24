package jmud.character.statmods;

public class StatModDef {
	private String name = "";
	private int max = -1;
	private int min = -1;
	private int current = -1;
	
	public StatModDef( String name, int max, int min, int current) {
		super();
		this.current = current;
		this.max = max;
		this.min = min;
		this.name = name;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public String getName() {
		return name;
	}

	
	
}
