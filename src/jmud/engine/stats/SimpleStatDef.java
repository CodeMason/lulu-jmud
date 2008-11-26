package jmud.engine.stats;

import jmud.engine.core.Namespace;

/**
 * A simple implementation of an AbstractStatDef for use with the core JMUD
 * engine.
 *
 * ToDO: can events be kicked off by stat changes? If so we'll want to
 *       look at using behaviors for stat events.
 *       (e.g. sending a message to the user that their stat max has changed,
 *             or that they can now wield a certain weapon or armor)
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
