package zan.lib.gfx;

import static org.lwjgl.opengl.GL11.*;

public abstract class BaseSprite {
	
	private float posX, posY;
	private float scaleX, scaleY;
	private float angle;
	private float opacity;
	
	private float oldPosX, oldPosY;
	private float oldScaleX, oldScaleY;
	private float oldAngle;
	private float oldOpacity;
	
	public BaseSprite() {loadIdentity();}
	
	public void loadIdentity() {
		setPos(0f, 0f);
		setScale(1f);
		setAngle(0f);
		setOpacity(1f);
		amendState();
	}
	
	public void amendState() {
		oldPosX = posX;
		oldPosY = posY;
		oldScaleX = scaleX;
		oldScaleY = scaleY;
		oldAngle = angle;
		oldOpacity = opacity;
	}
	
	public void setPos(float posX, float posY) {setX(posX);	setY(posY);}
	public void setX(float posX) {oldPosX = this.posX; this.posX = posX;}
	public void setY(float posY) {oldPosY = this.posY; this.posY = posY;}
	public void setScale(float scale) {setScaleX(scale); setScaleY(scale);}
	public void setScaleX(float scaleX) {oldScaleX = this.scaleX; this.scaleX = scaleX;}
	public void setScaleY(float scaleY) {oldScaleY = this.scaleY; this.scaleY = scaleY;}
	public void setAngle(float angle) {oldAngle = this.angle; this.angle = angle;}
	public void setOpacity(float opacity) {oldOpacity = this.opacity; this.opacity = opacity;}
	
	public void setFlip(int flip) {
		if (flip == 1) {
			setScaleX(-Math.abs(scaleX));
			setScaleY(Math.abs(scaleY));
		} else if (flip == 2) {
			setScaleX(Math.abs(scaleX));
			setScaleY(-Math.abs(scaleY));
		} else if (flip == 3) {
			setScaleX(-Math.abs(scaleX));
			setScaleY(-Math.abs(scaleY));
		} else {
			setScaleX(Math.abs(scaleX));
			setScaleY(Math.abs(scaleY));
		}
	}
	
	public void render(float ip) {
		float ipX = oldPosX+(posX-oldPosX)*ip;
		float ipY = oldPosY+(posY-oldPosY)*ip;
		float ipScaleX = oldScaleX+(scaleX-oldScaleX)*ip;
		float ipScaleY = oldScaleY+(scaleY-oldScaleY)*ip;
		float ipAngle = oldAngle+(angle-oldAngle)*ip;
		float ipOpacity = oldOpacity+(opacity-oldOpacity)*ip;
		
		glPushMatrix();
		glTranslatef(ipX, ipY, 0f);
		glRotatef(ipAngle, 0f, 0f, 1f);
		glScalef(ipScaleX, ipScaleY, 1f);
		glColor4f(1f, 1f, 1f, ipOpacity);
		draw();
		glPopMatrix();
	}
	
	public abstract void draw();
	
}
