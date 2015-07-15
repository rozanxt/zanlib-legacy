package zan.lib.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Utility {
	
	public static double interpolateLinear(double lastState, double currentState, double ip) {
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
