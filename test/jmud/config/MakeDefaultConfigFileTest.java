package jmud.config;

import jmud.engine.config.JMudConfig;

public class MakeDefaultConfigFileTest {

	public static void main(String[] args)
	{
		boolean retVal = JMudConfig.getInstance().loadConfig();
		System.out.println("\nloadConfig() returned: " + retVal);
		
		JMudConfig.getInstance().printConfig();
		return;
	}
}
