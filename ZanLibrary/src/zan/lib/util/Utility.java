package zan.lib.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utility {

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

	public static String readFileAsString(String filename) {
		StringBuilder content = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			while((line = br.readLine()) != null) content.append(line).append('\n');
			br.close();
		} catch (IOException e) {
			System.err.println("Error reading file  '" + filename + "':\n" + e);
		}
		return content.toString();
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

}
