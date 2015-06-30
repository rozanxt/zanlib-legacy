package zan.lib.gfx;

public class TextureData {
	
	private int textureID;
	private int textureWidth, textureHeight;
	
	public TextureData(int id, int width, int height) {
		textureID = id;
		textureWidth = width;
		textureHeight = height;
	}
	
	public int getTextureID() {return textureID;}
	public int getTextureWidth() {return textureWidth;}
	public int getTextureHeight() {return textureHeight;}
	public float getTextureRatio() {return (float)textureWidth/(float)textureHeight;}
	
}
