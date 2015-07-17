package zan.lib.gfx.sprite;

import zan.lib.gfx.ShaderProgram;
import zan.lib.util.Utility;

public abstract class BaseSprite {
	
	protected double posX, posY;
	protected double scaleX, scaleY;
	protected double angle;
	protected double red, green, blue, opacity;
	
	protected double oldPosX, oldPosY;
	protected double oldScaleX, oldScaleY;
	protected double oldAngle;
	protected double oldRed, oldGreen, oldBlue, oldOpacity;
	
	protected boolean enableInterpolation;
	protected boolean enableTransformation;
	protected boolean enableColor;
	
	public BaseSprite() {
		loadIdentity();
		amendState();
		enableInterpolation = true;
		enableTransformation = true;
		enableColor = true;
	}
	
	public void destroy() {}
	
	public void loadIdentity() {
		posX = 0.0; posY = 0.0;
		scaleX = 1.0; scaleY = 1.0;
		angle = 0.0;
		red = 1.0; green = 1.0; blue = 1.0;	opacity = 1.0;
	}
	public void amendState() {
		oldPosX = posX; oldPosY = posY;
		oldScaleX = scaleX; oldScaleY = scaleY;
		oldAngle = angle;
		oldRed = red; oldGreen = green; oldBlue = blue;	oldOpacity = opacity;
	}
	
	public void enableInterpolation(boolean interpolation) {enableInterpolation = interpolation;}
	public void enableTransformation(boolean transformation) {enableTransformation = transformation;}
	public void enableColor(boolean color) {enableColor = color;}
	
	public void setPos(double posX, double posY) {setX(posX); setY(posY);}
	public void setX(double posX) {this.posX = posX;}
	public void setY(double posY) {this.posY = posY;}
	public void setScale(double scale) {setScaleX(scale); setScaleY(scale);}
	public void setScaleX(double scaleX) {this.scaleX = scaleX;}
	public void setScaleY(double scaleY) {this.scaleY = scaleY;}
	public void setAngle(double angle) {this.angle = angle;}
	public void setColor(double red, double green, double blue, double opacity) {this.red = red; this.green = green; this.blue = blue; this.opacity = opacity;}
	public void setColor(double red, double green, double blue) {setColor(red, green, blue, opacity);}
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
	
	public void update() {
		amendState();
	}
	
	public void render(ShaderProgram sp, double ip) {
		double iP = ip;
		if (!enableInterpolation) iP = 1.0;
		
		if (enableTransformation) {
			double iPosX = Utility.interpolateLinear(oldPosX, posX, iP);
			double iPosY = Utility.interpolateLinear(oldPosY, posY, iP);
			double iScaleX = Utility.interpolateLinear(oldScaleX, scaleX, iP);
			double iScaleY = Utility.interpolateLinear(oldScaleY, scaleY, iP);
			double iAngle = Utility.interpolateLinear(oldAngle, angle, iP);
			
			sp.pushMatrix();
			sp.translate(iPosX, iPosY, 0.0);
			sp.rotate(iAngle, 0.0, 0.0, 1.0);
			sp.scale(iScaleX, iScaleY, 1.0);
			sp.applyModelView();
			sp.popMatrix();
		}
		
		if (enableColor) {
			double iRed = Utility.interpolateLinear(oldRed, red, iP);
			double iGreen = Utility.interpolateLinear(oldGreen, green, iP);
			double iBlue = Utility.interpolateLinear(oldBlue, blue, iP);
			double iOpacity = Utility.interpolateLinear(oldOpacity, opacity, iP);
			
			sp.setColor(iRed, iGreen, iBlue, iOpacity);
		}
		
		draw(sp);
	}
	
	public abstract void draw(ShaderProgram sp);
	
}
