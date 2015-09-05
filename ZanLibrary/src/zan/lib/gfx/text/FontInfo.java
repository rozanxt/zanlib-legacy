package zan.lib.gfx.text;

import java.util.ArrayList;

public class FontInfo {

	public final String name, filename;
	public final int x_res, y_res;
	public final int x_tiles, y_tiles;
	public final int def_w, def_h;
	public final int x_os;

	private ArrayList<CharInfo> charInfo;

	public FontInfo(String name, String filename, int x_res, int y_res, int x_tiles, int y_tiles, int def_w, int def_h, int x_os, ArrayList<CharInfo> charInfo) {
		this.name = name;
		this.filename = filename;
		this.x_res = x_res;
		this.y_res = y_res;
		this.x_tiles = x_tiles;
		this.y_tiles = y_tiles;
		this.def_w = def_w;
		this.def_h = def_h;
		this.x_os = x_os;
		this.charInfo = charInfo;
	}

	public CharInfo getCharInfo(int index) {return charInfo.get(index);}

}
