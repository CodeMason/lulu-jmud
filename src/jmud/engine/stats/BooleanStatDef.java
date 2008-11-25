package jmud.engine.stats;

import jmud.engine.core.Namespace;

/**
 * A simple implementation of an AbstractStatDef for use with the core JMUD
 * engine.
 * 
 * @author David Loman
 * @version 0.1
 */
public class BooleanStatDef extends AbstractStatDef {
	
	public BooleanStatDef(String name, Namespace ns) {
		super(name, ns);
	}

	/*
	 * Value Modifiers
	 */

	@Override
	public int modCurrent(Stat s, int value) {
		int tval = s.getCurrent() + value;

		if (tval <= 0) {
			tval = 0;
		} else {
			tval = 1;
		}
	
		s.setCurrent(tval);
		return s.getCurrent();
	}

	@Override
	public int modMax(Stat s, int value) {
		s.setMax(1);
		System.err.println("Attemped to change MAX of a BooleanStat");
		return s.getMax();
	}

	@Override
	public int modMin(Stat s, int value) {
		s.setMin(0);
		System.err.println("Attemped to change MIN of a BooleanStat");
		return s.getMin();
	}

}
