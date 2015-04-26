package zan.lib.gfx.obj;

import static org.lwjgl.opengl.GL11.*;

public class SpriteObject extends TextureObject {
	
	public SpriteObject(int textureID, float width, float height, float x0, float y0, float x1, float y1, float originX, float originY) {
		super(textureID);
		float ratio = Math.abs(x1-x0)/Math.abs(y1-y0);
		float tx0 = x0/width;
		float ty0 = y0/height;
		float tx1 = x1/width;
		float ty1 = y1/height;
		final int[] indices = {0, 1, 2, 3};
		final float[] vertices = {
			-originX*ratio, -originY, tx0, ty1,
			(1-originX)*ratio, -originY, tx1, ty1,
			(1-originX)*ratio, (1-originY), tx1, ty0,
			-originX*ratio, (1-originY), tx0, ty0
		};
		createVBO(vertices);
		createIBO(indices);
		setNumCoords(2);
		setDrawMode(GL_TRIANGLE_FAN);
	}
	public SpriteObject(int textureID, float width, float height, float x0, float y0, float x1, float y1) {
		this(textureID, width, height, x0, y0, x1, y1, 0.5f, 0.5f);
	}
	public SpriteObject(int textureID, float width, float height, float originX, float originY) {
		this(textureID, width, height, 0f, 0f, width, height, originX, originY);
	}
	public SpriteObject(int textureID, float width, float height) {
		this(textureID, width, height, 0f, 0f, width, height, 0.5f, 0.5f);
	}
	
}
