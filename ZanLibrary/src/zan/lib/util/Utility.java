package zan.lib.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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

	public static String readFileAsString(String path) {
		StringBuilder content = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			while((line = br.readLine()) != null) content.append(line).append('\n');
			br.close();
		} catch (IOException e) {
			System.err.println("Error reading file: " + path + ":\n" + e);
		}
		return content.toString();
	}

	public static int parseInt(String str) {
		int integer = 0;
		try {
			integer = Integer.parseInt(str);
		} catch(NumberFormatException e) {
			System.err.println("Error parsing '" + str + "' to integer");
		}
		return integer;
	}
	public static float parseFloat(String str) {
		float floating = 0f;
		try {
			floating = Float.parseFloat(str);
		} catch(NumberFormatException e) {
			System.err.println("Error parsing '" + str + "' to float");
		}
		return floating;
	}

	public static boolean isIntegerString(String str) {
		try {
			Integer.parseInt(str);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static String getPrefix(String fnm) {
		int dot;
		if ((dot = fnm.lastIndexOf(".")) == -1) {
			System.out.println("No prefix found for " + fnm);
			return fnm;
		} else return fnm.substring(0, dot);
	}

	public static String[] split(String str) {
		String[] a = str.split(" |\t|\n");
		ArrayList<String> b = new ArrayList<String>();
		for (int i=0;i<a.length;i++) if (!a[i].isEmpty()) b.add(a[i]);

		if (b.isEmpty()) {
			String[] tkns = new String[1];
			tkns[0] = "";
			return tkns;
		}

		String[] tkns = new String[b.size()];
		for (int i=0;i<tkns.length;i++) tkns[i] = b.get(i);
		return tkns;
	}

}
