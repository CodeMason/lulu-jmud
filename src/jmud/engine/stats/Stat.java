package jmud.engine.stats;

/**
 * Stat is the object that represents a Character's statistic.  Behavior of this Stat is defined
 * by a corresponding StatDef.  Stat objects and StatDef objects are correlated via the 'String name' field.
 *
 * @author David Loman
 * @version 0.1
 */
import java.util.ArrayList;

public class Stat {
	private String name = "";
	private int max = -1;
	private int min = -1;
	private int current = -1;
	private ArrayList<StatMod> mods = new ArrayList<StatMod>();

	//TODO why not have the Definition object reference attached at creation?
	private AbstractStatDef defToUse = null; 
	
	public Stat(String name, int max, int min, int current) {
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

	public ArrayList<StatMod> getMods() {
		return mods;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public String getName() {
		return name;
	}

}
