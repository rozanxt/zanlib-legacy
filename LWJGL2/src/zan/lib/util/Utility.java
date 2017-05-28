package zan.lib.util;

import java.util.Random;

public class Utility {

	private static Random rnd;

	public static Random getRandom() {
		if (rnd == null) rnd = new Random();
		return rnd;
	}
	public static void setRandomSeed(long seed) {
		getRandom().setSeed(seed);
	}

	public static boolean evaluate(double a, double b, double epsilon) {
		return (Math.abs(b-a) < epsilon);
	}
	public static boolean evaluate(double a, double b) {
		return evaluate(a, b, 1E-8);
	}

	public static float interpolateLinear(float lastState, float currentState, float ip) {
		return lastState + (currentState - lastState) * ip;
	}
	public static double interpolateLinear(double lastState, double currentState, double ip) {
		return lastState + (currentState - lastState) * ip;
	}

	public static int parseInt(String str) {
		int integer = 0;
		try {
			integer = Integer.parseInt(str);
		} catch(NumberFormatException e) {
			System.err.println("Error parsing '" + str + "' to integer!");
		}
		return integer;
	}
	public static float parseFloat(String str) {
		float floating = 0f;
		try {
			floating = Float.parseFloat(str);
		} catch(NumberFormatException e) {
			System.err.println("Error parsing '" + str + "' to float!");
		}
		return floating;
	}

	public static String getPrefix(String str) {
		if (str.contains(".")) {
			int dot = str.lastIndexOf(".");
			return str.substring(0, dot);
		}
		return str;
	}
	public static String getSuffix(String str) {
		if (str.contains(".")) {
			int dot = str.lastIndexOf(".");
			return str.substring(dot+1);
		}
		return str;
	}

}
