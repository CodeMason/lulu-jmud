package jmud.core;

import jmud.engine.config.JMudConfig;
import jmud.engine.core.SplashScreenLoader;

public class SplashScreenLoaderTest {

	public static void main(String[] args)
	{
		
		//Try to load the splashscreen without a ConfigElement in place.
		System.out.println(SplashScreenLoader.getSplashScreen());

		System.out.println("\n\n\n");

		//Instantiate and Load a Config Element
		JMudConfig.getInstance().loadConfig();
		JMudConfig.getInstance().printConfig();

		System.out.println("\n\n\n");
		
		//Try to load the splashscreen with a ConfigElement in place.
		System.out.println(SplashScreenLoader.getSplashScreen());
		
		
		return;
	}
}
