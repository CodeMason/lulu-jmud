package jmud.engine.stats;

import java.util.ArrayList;
import jmud.engine.core.Namespace;

/**
 * Provides mandatory base implementation for all 'StatDef's'
 * 
 * @author David Loman
 * @version 0.1
 */
public abstract class AbstractStatDef {
	private String name = "";
	private ArrayList<Namespace> namespaces = new ArrayList<Namespace>();

	public AbstractStatDef(String name, Namespace ns) {
		super();
		this.name = name;
		this.namespaces.add(ns);
	}

	public String getName() {
		return name;
	}
	public ArrayList<Namespace> getNamespaces() {
		return namespaces;
	}
	
	public boolean add(Namespace ns) {
		return namespaces.add(ns);
	}

	public boolean remove(Object o) {
		return namespaces.remove(o);
	}

	public void selfRegister() {
		StatDefRegistrar.getInstance().addStatDef(this);
	}

	/*
	 * Value Modifiers
	 */

	public int incrMin(Stat s) {
		return this.modMin(s, 1);
	}

	public int incrMax(Stat s) {
		return this.modMax(s, 1);
	}

	public int incrCurrent(Stat s) {
		return this.modCurrent(s, 1);
	}

	public int decrMin(Stat s) {
		return this.modMin(s, -1);
	}

	public int decrMax(Stat s) {
		return this.modMax(s, -1);
	}

	public int decrCurrent(Stat s) {
		return this.modCurrent(s, -1);
	}

	public abstract int modMin(Stat s, int value);

	public abstract int modMax(Stat s, int value);

	public abstract int modCurrent(Stat s, int value);

}
