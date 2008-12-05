package jmud.engine.commands;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

	private final Map<String, AbstractCommand> cmdMap = Collections.synchronizedMap(new HashMap<String, AbstractCommand>());

	/**
	 * Protected constructor is sufficient to suppress unauthorized calls to the
	 * constructor.
	 */
	protected CommandRegistrar() {
	}
    
    public void init() {
	}

	public final void addAbstractCommand(final AbstractCommand cmd) {
		for (String alias : cmd.getAliases()) {
			this.cmdMap.put(alias, cmd);
		}
	}

	public final AbstractCommand getAbstractCommand(final String alias) {
		return this.cmdMap.get(alias);
	}

	public final AbstractCommand remAbstractCommandAlias(final String alias) {
		return this.cmdMap.remove(alias);
	}

	public final void remAbstractCommand(final AbstractCommand cmd) {
        // ToDo CM: might want to remove based on the set of values in case the set of aliases has changed
        for (String alias : cmd.getAliases()) {
			this.cmdMap.remove(alias);
		}

	}

}
