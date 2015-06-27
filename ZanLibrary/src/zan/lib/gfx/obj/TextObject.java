package zan.lib.gfx.obj;

public class TextObject extends SpriteObject {
	
	protected float mapWidth, mapHeight;
	
	public TextObject(int textureID, float width, float height) {
		super(textureID, width, height, 0f, 0f);
		mapWidth = width;
		mapHeight = height;
	}
	
	public void setTexCoord(float x0, float y0, float x1, float y1) {
		float ratio = 1f;
		float tx0 = x0;
		float ty0 = y0;
		float tx1 = x1;
		float ty1 = y1;
		final float[] vertices = {
			0f, 0f, tx0, ty1,
			ratio, 0f, tx1, ty1,
			ratio, 1f, tx1, ty0,
			0f, 1f, tx0, ty0
		};
		createVBO(vertices);
	}
	
}
