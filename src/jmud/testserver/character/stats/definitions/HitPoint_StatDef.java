package jmud.testserver.character.stats.definitions;

import jmud.engine.character.stats.AbstractStatDef;
import jmud.engine.character.stats.Stat;

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
		
	}
	
}







