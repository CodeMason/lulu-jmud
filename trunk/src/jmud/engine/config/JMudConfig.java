package jmud.engine.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <code>BaseConfig</code> is used to configure items that the core framework
 * needs.
 * 
 * @author david.h.loman
 */

public class JMudConfig {
	/*
	 * BEGIN Singleton Implementation
	 */
	private static final class Holder {
		private static final JMudConfig INSTANCE = new JMudConfig();

		private Holder() {
		}
	}

	public static JMudConfig getInstance() {
		return Holder.INSTANCE;
	}

	private JMudConfig() {
	}

	/*
	 * END Singleton Implementation
	 */

	/*
	 * XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	 */

	/*
	 * Simple Key, Value map for config Elements read from file
	 */
	private Map<String, String> configElements = Collections.synchronizedMap(new HashMap<String, String>());
	private final String defaultFile = "jmud.config";
	private final char commentChar = '#';

	private String usingFile;

	/*
	 * Element Searching/Replacing
	 */
	public boolean containsConfigElement(String key) {
		return configElements.containsKey(key);
	}

	public String getConfigElement(String key) {
		return configElements.get(key);
	}

	public String putConfigElement(String key, String value) {
		return configElements.put(key, value);
	}

	public String remConfigElement(String key) {
		return configElements.remove(key);
	}

	/*
	 * Getters
	 */
	
	public String getUsingFile() {
		return this.usingFile;
	}
	
	/*
	 * File IO
	 */

	public boolean loadConfig() {
		// Try to load the default
		if (readConfigFromFile(this.defaultFile) == false) {
			// Make a default file
			this.makeDefaultConfigFile(this.defaultFile);
			
			// try again
			if( this.readConfigFromFile(this.defaultFile) == false) {
				return false;
			}
		}
		this.usingFile = this.defaultFile;
		return true;
	}

	public boolean loadConfig(String pathAndFile) {
		if (this.readConfigFromFile(pathAndFile) == false) {
			// Make a default file
			this.makeDefaultConfigFile(pathAndFile);

			// try again
			if( this.readConfigFromFile(pathAndFile) == false) {
				return false;
			}
		}
		this.usingFile = pathAndFile;
		return true;
	}

	private boolean readConfigFromFile(String pathAndFile) {
		try {
			// Create new file object
			File file = new File(pathAndFile + "");

			// create file reader instance
			FileReader fr = new FileReader(file);

			// create memory buffer to store data read from file
			BufferedReader br = new BufferedReader(fr);

			// variable to store text from file
			String line = "";
			while ((line = br.readLine()) != null) {
				line = line.replace("\n", "").replace("\r", "");

				// remove whitespace
				while (line.contains("\t")) {
					line = line.replace("\t", " ");
				}
				while (line.contains("  ")) {
					line = line.replace("  ", " ");
				}
				line = line.trim();

				// Check for comment:
				if (line.length() <= 3 || line.charAt(0) == this.commentChar) {
					// Min length is 3 because of a=b being the shortest
					// possible config line.
					continue;
				}

				// check for equal sign:
				if (line.contains("=") == false) {
					continue;
				}

				String[] lineArr = line.split("=");
				String key = lineArr[0].trim();
				String value = lineArr[1].trim();

				if (key.length() > 0 && value.length() > 0) {
					this.configElements.put(key, value);
					System.out.println("Read config element: " + key + "=" + value);
				}
			}
			// close file reader
			fr.close();
			// close buffer reader
			br.close();

		}
		// it is required to catch exception in case file is not found
		catch (FileNotFoundException e) {
			System.err.println("Config file not found. (" + pathAndFile + ")");
			return false;
		}
		// it is required to catch exception in case file cannot be read
		catch (IOException e) {
			System.err.println("Error reading Config file (" + pathAndFile + ")");
			return false;
		}

		return true;
	}

	private boolean makeDefaultConfigFile(String pathAndFile) {
		try {
			File f = new File(pathAndFile);
			FileWriter fw = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write("#####################################################\n");
			bw.write("#                                                   #\n");
			bw.write("# Default AutoGenerated Configuration File for jMUD #\n");
			bw.write("#                                                   #\n");
			bw.write("#####################################################\n");
			bw.write("\n");

			bw.write("#The JDBC protocol, path and port" + "\n");
			bw.write("dbURL=jdbc:mysql://localhost:3306/" + "\n");
			bw.write("" + "\n");
			bw.write("#The JDBC database name" + "\n");
			bw.write("dbName=jmud" + "\n");
			bw.write("" + "\n");
			bw.write("#The Username & password used to connect to the Database" + "\n");
			bw.write("dbUName=jmud_server" + "\n");
			bw.write("dbPassWd=jmud" + "\n");
			bw.write("" + "\n");
			bw.write("#Maximum allowed attempts to login before the account is locked" + "\n");
			bw.write("maxLoginAttempts=3" + "\n");
			bw.write("" + "\n");
			bw.write("#Splashscreen text file" + "\n");
			bw.write("splashScreenFileName=splashScreen.txt" + "\n");
			bw.write("" + "\n");
			bw.write("" + "\n");
			bw.write("" + "\n");
			bw.write("" + "\n");
			bw.write("" + "\n");
			bw.write("" + "\n");
			bw.write("" + "\n");
			bw.write("" + "\n");
			bw.write("" + "\n");

			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean writeConfigFile() {
		// Make sure we have a file first!
		if (this.usingFile == null || this.usingFile.length() <= 0) {
			return false;
		}

		try {
			System.out.println("Writing Config File: " + this.usingFile);

			File f = new File(this.defaultFile);
			FileWriter fw = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write("#####################################################\n");
			bw.write("#                                                   #\n");
			bw.write("#            Configuration File for jMUD            #\n");
			bw.write("#                                                   #\n");
			bw.write("#####################################################\n");
			bw.write("\n");

			Set<String> keys = this.configElements.keySet();

			for (String key : keys) {
				String value = this.configElements.get(key);
				bw.write(key + "=" + value + "\n\n");
				System.out.println("Wrote config element: " + key + "=" + value);
			}

			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public void printConfig() {
		System.out.println("\nCurrent Configuration Elements:\n");

		Set<String> keys = this.configElements.keySet();
		for (String key : keys) {
			String value = this.configElements.get(key);
			System.out.println(key + "=" + value + "");
		}

	}
}