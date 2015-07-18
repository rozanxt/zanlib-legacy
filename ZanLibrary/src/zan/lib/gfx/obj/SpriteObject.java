package zan.lib.gfx.obj;

import static org.lwjgl.opengl.GL11.*;
import zan.lib.gfx.texture.TextureInfo;

public class SpriteObject extends TextureObject {
	
	public SpriteObject(TextureInfo texture, float x0, float y0, float x1, float y1, float originX, float originY) {
		super(texture);
		float ratio = Math.abs(x1-x0)/Math.abs(y1-y0);
		float tx0 = x0/texture.width;
		float ty0 = y0/texture.height;
		float tx1 = x1/texture.width;
		float ty1 = y1/texture.height;
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
	public SpriteObject(TextureInfo texture, float x0, float y0, float x1, float y1) {
		this(texture, x0, y0, x1, y1, 0.5f, 0.5f);
	}
	public SpriteObject(TextureInfo texture, float originX, float originY) {
		this(texture, 0f, 0f, texture.width, texture.height, originX, originY);
	}
	public SpriteObject(TextureInfo texture) {
		this(texture, 0f, 0f, texture.width, texture.height, 0.5f, 0.5f);
	}
	
}
