package zan.lib.gfx.sprite;

import zan.lib.gfx.ShaderProgram;
import zan.lib.gfx.obj.VertexState;
import zan.lib.math.matrix.MatUtil;

public abstract class BaseSprite {
	
	protected VertexState state;
	
	protected float posX, posY;
	protected float scaleX, scaleY;
	protected float angle;
	protected float opacity;
	
	public BaseSprite() {
		state = new VertexState();
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
	
	public void update() {
		state.amendState();
	}
	
	public void render(ShaderProgram sp, double ip) {
		state.setState(sp.getStackMatrix());
		state.multMatrix(MatUtil.translationMat44D(posX, posY, 0.0));
		state.multMatrix(MatUtil.rotationMat44D(angle, 0.0, 0.0, 1.0));
		state.multMatrix(MatUtil.scaleMat44D(scaleX, scaleY, 1.0));
		sp.setColor(1.0, 1.0, 1.0, opacity);
		sp.setModelView(state.getState(ip));
		draw(sp);
	}
	
	public abstract void draw(ShaderProgram sp);
	
}
