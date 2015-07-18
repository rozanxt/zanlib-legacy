package zan.lib.gfx.text;

import java.util.ArrayList;
import java.util.HashMap;

import zan.lib.gfx.ShaderProgram;
import zan.lib.gfx.obj.FontObject;
import zan.lib.res.ResourceData;

public class TextManager {
	
	private static HashMap<String, FontInfo> fontInfos;
	private static HashMap<String, FontObject> fontObjects;
	
	private static final String chars =
			" !\"#$%&\'()*+,-./" +
			"0123456789:;<=>?" +
			"@ABCDEFGHIJKLMNO" +
			"PQRSTUVWXYZ[\\]^_" +
			"`abcdefghijklmno" +
			"pqrstuvwxyz{|}~";
	
	public static void init() {
		fontInfos = new HashMap<String, FontInfo>();
		fontObjects = new HashMap<String, FontObject>();
	}
	
	public static void destroy() {
		fontInfos.clear();
		fontObjects.clear();
	}
	
	public static void renderText(ShaderProgram sp, String text, String font) {
		FontInfo fontInfo = fontInfos.get(font);
		
		FontObject fo = fontObjects.get(font);
		
		double dw = 0.0;
		for (int i=0;i<text.length();i++) {
			int ch = chars.indexOf(text.charAt(i));
			if (ch < 0) continue;
			
			sp.pushMatrix();
			sp.translate(dw, 0.0, 0.0);
			sp.applyModelView();
			sp.popMatrix();
			
			fo.render(sp, ch);
			
			dw += (fontInfo.getCharInfo(ch).width + fontInfo.x_os) / 32.0;
		}
	}
	
	public static void loadFontFile(ResourceData fontData) {
		if (fontData.isEmpty()) {
			System.err.println("Error loading font data:\n no data found");
			return;
		}
		
		String name = "";
		String filename = "";
		int x_res = 0;
		int y_res = 0;
		int x_tiles = 0;
		int y_tiles = 0;
		int def_w = 0;
		int def_h = 0;
		int x_os = 0;
		
		ArrayList<CharInfo> charInfo = new ArrayList<CharInfo>();
		
		name = fontData.getName();
		for (int i=0;i<fontData.getNumNodes();i++) {
			ResourceData node = fontData.getNode(i);
			if (node.getName().contentEquals("bitmap") && node.getNumValues() == 5) {
				filename = node.getValue("file");
				x_tiles = node.getIntegerValue("x");
				y_tiles = node.getIntegerValue("y");
				x_res = node.getIntegerValue("w");
				y_res = node.getIntegerValue("h");
			} else if (node.getName().contentEquals("offset") && node.getNumValues() == 2) {
				x_os = node.getIntegerValue("x");
				//y_os = node.getIntegerValue("y");
			} else if (node.getName().contentEquals("default") && node.getNumValues() == 2) {
				def_w = node.getIntegerValue("w");
				def_h = node.getIntegerValue("h");
			} else if (node.getName().contentEquals("char")) {
				int xi = node.getIntegerValue("x");
				int yi = node.getIntegerValue("y");
				int wi = node.getIntegerValue("w");
				charInfo.add(new CharInfo(xi, yi, wi));
			}
		}
		
		ArrayList<CharInfo> gridInfo = new ArrayList<CharInfo>();
		for (int i=0;i<16;i++) {
			for (int j=0;j<16;j++) {
				int charWidth = def_w;
				for (int k=0;k<charInfo.size();k++) {
					if (i == charInfo.get(k).yid && j == charInfo.get(k).xid) {
						charWidth = charInfo.get(k).width;
					}
				}
				gridInfo.add(new CharInfo(j, i, charWidth));
			}
		}
		
		FontInfo fontInfo = new FontInfo(name, filename, x_res, y_res, x_tiles, y_tiles, def_w, def_h, x_os, gridInfo);
		fontInfos.put(name, fontInfo);
		fontObjects.put(fontInfo.name, new FontObject(fontInfo, gridInfo));
	}
	
}
