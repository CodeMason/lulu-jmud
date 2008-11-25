package jmud.engine.config;

/**
 *
 * 25NOV08:  AbstractConfig is the base, abstract class that mandates
 * the minimum required operations for a subclass.  The config system
 * is used to load in all the single instance subclasses for the 
 * various *Managers in jmud.
 *
 *
 *
 *
 * Created on April 21, 2002, 4:24 PM
 */




public abstract class AbstractConfig {
	protected String name = "";
	public abstract boolean doConfig();

	public AbstractConfig() {
		super();
		this.name = this.getClass().getSimpleName();
	}

	public String getName() {
		return name;
	}

}
