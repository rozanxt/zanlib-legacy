package zan.lib.gfx.text;

import java.util.ArrayList;
import java.util.HashMap;

import zan.lib.gfx.ShaderProgram;
import zan.lib.gfx.TextureManager;
import zan.lib.gfx.obj.TextObject;
import zan.lib.math.matrix.MatUtil;
import zan.lib.res.ResourceData;

public class TextManager {
	
	private static HashMap<String, Integer> fontTextures;
	private static HashMap<String, FontInfo> fontInfos;
	private static HashMap<String, ArrayList<CharInfo>> charInfos;
	
	private static final String chars =
			" !\"#$%&\'()*+,-./" +
			"0123456789:;<=>?" +
			"@ABCDEFGHIJKLMNO" +
			"PQRSTUVWXYZ[\\]^_" +
			"`abcdefghijklmno" +
			"pqrstuvwxyz{|}~";
	
	public static void init() {
		fontTextures = new HashMap<String, Integer>();
		fontInfos = new HashMap<String, FontInfo>();
		charInfos = new HashMap<String, ArrayList<CharInfo>>();
	}
	
	public static void renderText(ShaderProgram sp, String text, String font, float sx, float sy, float size) {
		int fontTexture = fontTextures.get(font);
		FontInfo fontInfo = fontInfos.get(font);
		ArrayList<CharInfo> charInfo = charInfos.get(font);
		
		TextObject to = new TextObject(fontTexture, fontInfo.x_res, fontInfo.y_res);
		
		float dw = 0f;
		for (int i=0;i<text.length();i++) {
			int ch = chars.indexOf(text.charAt(i));
			if (ch < 0) continue;
			
			int fi = (int)(ch / 16);
			int fj = (int)(ch % 16);
			
			float tile = (1f/16f);
			
			to.setTexCoord(fj * tile, fi * tile, (fj + 1) * tile, (fi + 1) * tile);
			
			sp.pushMatrix();
			sp.multMatrix(MatUtil.translationMat44D(sx+dw, sy, 0.0));
			sp.multMatrix(MatUtil.rotationMat44D(0.0, 0.0, 0.0, 1.0));
			sp.multMatrix(MatUtil.scaleMat44D(size, size, 1.0));
			sp.applyModelView();
			sp.popMatrix();
			
			sp.setColor(1.0, 1.0, 0.0, 1.0);
			to.render(sp);
			
			System.out.println(charInfo.get(ch).width);
			
			dw += size * (charInfo.get(ch).width + fontInfo.x_os) / 32f;
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
		
		fontTextures.put(fontInfo.name, TextureManager.loadTexture(fontInfo.name, fontInfo.file));
		fontInfos.put(fontInfo.name, fontInfo);
		
		ArrayList<CharInfo> gridInfo = new ArrayList<CharInfo>();
		for (int i=0;i<6;i++) {
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
	}
	
}
