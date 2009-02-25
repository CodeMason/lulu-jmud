package jmud.engine.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import jmud.engine.config.JMudConfig;
import jmud.engine.config.JMudConfigElement;

public class SplashScreenLoader {
	public static String getSplashScreen() {
		String ssFile = JMudConfig.getInstance().getConfigElement(JMudConfigElement.splashScreenFileName);

		//Check to see if there is a ConfigElement
		if (ssFile != null && ssFile.length() > 0) {
			
			//If so, try to load from that file
			String retVal = loadSplashScreenFromFile(ssFile);

			//If that doesn't work, return the default.
			if (retVal == null) {
				return DEFAULT_SPLASH_SCREEN;
			}
			//Else return the loaded screen;
			return retVal;
			
		} else {
			//Else return the default.
			return DEFAULT_SPLASH_SCREEN;
		}

	}

	public static String loadSplashScreenFromFile(String pathAndFile) {
		try {
			File file = new File(pathAndFile + "");
			FileReader fr;
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String out = "";
			String line = "";
			while ((line = br.readLine()) != null) {
				out += line + "\n";
			}

			// close file reader
			fr.close();
			// close buffer reader
			br.close();

			return out;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static final String DEFAULT_SPLASH_SCREEN = new StringBuffer().append(JMudStatics.CRNL).append(
			"_________ _______           ______  ").append(JMudStatics.CRNL).append(
			"\\__    _/(       )|\\     /|(  __  \\ ").append(JMudStatics.CRNL).append(
			"   )  (  | () () || )   ( || (  \\  )").append(JMudStatics.CRNL).append(
			"   |  |  | || || || |   | || |   ) |").append(JMudStatics.CRNL).append(
			"   |  |  | |(_)| || |   | || |   | |").append(JMudStatics.CRNL).append(
			"   |  |  | |   | || |   | || |   ) |").append(JMudStatics.CRNL).append(
			"|\\_)  )  | )   ( || (___) || (__/  )").append(JMudStatics.CRNL).append(
			"(____/   |/     \\|(_______)(______/ ").append(JMudStatics.CRNL).append("The Java Mud Framework.").append(
			JMudStatics.CRNL).append(JMudStatics.CRNL).append("---------------------------------").append(
			JMudStatics.CRNL).toString();

}
