package jmud.testserver.stats.definitions;

import jmud.engine.stats.AbstractStatDef;
import jmud.engine.stats.Stat;

/**
 * An implementation of an AbstractStatDef for use with the HitPoints
 * 
 * @author David Loman
 * @version 0.1
 */
public class HitPoint_StatDef extends AbstractStatDef {

	public HitPoint_StatDef(String name) {
		super(name);
	}

	/*
	 * Value Modifiers
	 */


	@Override
	public int modCurrent(Stat s, int value) {
		s.setCurrent(s.getCurrent() + value);
		
		//Check to see if we are decrementing
		if (value < 0) {
			//Now see if the value exceeds 25% of Max Hitpoints
			if (value > (0.25 * s.getMax())) {
				this.invokeSystemShock();
			}
		}
		
		return s.getCurrent();
	}

	@Override
	public int modMax(Stat s, int value) {
		s.setMax(s.getMax() + value);
		return s.getMax();
	}

	@Override
	public int modMin(Stat s, int value) {
		s.setMin(s.getMin() + value);
		return s.getMin();
	}

	
	private void invokeSystemShock() {
		//find a way to apply a SystemShock condition to the target.
	}
	
}







