package zan.lib.gfx.sprite;

import zan.lib.gfx.ShaderProgram;
import zan.lib.util.Utility;

public abstract class BaseSprite {
	
	protected double posX, posY;
	protected double scaleX, scaleY;
	protected double angle;
	protected double opacity;
	
	protected double oldPosX, oldPosY;
	protected double oldScaleX, oldScaleY;
	protected double oldAngle;
	protected double oldOpacity;
	
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
	
	public void setPos(double posX, double posY) {setX(posX);	setY(posY);}
	public void setX(double posX) {this.posX = posX;}
	public void setY(double posY) {this.posY = posY;}
	public void setScale(double scale) {setScaleX(scale); setScaleY(scale);}
	public void setScaleX(double scaleX) {this.scaleX = scaleX;}
	public void setScaleY(double scaleY) {this.scaleY = scaleY;}
	public void setAngle(double angle) {this.angle = angle;}
	public void setOpacity(double opacity) {this.opacity = opacity;}
	
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
		double iPosX = Utility.interpolateLinear(oldPosX, posX, ip);
		double iPosY = Utility.interpolateLinear(oldPosY, posY, ip);
		double iScaleX = Utility.interpolateLinear(oldScaleX, scaleX, ip);
		double iScaleY = Utility.interpolateLinear(oldScaleY, scaleY, ip);
		double iAngle = Utility.interpolateLinear(oldAngle, angle, ip);
		double iOpacity = Utility.interpolateLinear(oldOpacity, opacity, ip);
		
		sp.pushMatrix();
		sp.translate(iPosX, iPosY, 0.0);
		sp.rotate(iAngle, 0.0, 0.0, 1.0);
		sp.scale(iScaleX, iScaleY, 1.0);
		sp.applyModelView();
		sp.popMatrix();
		
		sp.setColor(1.0, 1.0, 1.0, iOpacity);
		draw(sp);
	}
	
	public abstract void draw(ShaderProgram sp);
	
}
