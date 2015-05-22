package zan.lib.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utility {
	
	public static float interpolateLinear(float lastState, float currentState, float ip) {
		return lastState + (currentState - lastState) * ip;
	}
	
	public static String readFileAsString(String path) {
		StringBuilder content = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			while((line = br.readLine()) != null) {
				content.append(line).append('\n');
			}
			br.close();
		} catch (IOException e) {
			System.err.println("Error reading file: " + path + ":\n" + e);
		}
		return content.toString();
	}
	
}
