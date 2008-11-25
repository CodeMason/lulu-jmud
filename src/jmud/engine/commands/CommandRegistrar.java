package jmud.engine.commands;

import jmud.engine.commands.definitions.Command;

import java.util.HashMap;

public class CommandRegistrar {
	/*
	 * ********************************************
	 * Singleton Implementation
	 * ********************************************
	 */
	/**
	 * Protected constructor is sufficient to suppress unauthorized calls to the
	 * constructor
	 */
	protected CommandRegistrar() {
	}

	/**
	 * JobManagerHolder is loaded on the first execution of
	 * JobManager.getInstance() or the first access to
	 * JobManagerHolder.INSTANCE, not before.
	 */
	private static class CommandRegistrarHolder {
		private static final CommandRegistrar INSTANCE = new CommandRegistrar();
	}

	public static CommandRegistrar getInstance() {
		return CommandRegistrarHolder.INSTANCE;
	}

	/*
	 * ********************************************
	 * Concrete Class Implementation
	 * ********************************************
	 */

	private final HashMap<String, Command> cmdMap = new HashMap<String, Command>();


	public void init () {

	}

	public void addCommand(String alias, Command cmd) {
		synchronized (this.cmdMap) {
			this.cmdMap.put(alias, cmd);
		}
	}

	public Command getCommand(String alias) {
		Command c = null;
		synchronized (this.cmdMap) {
			c = this.cmdMap.get(alias);
		}
		return c;
	}

	public Command remCommand(String alias) {
		Command c = null;
		synchronized (this.cmdMap) {
			c = this.cmdMap.remove(alias);
		}
		return c;
	}

}

















