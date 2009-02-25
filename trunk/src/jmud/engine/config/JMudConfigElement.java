package jmud.engine.config;

import jmud.engine.Utilities;

public enum JMudConfigElement {
	dbUrl {
		@Override
		public String getDescription() {
			return "The JDBC protocol, sql type, host and port";
		}

		@Override
		public String getDefaultValue() {
			return "jdbc:mysql://localhost:3306/";
		}
	},
	dbName {
		@Override
		public String getDescription() {
			return "The JDBC database name";
		}

		@Override
		public String getDefaultValue() {
			return "jmud";
		}
	},
	dbUName {
		@Override
		public String getDescription() {
			return "The Username used to connect to the Database";
		}

		@Override
		public String getDefaultValue() {
			return "jmud_server";
		}
	},
	dbPassWd {
		@Override
		public String getDescription() {
			return "The Password used to connect to the Database";
		}

		@Override
		public String getDefaultValue() {
			return "jmud";
		}
	},
	maxLoginAttempts {
		@Override
		public String getDescription() {
			return "Maximum allowed attempts to login before the account is locked";
		}

		@Override
		public String getDefaultValue() {
			return "3";
		}
	},
	splashScreenFileName {
		@Override
		public String getDescription() {
			return "Splashscreen text file";
		}

		@Override
		public String getDefaultValue() {
			return "splashScreen.txt";
		}
	},
	numOfWorkers {
		@Override
		public String getDescription() {
			return "Number of worker threads to start";
		}

		@Override
		public String getDefaultValue() {
			return "10";
		}
	};

	abstract public String getDescription();

	abstract public String getDefaultValue();
	
	public String getCurrentValue() {
		return JMudConfig.getInstance().getConfigElement(this);
	}
	
	public Short getCurrentValueAsShort() {
		return Utilities.stringToShort(JMudConfig.getInstance().getConfigElement(this));
	}
	
	public Integer getCurrentValueAsInteger() {
		return Utilities.stringToInteger(JMudConfig.getInstance().getConfigElement(this));
	}
	
	public Long getCurrentValueAsLong() {
		return Utilities.stringToLong(JMudConfig.getInstance().getConfigElement(this));
	}
	
	public Float getCurrentValueAsFloat() {
		return Utilities.stringToFloat(JMudConfig.getInstance().getConfigElement(this));
	}
	
	public Double getCurrentValueAsDouble() {
		return Utilities.stringToDouble(JMudConfig.getInstance().getConfigElement(this));
	}
	
	
	
	public Short getDefaultValueAsShort() {
		return Utilities.stringToShort(this.getDefaultValue());
	}
	
	public Integer getDefaultValueAsInteger() {
		return Utilities.stringToInteger(this.getDefaultValue());
	}
	
	public Long getDefaultValueAsLong() {
		return Utilities.stringToLong(this.getDefaultValue());
	}
	
	public Float getDefaultValueAsFloat() {
		return Utilities.stringToFloat(this.getDefaultValue());
	}
	
	public Double getDefaultValueAsDouble() {
		return Utilities.stringToDouble(this.getDefaultValue());
	}
	
}
