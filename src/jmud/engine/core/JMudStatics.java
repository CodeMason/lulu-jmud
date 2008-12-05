package jmud.engine.core;

public class JMudStatics {

	//TODO ultimately, these values will be read in from a config file.
	public final static String dbUrl = "jdbc:mysql://localhost:3306/mysql";
	public final static String dbUName = "jmud";
	public final static String dbPassWd = "jmud";
	public final static int dbPort = 3306;
	  
	
	
	
	
	// These are only temporary.
	public final static String hardcodeUName = "admin";
	public final static String hardcodePasswd = "admin";
	public final static String[] characters = { "Slinky", "Stinky", "Pinky" };

	// This determines the MAX length of a single line of input from a console.
	public final static int CONNECTION_READ_BUFFER_SIZE = 8196;

	public final static int MAX_LOGIN_ATTEMPTS = 3;

	public final static String CRLF = "\r\n";
	
	public final static String SplashScreen = JMudStatics.CRLF + 
				"_________ _______           ______  " + JMudStatics.CRLF + 
				"\\__    _/(       )|\\     /|(  __  \\ " + JMudStatics.CRLF + 
				"   )  (  | () () || )   ( || (  \\  )" + JMudStatics.CRLF + 
				"   |  |  | || || || |   | || |   ) |" + JMudStatics.CRLF + 
				"   |  |  | |(_)| || |   | || |   | |" + JMudStatics.CRLF + 
				"   |  |  | |   | || |   | || |   ) |" + JMudStatics.CRLF + 
				"|\\_)  )  | )   ( || (___) || (__/  )" + JMudStatics.CRLF + 
				"(____/   |/     \\|(_______)(______/ " + JMudStatics.CRLF + JMudStatics.CRLF + 
				"The Java Mud Framework." +		JMudStatics.CRLF + JMudStatics.CRLF + 
				"Username: ";
	
}
