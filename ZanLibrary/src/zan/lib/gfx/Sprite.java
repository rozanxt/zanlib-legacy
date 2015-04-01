package zan.lib.gfx;

public class Sprite extends BaseSprite {
	
	private RawSprite sprite;
	
	public Sprite(int textureID, float width, float height) {
		super();
		sprite = new RawSprite(textureID, width, height);
	}
	
	public Sprite(int textureID, float width, float height, float x0, float y0, float x1, float y1) {
		super();
		sprite = new RawSprite(textureID, width, height, x0, y0, x1, y1);
	}
	
	public void draw() {sprite.draw();}
	
}
