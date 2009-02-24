package jmud.engine.core;

import jmud.engine.job.JobManager;

public class JMudStatics {
	
    public static final String log4jConfigFile = "log4j.lcf";
    // ToDo CM: load from config so that a different default JobManager can be specified
    public static JobManager jobManager = JobManager.getInstance();


    //ToDo CM: this should be configurable per user (e.g. [CurrHP]:[MaxHP]/[CurrMana]:[MaxMana] etc.)
	public static final String PROMPT = "jmud> ";

	// These are only temporary.
    public static final String hardcodeUName = "admin";
	public static final String hardcodePasswd = "admin";
	public static final String[] characters = { "Slinky", "Stinky", "Pinky" };

	public static final char CR = '\r';
	public static final char LF = '\n';
	public static final String CRLF = "\r\n";


     public static JobManager getJobManager(){
        return jobManager;
    }

    public static void setJobManager(JobManager jobManager) {
        JMudStatics.jobManager = jobManager;
    }

    
    public static final String MAIN_MENU = new StringBuffer().append(CRLF)
				.append("--~------------------------------").append(CRLF)
				.append("1) Login").append(CRLF)
				.append("2) View Wizlist").append(CRLF)
				.append("3) See Who is currently Online").append(CRLF)
				.append("4) Read about jMUD").append(CRLF)
				.append("5) Disconnect").append(CRLF)
				.append("--~------------------------------").append(CRLF)
				.append("Make a selection: ").toString();
}
