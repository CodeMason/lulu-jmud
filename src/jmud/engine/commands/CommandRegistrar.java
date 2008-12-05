package jmud.engine.commands;

import java.util.HashMap;

/**
 * @author david.h.loman
 */
public class CommandRegistrar {
	/**
	 * <code>Holder</code> is loaded on the first execution of
	 * <code>CommandRegistrar.getInstance()</code> or the first access to
	 * <code>Holder.INSTANCE</code>, not before.
	 */
	private static class Holder {
		/**
		 * The singleton instance.
		 */
		private static final CommandRegistrar INSTANCE = new CommandRegistrar();

		/**
		 * <code>Holder</code> is a utility class. Disallowing public/default
		 * constructor.
		 */
		private Holder() {
		}
	}

	/**
	 * @return the singleton instance
	 */
	public static CommandRegistrar getInstance() {
		return Holder.INSTANCE;
	}

	private final HashMap<String, AbstractCommandDef> cmdMap = new HashMap<String, AbstractCommandDef>();

	/**
	 * Protected constructor is sufficient to suppress unauthorized calls to the
	 * constructor.
	 */
	protected CommandRegistrar() {
	}

	public final void addAbstractCommandDef(final String alias, final AbstractCommandDef cmd) {
		synchronized (this.cmdMap) {
			this.cmdMap.put(alias, cmd);
		}
	}

	public final AbstractCommandDef getAbstractCommandDef(final String alias) {
		AbstractCommandDef c = null;
		synchronized (this.cmdMap) {
			c = this.cmdMap.get(alias);
		}
		return c;
	}

	public void init() {

	}

	public final AbstractCommandDef remAbstractCommandDef(final String alias) {
		AbstractCommandDef c = null;
		synchronized (this.cmdMap) {
			c = this.cmdMap.remove(alias);
		}
		return c;
	}

}
