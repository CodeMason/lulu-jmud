package jmud.engine.core;

import jmud.engine.job.JobManager;

import java.io.File;
import java.io.FileReader;

public class JMudStatics {

	//TODO ultimately, these values will be read in from a config file.
    public static final String dbUrl = "jdbc:mysql://localhost:3306/jmuddb";
	public static final String dbUName = "root";
	public static final String dbPassWd = "jmud";
	public static final int dbPort = 3306;
    public static final String log4jConfigFile = "log4j.lcf";
    // ToDo CM: load from config so that a different default JobManager can be specified
    public static JobManager defaultJobManager = JobManager.getLazyLoadedInstance();


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

    private static String SPLASH_SCREEN_FILE_NAME = "splashScreen.txt";

     public static JobManager getDefaultJobManager(){
        return defaultJobManager;
    }

    public static void setDefaultJobManager(JobManager jobManager) {
        defaultJobManager = jobManager;
    }

    public static String getSplashScreen(){
        return SPLASH_SCREEN_FROM_FILE.length() > 0 ? SPLASH_SCREEN_FROM_FILE : DEFAULT_SPLASH_SCREEN;
    }

    public static void reloadSplashScreenFromFile(String splashScreenFileName){
        SPLASH_SCREEN_FILE_NAME = splashScreenFileName;
        SPLASH_SCREEN_FROM_FILE = getSplashScreenFromFile();
    }

    private static String SPLASH_SCREEN_FROM_FILE = getSplashScreenFromFile();

    private static String getSplashScreenFromFile(){
        File splashScreenFile = new File(SPLASH_SCREEN_FILE_NAME);
        return(readFile(splashScreenFile));
    }

    private static String readFile(File fileToRead){
        FileReader fileReader = null;
        int charsRead = 0;
        String textFromFile = "";
        char[] charsFromFile = new char[8192];

        if(fileToRead.exists()){
            try {
                fileReader = new FileReader(SPLASH_SCREEN_FILE_NAME);

                charsRead = fileReader.read(charsFromFile);
            }catch(Exception e){
                return textFromFile;
            } finally {
                if(fileReader != null){
                    try{
                        fileReader.close();
                    }catch(Exception e){
                        // do nothing
                    }
                }
            }
            textFromFile = String.valueOf(charsFromFile, 0, charsRead);
        }
        return textFromFile;
    }

    private static final String DEFAULT_SPLASH_SCREEN = new StringBuffer().append(CRLF)
				.append("_________ _______           ______  ").append(CRLF)
				.append("\\__    _/(       )|\\     /|(  __  \\ ").append(CRLF)
				.append("   )  (  | () () || )   ( || (  \\  )").append(CRLF)
				.append("   |  |  | || || || |   | || |   ) |").append(CRLF)
				.append("   |  |  | |(_)| || |   | || |   | |").append(CRLF)
				.append("   |  |  | |   | || |   | || |   ) |").append(CRLF)
				.append("|\\_)  )  | )   ( || (___) || (__/  )").append(CRLF)
				.append("(____/   |/     \\|(_______)(______/ ").append(CRLF)
				.append("The Java Mud Framework.").append(CRLF).append(CRLF)
				.append("Username: ").toString();
}
