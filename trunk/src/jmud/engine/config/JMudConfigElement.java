package jmud.engine.config;

public enum JMudConfigElement {
	dbUrl {
		@Override
		String getDescription() {
			return "The JDBC protocol, sql type, host and port";
		}

		@Override
		String getDefaultValue() {
			return "jdbc:mysql://localhost:3306/";
		}
	},
	dbName {
		@Override
		String getDescription() {
			return "The JDBC database name";
		}

		@Override
		String getDefaultValue() {
			return "jmud";
		}
	},
	dbUName {
		@Override
		String getDescription() {
			return "The Username used to connect to the Database";
		}

		@Override
		String getDefaultValue() {
			return "jmud_server";
		}
	},
	dbPassWd {
		@Override
		String getDescription() {
			return "The Password used to connect to the Database";
		}

		@Override
		String getDefaultValue() {
			return "jmud";
		}
	},
	maxLoginAttempts {
		@Override
		String getDescription() {
			return "Maximum allowed attempts to login before the account is locked";
		}

		@Override
		String getDefaultValue() {
			return "3";
		}
	},
	splashScreenFileName {
		@Override
		String getDescription() {
			return "Splashscreen text file";
		}

		@Override
		String getDefaultValue() {
			return "splashScreen.txt";
		}
	},
	numOfWorkers {
		@Override
		String getDescription() {
			return "Number of worker threads to start";
		}

		@Override
		String getDefaultValue() {
			return "10";
		}
	};

	abstract String getDescription();

	abstract String getDefaultValue();
}
