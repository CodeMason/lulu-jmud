package jmud.engine.stats;

import jmud.engine.core.Namespace;

/**
 * A simple implementation of an AbstractStatDef for use with the core JMUD
 * engine.
 * 
 * @author David Loman
 * @version 0.1
 */
public class SimpleStatDef extends AbstractStatDef {

	public SimpleStatDef(String name, Namespace ns) {
		super(name, ns);
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

}
