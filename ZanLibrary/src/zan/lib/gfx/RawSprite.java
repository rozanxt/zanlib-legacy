package zan.lib.gfx;

import static org.lwjgl.opengl.GL11.*;

public class RawSprite {
	
	private int textureID;
	private float textureX0, textureY0;
	private float textureX1, textureY1;
	private float originX, originY;
	private float ratio;
	
	public RawSprite(int textureID, float width, float height) {
		this.textureID = textureID;
		textureX0 = 0f; textureY0 = 0f;
		textureX1 = 1f; textureY1 = 1f;
		ratio = width/height;
		setOrigin(0.5f, 0.5f);
	}
	
	public RawSprite(int textureID, float width, float height, float x0, float y0, float x1, float y1) {
		this.textureID = textureID;
		textureX0 = x0/width; textureY0 = y0/height;
		textureX1 = x1/width; textureY1 = y1/height;
		ratio = Math.abs(x1-x0)/Math.abs(y1-y0);
		setOrigin(0.5f, 0.5f);
	}
	
	public void setOrigin(float originX, float originY) {this.originX = originX; this.originY = originY;}
	
	public void draw() {
		float left = - originX * ratio;
		float right = (1f - originX) * ratio;
		float down = - originY;
		float up = 1f - originY;
		
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, textureID);
		glBegin(GL_QUADS);
			glTexCoord2f(textureX0, textureY1);
			glVertex2f(left, down);
			glTexCoord2f(textureX1, textureY1);
			glVertex2f(right, down);
			glTexCoord2f(textureX1, textureY0);
			glVertex2f(right, up);
			glTexCoord2f(textureX0, textureY0);
			glVertex2f(left, up);
		glEnd();
		glDisable(GL_TEXTURE_2D);
	}
	
}
