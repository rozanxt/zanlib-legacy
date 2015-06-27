package zan.lib.gfx.text;

import java.util.ArrayList;
import java.util.HashMap;

import zan.lib.gfx.ShaderProgram;
import zan.lib.gfx.obj.FontObject;
import zan.lib.math.matrix.MatUtil;
import zan.lib.res.ResourceData;

public class TextManager {
	
	private static HashMap<String, FontInfo> fontInfos;
	private static HashMap<String, ArrayList<CharInfo>> charInfos;
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
		charInfos = new HashMap<String, ArrayList<CharInfo>>();
		fontObjects = new HashMap<String, FontObject>();
	}
	
	public static void renderText(ShaderProgram sp, String text, String font) {
		FontInfo fontInfo = fontInfos.get(font);
		ArrayList<CharInfo> charInfo = charInfos.get(font);
		
		FontObject fo = fontObjects.get(font);
		
		float dw = 0f;
		for (int i=0;i<text.length();i++) {
			int ch = chars.indexOf(text.charAt(i));
			if (ch < 0) continue;
			
			sp.pushMatrix();
			sp.multMatrix(MatUtil.translationMat44D(dw, 0.0, 0.0));
			sp.applyModelView();
			sp.popMatrix();
			
			fo.render(sp, ch);
			
			dw += (charInfo.get(ch).width + fontInfo.x_os) / 32f;
		}
	}
	
	public static void loadFontFile(ResourceData fontData) {
		if (fontData.isEmpty()) {
			System.err.println("Error loading font data:\n no data found");
			return;
		}
		
		FontInfo fontInfo = new FontInfo();
		ArrayList<CharInfo> charInfo = new ArrayList<CharInfo>();
		
		fontInfo.name = fontData.getName();
		for (int i=0;i<fontData.getNumNodes();i++) {
			ResourceData node = fontData.getNode(i);
			if (node.getName().contentEquals("bitmap") && node.getNumValues() == 5) {
				fontInfo.file = node.getValue("file");
				fontInfo.x_tiles = node.getIntegerValue("x");
				fontInfo.y_tiles = node.getIntegerValue("y");
				fontInfo.x_res = node.getIntegerValue("w");
				fontInfo.y_res = node.getIntegerValue("h");
			} else if (node.getName().contentEquals("offset") && node.getNumValues() == 2) {
				fontInfo.x_os = node.getIntegerValue("x");
				//fontInfo.y_os = node.getIntegerValue("y");
			} else if (node.getName().contentEquals("default") && node.getNumValues() == 2) {
				fontInfo.def_w = node.getIntegerValue("w");
				fontInfo.def_h = node.getIntegerValue("h");
			} else if (node.getName().contentEquals("char")) {
				int xi = node.getIntegerValue("x");
				int yi = node.getIntegerValue("y");
				int wi = node.getIntegerValue("w");
				charInfo.add(new CharInfo(xi, yi, wi));
			}
		}
		
		fontInfos.put(fontInfo.name, fontInfo);
		
		ArrayList<CharInfo> gridInfo = new ArrayList<CharInfo>();
		for (int i=0;i<16;i++) {
			for (int j=0;j<16;j++) {
				int charWidth = fontInfo.def_w;
				for (int k=0;k<charInfo.size();k++) {
					if (i == charInfo.get(k).yid && j == charInfo.get(k).xid) {
						charWidth = charInfo.get(k).width;
					}
				}
				gridInfo.add(new CharInfo(j, i, charWidth));
			}
		}
		charInfos.put(fontInfo.name, gridInfo);
		
		fontObjects.put(fontInfo.name, new FontObject(fontInfo, gridInfo));
	}
	
}
