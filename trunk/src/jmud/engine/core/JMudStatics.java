package jmud.engine.core;

public class JMudStatics {

	//TODO ultimately, these values will be read in from a config file.
    public static final String dbUrl = "jdbc:mysql://localhost:3306/jmuddb";
	public static final String dbUName = "root";
	public static final String dbPassWd = "jmud";
	public static final int dbPort = 3306;


    //ToDo CM: this should be configurable per user (e.g. [CurrHP]:[MaxHP]/[CurrMana]:[MaxMana] etc.)
	public static final String PROMPT = "jmud> ";

	// These are only temporary.
    public static final String hardcodeUName = "admin";
	public static final String hardcodePasswd = "admin";
	public static final String[] characters = { "Slinky", "Stinky", "Pinky" };

	// This determines the MAX length of a single line of input from a console.
    public static final int CONNECTION_READ_BUFFER_SIZE = 8196;

	public static final int MAX_LOGIN_ATTEMPTS = 3;

	public static final String CRLF = "\r\n";

    public static final String SplashScreen = JMudStatics.CRLF +
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
