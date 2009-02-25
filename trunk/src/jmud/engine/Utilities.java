package jmud.engine;

public class Utilities {

	public static Short stringToShort(String s) {
		try {
			Short sh = Short.parseShort(s);
			return sh;
		} catch (NumberFormatException nfe) {
			return null;
		}
	}
	public static Integer stringToInt(String s) {
		try {
			Integer sh = Integer.parseInt(s);
			return sh;
		} catch (NumberFormatException nfe) {
			return null;
		}
	}
	public static Long stringToLong(String s) {
		try {
			Long sh = Long.parseLong(s);
			return sh;
		} catch (NumberFormatException nfe) {
			return null;
		}
	}
	public static Float stringToFloat(String s) {
		try {
			Float sh = Float.parseFloat(s);
			return sh;
		} catch (NumberFormatException nfe) {
			return null;
		}
	}

	public static Double stringToDouble(String s) {
		try {
			Double sh = Double.parseDouble(s);
			return sh;
		} catch (NumberFormatException nfe) {
			return null;
		}
	}

}
