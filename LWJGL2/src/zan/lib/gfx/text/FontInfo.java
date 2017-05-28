package zan.lib.gfx.text;

import java.util.ArrayList;

public class FontInfo {

	public final String name;
	public final String bitmap;
	public final int tiles_x, tiles_y;
	public final int offset_x, offset_y;
	public final int default_w, default_h;

	private ArrayList<CharInfo> charInfo;

	public FontInfo(String name, String bitmap, int tiles_x, int tiles_y, int offset_x, int offset_y, int default_w, int default_h, ArrayList<CharInfo> charInfo) {
		this.name = name;
		this.bitmap = bitmap;
		this.tiles_x = tiles_x;
		this.tiles_y = tiles_y;
		this.offset_x = offset_x;
		this.offset_y = offset_y;
		this.default_w = default_w;
		this.default_h = default_h;
		this.charInfo = charInfo;
	}

	public CharInfo getCharInfo(int index) {return charInfo.get(index);}

}
