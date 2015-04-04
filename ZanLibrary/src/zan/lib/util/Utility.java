package zan.lib.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utility {
	private static final String LOGNAME = "Utility :: ";
	
	private Utility() {}
	
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
			System.err.println(LOGNAME + "Error reading file: " + path + ":\n" + e);
		}
		return content.toString();
	}
	
}
