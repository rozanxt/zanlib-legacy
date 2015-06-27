package zan.lib.gfx.text;

public class FontInfo {
	
	public String name, file;
	public int x_res, y_res;
	public int x_tiles, y_tiles;
	public int def_w, def_h;
	public int x_os;
	
	public FontInfo() {
		name = "";
		file = "";
		x_res = 128;
		y_res = 128;
		x_tiles = 16;
		y_tiles = 16;
		def_w = 8;
		def_h = 8;
		x_os = 1;
	}
	
}
