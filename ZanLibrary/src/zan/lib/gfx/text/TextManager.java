package zan.lib.gfx.text;

import static zan.lib.gfx.object.SpriteObject.ALIGN_HORIZONTAL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import zan.lib.gfx.object.SpriteObject;
import zan.lib.gfx.scene.DefaultScene;
import zan.lib.gfx.texture.TextureManager;
import zan.lib.res.ResourceData;
import zan.lib.res.ResourceUtil;
import zan.lib.util.Utility;

public class TextManager {

	private static HashMap<String, FontInfo> fontInfos;
	private static HashMap<String, SpriteObject> fontObjects;

	private static final String chars = ""
			+ " !\"#$%&\'()*+,-./"
			+ "0123456789:;<=>?"
			+ "@ABCDEFGHIJKLMNO"
			+ "PQRSTUVWXYZ[\\]^_"
			+ "`abcdefghijklmno"
			+ "pqrstuvwxyz{|}~";

	public static void create() {
		fontInfos = new HashMap<String, FontInfo>();
		fontObjects = new HashMap<String, SpriteObject>();
	}

	public static void destroy() {
		for (Map.Entry<String, SpriteObject> entry : fontObjects.entrySet()) entry.getValue().destroy();
		fontInfos.clear();
		fontObjects.clear();
	}

	public static void renderText(DefaultScene sc, String text, String font) {
		FontInfo fontInfo = fontInfos.get(font);
		SpriteObject fo = fontObjects.get(font);

		if (fontInfo == null || fo == null) {
			System.err.println("Error retrieving font: No font stored under '" + font + "'!");
			return;
		}

		double dw = 0.0;
		for (int i=0;i<text.length();i++) {
			int ch = chars.indexOf(text.charAt(i));
			if (ch < 0) continue;
			sc.pushMatrix();
			sc.translate(dw, 0.0, 0.0);
			sc.applyModelMatrix();
			fo.renderFrame(sc, ch);
			sc.popMatrix();
			dw += (fontInfo.getCharInfo(ch).width + fontInfo.offset_x) / 32.0;
		}
	}

	public static void loadFont(ResourceData fontData) {
		String name = fontData.getValue("name");
		String bitmap = fontData.getValue("bitmap");
		int tiles_x = 0;
		int tiles_y = 0;
		int offset_x = 0;
		int offset_y = 0;
		int default_w = 0;
		int default_h = 0;
		ArrayList<CharInfo> charInfo = new ArrayList<CharInfo>();

		for (int i=0;i<fontData.getNumNodes();i++) {
			ResourceData node = fontData.getNode(i);
			if (node.getName().contentEquals("tiles")) {
				tiles_x = node.getIntegerValue("x");
				tiles_y = node.getIntegerValue("y");
			} else if (node.getName().contentEquals("offset")) {
				offset_x = node.getIntegerValue("x");
				offset_y = node.getIntegerValue("y");
			} else if (node.getName().contentEquals("default")) {
				default_w = node.getIntegerValue("w");
				default_h = node.getIntegerValue("h");
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
				int charWidth = default_w;
				for (int k=0;k<charInfo.size();k++) {
					if (i == charInfo.get(k).yid && j == charInfo.get(k).xid) {
						charWidth = charInfo.get(k).width;
					}
				}
				gridInfo.add(new CharInfo(j, i, charWidth));
			}
		}

		FontInfo fontInfo = new FontInfo(name, bitmap, tiles_x, tiles_y, offset_x, offset_y, default_w, default_h, gridInfo);
		fontInfos.put(name, fontInfo);
		fontObjects.put(fontInfo.name, new SpriteObject(TextureManager.loadTexture(fontInfo.name, fontInfo.bitmap), 0f, 0f, tiles_x, tiles_y, ALIGN_HORIZONTAL));
	}

	public static void loadFontFile(String filename) {
		ResourceData fontData;
		if (Utility.getSuffix(filename).contentEquals("xml")) {
			fontData = ResourceUtil.readXML(filename);
		} else {
			fontData = ResourceUtil.readResource(filename);
		}

		ArrayList<ResourceData> fontsToLoad = ResourceUtil.searchDataByName(fontData, "font");
		if (fontsToLoad.isEmpty()) {
			System.err.println("Error loading font data '" + filename + "': No data found!");
			return;
		}
		for (int i=0;i<fontsToLoad.size();i++) loadFont(fontsToLoad.get(i));
	}

}
