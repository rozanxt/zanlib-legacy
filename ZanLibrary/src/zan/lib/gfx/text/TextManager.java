package zan.lib.gfx.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static zan.lib.gfx.obj.SpriteObject.ALIGN_HORIZONTAL;
import zan.lib.gfx.obj.SpriteObject;
import zan.lib.gfx.shader.DefaultShader;
import zan.lib.gfx.texture.TextureManager;
import zan.lib.util.Utility;
import zan.lib.util.res.ResourceData;
import zan.lib.util.res.ResourceUtil;

public class TextManager {

	private static HashMap<String, FontInfo> fontInfos;
	private static HashMap<String, SpriteObject> fontObjects;

	private static boolean initialized = false;

	private static final String chars = ""
			+ " !\"#$%&\'()*+,-./"
			+ "0123456789:;<=>?"
			+ "@ABCDEFGHIJKLMNO"
			+ "PQRSTUVWXYZ[\\]^_"
			+ "`abcdefghijklmno"
			+ "pqrstuvwxyz{|}~";

	public static void init() {
		if (!initialized) {
			fontInfos = new HashMap<String, FontInfo>();
			fontObjects = new HashMap<String, SpriteObject>();
			initialized = true;
		}
	}

	public static void clear() {
		if (initialized) {
			for (Map.Entry<String, SpriteObject> entry : fontObjects.entrySet()) entry.getValue().destroy();
			fontInfos.clear();
			fontObjects.clear();
		}
	}

	public static void renderText(DefaultShader sp, String text, String font) {
		if (!initialized) {
			System.err.println("Error rendering text: TextManager is not initialized!");
			return;
		}

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
			sp.pushMatrix();
			sp.translate(dw, 0.0, 0.0);
			sp.applyModelMatrix();
			fo.renderFrame(sp, ch);
			sp.popMatrix();
			dw += (fontInfo.getCharInfo(ch).width + fontInfo.offset_x) / 32.0;
		}
	}

	public static void loadFont(ResourceData fontData) {
		if (!initialized) {
			System.err.println("Error loading font '" + fontData.getValue("name") + "': TextManager is not initialized!");
			return;
		}

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
		if (!initialized) {
			System.err.println("Error loading font data '" + filename + "': TextManager is not initialized!");
			return;
		}

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
