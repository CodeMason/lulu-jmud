package jmud.engine;

import java.io.IOException;
import jmud.engine.behavior.BehaviorManager;
import jmud.engine.behavior.BuiltinBehaviorLoader;
import jmud.engine.config.JMudConfig;
import jmud.engine.job.JobManager;
import jmud.engine.netio.JMudClientManager;

public class JMud implements Runnable {

	/*
	 * Static Main
	 */

	public static void main(String[] args) {
		JMud.getInstance().run();
	}

	/*
	 * BEGIN Singleton Implementation
	 */
	private static final class Holder {
		private static final JMud INSTANCE = new JMud();

		private Holder() {
		}
	}

	public static JMud getInstance() {
		return Holder.INSTANCE;
	}

	private JMud() {
		// Get handles on the singletons.
		this.config = JMudConfig.getInstance();
		this.jobMan = JobManager.getInstance();
		this.behaveMan = BehaviorManager.getInstance();
	}

	/*
	 * END Singleton Implementation
	 */

	/*
	 * Class Def
	 */

	protected boolean runCmd = false;
	protected boolean runStatus = false;
	protected Thread jmudThread;
	protected boolean inited = false;

	protected JMudConfig config;
	protected JMudClientManager jmcMan;
	protected JobManager jobMan;
	protected BehaviorManager behaveMan;

	public boolean init(String configFile) {
		boolean retVal;

		// Attempt to load the config file supplied.
		retVal = this.config.loadConfig(configFile);

		if (!retVal) {
			// Attempt default file.
			System.err.println("Loading config from: " + configFile + " failed.  Attempting default file.");
			retVal = this.config.loadConfig();

			if (!retVal) {
				System.err.println("Default config load failed!");
				return false;
			} else {
				System.out.println("Default config load succeeded");
				this.inited = true;
				return true;
			}
		}

		System.out.println("Config loaded from: " + configFile);
		this.inited = true;
		return true;
	}

	public void start() {
		System.out.println(this.getClass().getSimpleName() + ": Received Startup Command.");
		this.runCmd = true;
		this.jmudThread = new Thread(this, this.getClass().getSimpleName() + "-Thread");
		this.jmudThread.start();
	}

	private boolean _shutdown() {
		this.jmcMan.stop();
		this.jobMan.stopAllWorkers();
		return true;
	}

	@Override
	public void run() {

		// Start up the subsystems
		if (!this._startup()) {
			System.err.println("JMud.run() aborted during startup().");
			return;
		}

		// Load persisted data
		if (!this._loadData()) {
			System.err.println("JMud.run() aborted during loadData().");
			return;
		}

		this.runStatus = true;
		/*
		 * Run
		 */

		this.runStatus = false;

		if (!this._shutdown()) {
			System.err.println("JMud.run() aborted during shutdown().");
			return;
		}
		return;
	}

	private boolean _startup() {
		// Start a JobManager
		this.jobMan.init(4);

		// Load the 'built-in' Behaviors
		BuiltinBehaviorLoader.load();

		// initialize JobManager with 10 worker
		this.jobMan.init(10);

		
		
		
		// initialize and start JMudClientManager
		try {
			this.jmcMan = new JMudClientManager(54321);
		} catch (IOException e) {
			System.err.println("JMudClientManager threw an IOException. Aborting.");
			JobManager.getInstance().stopAllWorkers();
			return false;
		}
		this.jmcMan.start();
		return true;
	}

	public void stop() {
		System.out.println(this.getClass().getSimpleName() + ": Received Shutdown Command.");
		this.runCmd = false;
		return;
	}

	private boolean _loadData() {
		return true;
	}

	/*
	 * GETTERS N SETTERS
	 */

	/**
	 * @return the JMUD's main loop run command
	 */
	public final boolean getRunCmd() {
		return this.runCmd;
	}

	/**
	 * @return the JMUD's main loop run status
	 */
	public final boolean getRunStatus() {
		return this.runStatus;
	}

	/**
	 * @return the Thread this JMUD's is running in.
	 */
	public final Thread getThread() {
		return this.jmudThread;
	}

}
