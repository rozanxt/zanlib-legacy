package zan.lib.gfx;

import static org.lwjgl.opengl.GL11.*;

public class Sprite {
	
	private int textureID;
	private float textureX0, textureY0;
	private float textureX1, textureY1;
	private float originX, originY;
	private float ratio;
	
	private float posX, posY;
	private float size;
	private float angle;
	private float opacity;
	
	private float oldPosX, oldPosY;
	private float oldSize;
	private float oldAngle;
	private float oldOpacity;
	
	public Sprite(int textureID, float width, float height) {
		this.textureID = textureID;
		textureX0 = 0f; textureY0 = 0f;
		textureX1 = 1f; textureY1 = 1f;
		ratio = width/height;
		loadIdentity();
	}
	
	public Sprite(int textureID, float width, float height, float x0, float y0, float x1, float y1) {
		this.textureID = textureID;
		textureX0 = x0/width; textureY0 = y0/height;
		textureX1 = x1/width; textureY1 = y1/height;
		ratio = Math.abs(x1-x0)/Math.abs(y1-y0);
		loadIdentity();
	}
	
	public void loadIdentity() {
		setOrigin(0.5f, 0.5f);
		setPos(0f, 0f);
		setSize(1f);
		setAngle(0f);
		setOpacity(1f);
		amendState();
	}
	
	public void amendState() {
		oldPosX = posX;
		oldPosY = posY;
		oldSize = size;
		oldAngle = angle;
		oldOpacity = opacity;
	}
	
	public void setOrigin(float originX, float originY) {this.originX = originX; this.originY = originY;}
	public void setPos(float posX, float posY) {setX(posX);	setY(posY);}
	public void setX(float posX) {oldPosX = this.posX; this.posX = posX;}
	public void setY(float posY) {oldPosY = this.posY; this.posY = posY;}
	public void setSize(float size) {oldSize = this.size; this.size = size;}
	public void setAngle(float rotation) {oldAngle = this.angle; this.angle = rotation;}
	public void setOpacity(float opacity) {oldOpacity = this.opacity; this.opacity = opacity;}
	
	public void render(float ip) {
		float ipX = oldPosX+(posX-oldPosX)*ip;
		float ipY = oldPosY+(posY-oldPosY)*ip;
		float ipSize = oldSize+(size-oldSize)*ip;
		float ipAngle = oldAngle+(angle-oldAngle)*ip;
		float ipOpacity = oldOpacity+(opacity-oldOpacity)*ip;
		
		glColor4f(1f, 1f, 1f, ipOpacity);
		glPushMatrix();
		glTranslatef(ipX, ipY, 0f);
		glRotatef(ipAngle, 0f, 0f, 1f);
		glScalef(ipSize, ipSize, 0f);
		renderRaw();
		glPopMatrix();
	}
	
	public void renderRaw() {
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
