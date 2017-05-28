package zan.lib.droid.gfx.view;

import zan.lib.droid.gfx.shader.DefaultShader;
import zan.lib.droid.util.math.Vec2F;

public class ViewPort2D extends ViewPort {

	private float heightInterval;
	private float depthInterval;
	private float zoomScale;
	private Vec2F origin = new Vec2F();
	private Vec2F offset = new Vec2F();

	private float screenToVirtualRatio;
	private float virtualToScreenRatio;

	public ViewPort2D(int viewPortX, int viewPortY, int viewPortW, int viewPortH) {
		super(viewPortX, viewPortY, viewPortW, viewPortH);
		setHeightInterval(2f);
		setDepthInterval(2f);
		setOrigin(0.5f, 0.5f);
		setOffset(0f, 0f);
	}

	@Override
	public void setViewPort(int viewPortX, int viewPortY, int viewPortW, int viewPortH) {
		super.setViewPort(viewPortX, viewPortY, viewPortW, viewPortH);
		calcRatio();
	}

	public void setHeightInterval(float heightInterval) {
		this.heightInterval = heightInterval;
		zoomScale = 2f/heightInterval;
		calcRatio();
	}
	public void setDepthInterval(float depthInterval) {this.depthInterval = depthInterval;}
	public void setOrigin(float originX, float originY) {setOriginX(originX); setOriginY(originY);}
	public void setOriginX(float originX) {origin.setX(originX);}
	public void setOriginY(float originY) {origin.setY(originY);}
	public void setOffset(float offsetX, float offsetY) {setOffsetX(offsetX); setOffsetY(offsetY);}
	public void setOffsetX(float offsetX) {offset.setX(offsetX);}
	public void setOffsetY(float offsetY) {offset.setY(offsetY);}

	public void calcRatio() {
		screenToVirtualRatio = heightInterval/viewPortH;
		virtualToScreenRatio = viewPortH/heightInterval;
	}

	public float getHeightInterval() {return heightInterval;}
	public float getDepthInterval() {return depthInterval;}
	public float getOriginX() {return origin.getX();}
	public float getOriginY() {return origin.getY();}
	public float getOffsetX() {return offset.getX();}
	public float getOffsetY() {return offset.getY();}

	public float getScreenToVirtualRatio() {return screenToVirtualRatio;}
	public float getVirtualToScreenRatio() {return virtualToScreenRatio;}

	public float getScreenToVirtualX(float x) {
		return (x-viewPortX-origin.getX()*viewPortW)*screenToVirtualRatio+offset.getX();
	}
	public float getScreenToVirtualY(float y) {
		return ((screenHeight-y)-viewPortY-origin.getY()*viewPortH)*screenToVirtualRatio+offset.getY();
	}
	public float getVirtualToScreenX(float x) {
		return (x-offset.getX())*virtualToScreenRatio+origin.getX()*viewPortW+viewPortX;
	}
	public float getVirtualToScreenY(float y) {
		return screenHeight-((y-offset.getY())*virtualToScreenRatio+origin.getY()*viewPortH+viewPortY);
	}

	@Override
	public void projectView(DefaultShader sp) {
		sp.setOrthoProjection(-viewPortRatio, viewPortRatio, -1f, 1f, -depthInterval*0.5f, depthInterval*0.5f);
	}

	@Override
	public void adjustView(DefaultShader sp) {
		sp.pushMatrix();
		sp.loadIdentityMatrix();
		sp.translate(2f*(origin.getX()-0.5f)*viewPortRatio, 2f*(origin.getY()-0.5f), 0f);
		sp.scale(zoomScale, zoomScale, 1f);
		sp.translate(-offset.getX(), -offset.getY(), 0f);
		sp.applyViewMatrix();
		sp.popMatrix();
	}

}
