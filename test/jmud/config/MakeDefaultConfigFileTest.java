package jmud.config;

import jmud.engine.config.JMudConfig;

public class MakeDefaultConfigFileTest {

	public static void main(String[] args)
	{
		boolean retVal = JMudConfig.getInstance().loadConfig();
		System.out.println("\nloadConfig() returned: " + retVal);
		
		JMudConfig.getInstance().printConfig();
		System.out.println("\n\n\n");
		
		retVal = JMudConfig.getInstance().loadConfig("testing.config");
		System.out.println("\nloadConfig() returned: " + retVal);
		
		JMudConfig.getInstance().printConfig();
		System.out.println("\n\n\n");
		
		JMudConfig.getInstance().putConfigElement("dbPassWd", "NunyaDamnBidness");
		JMudConfig.getInstance().printConfig();
		System.out.println("\n\n\n");
		
		JMudConfig.getInstance().writeConfigFile();
		JMudConfig.getInstance().printConfig();
		System.out.println("\n\n\n");
		
		
		return;
	}
}
