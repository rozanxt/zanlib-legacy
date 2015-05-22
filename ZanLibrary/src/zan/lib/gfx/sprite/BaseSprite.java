package zan.lib.gfx.sprite;

import zan.lib.gfx.ShaderProgram;
import zan.lib.math.matrix.MatUtil;
import zan.lib.util.Utility;

public abstract class BaseSprite {
	
	protected float posX, posY;
	protected float scaleX, scaleY;
	protected float angle;
	protected float opacity;
	
	protected float oldPosX, oldPosY;
	protected float oldScaleX, oldScaleY;
	protected float oldAngle;
	protected float oldOpacity;
	
	public BaseSprite() {
		loadIdentity();
	}
	
	public void destroy() {}
	
	public void loadIdentity() {
		posX = 0f; posY = 0f;
		scaleX = 1f; scaleY = 1f;
		angle = 0f;
		opacity = 1f;
	}
	
	public void setPos(float posX, float posY) {setX(posX);	setY(posY);}
	public void setX(float posX) {this.posX = posX;}
	public void setY(float posY) {this.posY = posY;}
	public void setScale(float scale) {setScaleX(scale); setScaleY(scale);}
	public void setScaleX(float scaleX) {this.scaleX = scaleX;}
	public void setScaleY(float scaleY) {this.scaleY = scaleY;}
	public void setAngle(float angle) {this.angle = angle;}
	public void setOpacity(float opacity) {this.opacity = opacity;}
	
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
	
	public void amendState() {
		oldPosX = posX; oldPosY = posY;
		oldScaleX = scaleX; oldScaleY = scaleY;
		oldAngle = angle;
		oldOpacity = opacity;
	}
	
	public void update() {
		amendState();
	}
	
	public void render(ShaderProgram sp, double ip) {
		float iPosX = Utility.interpolateLinear(oldPosX, posX, (float)ip);
		float iPosY = Utility.interpolateLinear(oldPosY, posY, (float)ip);
		float iScaleX = Utility.interpolateLinear(oldScaleX, scaleX, (float)ip);
		float iScaleY = Utility.interpolateLinear(oldScaleY, scaleY, (float)ip);
		float iAngle = Utility.interpolateLinear(oldAngle, angle, (float)ip);
		float iOpacity = Utility.interpolateLinear(oldOpacity, opacity, (float)ip);
		
		sp.pushMatrix();
		sp.multMatrix(MatUtil.translationMat44D(iPosX, iPosY, 0.0));
		sp.multMatrix(MatUtil.rotationMat44D(iAngle, 0.0, 0.0, 1.0));
		sp.multMatrix(MatUtil.scaleMat44D(iScaleX, iScaleY, 1.0));
		sp.applyModelView();
		sp.popMatrix();
		
		sp.setColor(1.0, 1.0, 1.0, iOpacity);
		draw(sp);
	}
	
	public abstract void draw(ShaderProgram sp);
	
}
