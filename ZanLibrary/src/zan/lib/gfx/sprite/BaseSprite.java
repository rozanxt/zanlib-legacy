package zan.lib.gfx.sprite;

import zan.lib.gfx.shader.DefaultShader;
import zan.lib.math.linalg.LinAlgUtil;
import zan.lib.math.linalg.Vec2D;
import zan.lib.math.linalg.Vec4D;
import zan.lib.util.Utility;

public abstract class BaseSprite {

	protected Vec4D pos = LinAlgUtil.zeroVec4D;
	protected Vec4D scale = LinAlgUtil.zeroVec4D;
	protected Vec2D angle = LinAlgUtil.zeroVec2D;
	protected Vec4D color = LinAlgUtil.zeroVec4D;
	protected Vec4D oldColor = LinAlgUtil.zeroVec4D;

	protected boolean enableInterpolation = false;
	protected boolean enableTransformation = true;
	protected boolean enableColor = true;

	public void loadIdentity() {
		pos = new Vec4D(0.0, 0.0, pos.z, pos.w);
		scale = new Vec4D(1.0, 1.0, scale.z, scale.w);
		angle = new Vec2D(0.0, angle.y);
		color = new Vec4D(1.0, 1.0, 1.0, 1.0);
	}
	public void amendState() {
		pos = new Vec4D(pos.x, pos.y, pos.x, pos.y);
		scale = new Vec4D(scale.x, scale.y, scale.x, scale.y);
		angle = new Vec2D(angle.x, angle.x);
		oldColor = new Vec4D(color.x, color.y, color.z, color.w);
	}

	public void enableInterpolation(boolean interpolation) {enableInterpolation = interpolation;}
	public void enableTransformation(boolean transformation) {enableTransformation = transformation;}
	public void enableColor(boolean color) {enableColor = color;}

	public void setPos(double posX, double posY) {pos = new Vec4D(posX, posY, pos.z, pos.w);}
	public void setPosX(double posX) {pos = new Vec4D(posX, pos.y, pos.z, pos.w);}
	public void setPosY(double posY) {pos = new Vec4D(pos.x, posY, pos.z, pos.w);}
	public void setScale(double scaleX, double scaleY) {scale = new Vec4D(scaleX, scaleY, scale.z, scale.w);}
	public void setScaleX(double scaleX) {scale = new Vec4D(scaleX, scale.y, scale.z, scale.w);}
	public void setScaleY(double scaleY) {scale = new Vec4D(scale.x, scaleY, scale.z, scale.w);}
	public void setAngle(double angle) {this.angle = new Vec2D(angle, this.angle.y);}
	public void setColor(double r, double g, double b, double a) {color = new Vec4D(r, g, b, a);}
	public void setColor(double r, double g, double b) {color = new Vec4D(r, g, b, color.w);}
	public void setOpacity(double a) {color = new Vec4D(color.x, color.y, color.z, a);}

	public void setFlip(int flip) {
		if (flip == 1) setScale(-Math.abs(scale.x), Math.abs(scale.y));
		else if (flip == 2) setScale(Math.abs(scale.x), -Math.abs(scale.y));
		else if (flip == 3) setScale(-Math.abs(scale.x), -Math.abs(scale.y));
		else setScale(Math.abs(scale.x), Math.abs(scale.y));
	}

	public void update() {
		amendState();
	}

	public void render(DefaultShader sp, double ip) {
		double iP = 1.0;
		if (enableInterpolation) iP = ip;

		if (enableColor) {
			double iRed = Utility.interpolateLinear(color.get(4), color.get(0), iP);
			double iGreen = Utility.interpolateLinear(color.get(5), color.get(1), iP);
			double iBlue = Utility.interpolateLinear(color.get(6), color.get(2), iP);
			double iOpacity = Utility.interpolateLinear(color.get(7), color.get(3), iP);

			sp.setColor(iRed, iGreen, iBlue, iOpacity);
		}

		if (enableTransformation) {
			double iPosX = Utility.interpolateLinear(pos.z, pos.x, iP);
			double iPosY = Utility.interpolateLinear(pos.w, pos.y, iP);
			double iScaleX = Utility.interpolateLinear(scale.z, scale.x, iP);
			double iScaleY = Utility.interpolateLinear(scale.w, scale.y, iP);
			double iAngle = Utility.interpolateLinear(angle.get(1), angle.get(0), iP);

			sp.pushMatrix();
			sp.translate(iPosX, iPosY, 0.0);
			sp.rotate(iAngle, 0.0, 0.0, 1.0);
			sp.scale(iScaleX, iScaleY, 1.0);
			sp.applyModelMatrix();
			draw(sp);
			sp.popMatrix();
		} else {
			draw(sp);
		}
	}

	public abstract void destroy();
	public abstract void draw(DefaultShader sp);

}
