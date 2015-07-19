package zan.lib.gfx.sprite;

import zan.lib.gfx.ShaderProgram;
import zan.lib.util.Utility;
import zan.lib.util.math.Vec2D;
import zan.lib.util.math.Vec4D;
import zan.lib.util.math.VecD;

public abstract class BaseSprite {
	
	protected Vec4D pos;
	protected Vec4D scale;
	protected Vec2D angle;
	protected VecD color;
	
	protected boolean enableInterpolation;
	protected boolean enableTransformation;
	protected boolean enableColor;
	
	public BaseSprite() {
		pos = new Vec4D();
		scale = new Vec4D();
		angle = new Vec2D();
		color = new VecD(8);
		loadIdentity();
		amendState();
		enableInterpolation = true;
		enableTransformation = true;
		enableColor = true;
	}
	
	public void destroy() {}
	
	public void loadIdentity() {
		pos.put(0.0, 0.0);
		scale.put(1.0, 1.0);
		angle.put(0.0);
		color.put(1.0, 1.0, 1.0, 1.0);
	}
	public void amendState() {
		pos.set(2, pos.get(0, 1));
		scale.set(2, scale.get(0, 1));
		angle.set(1, angle.get(0));
		color.set(4, color.get(0, 3));
	}
	
	public void enableInterpolation(boolean interpolation) {enableInterpolation = interpolation;}
	public void enableTransformation(boolean transformation) {enableTransformation = transformation;}
	public void enableColor(boolean color) {enableColor = color;}
	
	public void setPos(double posX, double posY) {setX(posX); setY(posY);}
	public void setX(double posX) {pos.setX(posX);}
	public void setY(double posY) {pos.setY(posY);}
	public void setScale(double scale) {setScaleX(scale); setScaleY(scale);}
	public void setScaleX(double scaleX) {scale.setX(scaleX);}
	public void setScaleY(double scaleY) {scale.setY(scaleY);}
	public void setAngle(double angle) {this.angle.set(0, angle);}
	public void setColor(double r, double g, double b, double a) {color.put(r, g, b, a);}
	public void setColor(double r, double g, double b) {color.put(r, g, b);}
	public void setOpacity(double a) {color.set(3, a);}
	
	public void setFlip(int flip) {
		if (flip == 1) {
			setScaleX(-Math.abs(scale.getX()));
			setScaleY(Math.abs(scale.getY()));
		} else if (flip == 2) {
			setScaleX(Math.abs(scale.getX()));
			setScaleY(-Math.abs(scale.getY()));
		} else if (flip == 3) {
			setScaleX(-Math.abs(scale.getX()));
			setScaleY(-Math.abs(scale.getY()));
		} else {
			setScaleX(Math.abs(scale.getX()));
			setScaleY(Math.abs(scale.getY()));
		}
	}
	
	public void update() {
		amendState();
	}
	
	public void render(ShaderProgram sp, double ip) {
		double iP = ip;
		if (!enableInterpolation) iP = 1.0;
		
		if (enableColor) {
			double iRed = Utility.interpolateLinear(color.get(4), color.get(0), iP);
			double iGreen = Utility.interpolateLinear(color.get(5), color.get(1), iP);
			double iBlue = Utility.interpolateLinear(color.get(6), color.get(2), iP);
			double iOpacity = Utility.interpolateLinear(color.get(7), color.get(3), iP);
			
			sp.setColor(iRed, iGreen, iBlue, iOpacity);
		}
		
		if (enableTransformation) {
			double iPosX = Utility.interpolateLinear(pos.getZ(), pos.getX(), iP);
			double iPosY = Utility.interpolateLinear(pos.getW(), pos.getY(), iP);
			double iScaleX = Utility.interpolateLinear(scale.getZ(), scale.getX(), iP);
			double iScaleY = Utility.interpolateLinear(scale.getW(), scale.getY(), iP);
			double iAngle = Utility.interpolateLinear(angle.get(1), angle.get(0), iP);
			
			sp.pushMatrix();
			sp.translate(iPosX, iPosY, 0.0);
			sp.rotate(iAngle, 0.0, 0.0, 1.0);
			sp.scale(iScaleX, iScaleY, 1.0);
			sp.applyModelView();
			draw(sp);
			sp.popMatrix();
		} else {
			draw(sp);
		}
	}
	
	public abstract void draw(ShaderProgram sp);
	
}
