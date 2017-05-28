package zan.lib.droid.util;

import java.util.Random;

import android.content.Context;
import android.util.Log;

public class Utility {

	private static Context uContext = null;

	public static void init(Context context) {uContext = context;}
	public static Context getContext() {return uContext;}

	private static Random rnd;

	public static Random getRandom() {
		if (rnd == null) rnd = new Random();
		return rnd;
	}
	public static void setRandomSeed(long seed) {
		getRandom().setSeed(seed);
	}

	public static boolean evaluate(float a, float b, float epsilon) {
		return (Math.abs(b-a) < epsilon);
	}
	public static boolean evaluate(float a, float b) {
		return evaluate(a, b, (float)1E-8);
	}

	public static float interpolateLinear(float lastState, float currentState, float ip) {
		return lastState + (currentState - lastState) * ip;
	}

	public static int parseInt(String str) {
		int integer = 0;
		try {
			integer = Integer.parseInt(str);
		} catch(NumberFormatException e) {
			Log.e("Utility", "Error parsing '" + str + "' to integer");
		}
		return integer;
	}
	public static float parseFloat(String str) {
		float floating = 0f;
		try {
			floating = Float.parseFloat(str);
		} catch(NumberFormatException e) {
			Log.e("Utility", "Error parsing '" + str + "' to float");
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
