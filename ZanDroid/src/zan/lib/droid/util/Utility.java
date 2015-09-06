package zan.lib.droid.util;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utility {

	public static Context uContext = null;

	public static void init(Context context) {uContext = context;}
	public static Context getContext() {return uContext;}

	public static boolean evaluate(float a, float b, float epsilon) {
		return (Math.abs(b-a) < epsilon);
	}
	public static boolean evaluate(float a, float b) {
		return evaluate(a, b, (float)1E-8);
	}

	public static float interpolateLinear(float lastState, float currentState, float ip) {
		return lastState + (currentState - lastState) * ip;
	}

	public static String readFileAsString(int resourceID) {
		final InputStream inputStream = uContext.getResources().openRawResource(resourceID);
		final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		String nextLine;
		final StringBuilder body = new StringBuilder();

		try {
			while ((nextLine = bufferedReader.readLine()) != null) {
				body.append(nextLine);
				body.append('\n');
			}
		} catch (IOException e) {
			return null;
		}

		return body.toString();
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

}
