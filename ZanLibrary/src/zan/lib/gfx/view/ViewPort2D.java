package zan.lib.gfx.view;

import zan.lib.gfx.shader.DefaultShader;
import zan.lib.util.math.Vec2D;

public class ViewPort2D extends ViewPort {

	private double heightInterval;
	private double depthInterval;
	private double zoomScale;
	private Vec2D origin = new Vec2D();
	private Vec2D offset = new Vec2D();

	private double screenToVirtualRatio;
	private double virtualToScreenRatio;

	public ViewPort2D(int viewPortX, int viewPortY, int viewPortW, int viewPortH) {
		super(viewPortX, viewPortY, viewPortW, viewPortH);
		setHeightInterval(2.0);
		setDepthInterval(2.0);
		setOrigin(0.5, 0.5);
		setOffset(0, 0);
	}

	@Override
	public void setViewPort(int viewPortX, int viewPortY, int viewPortW, int viewPortH) {
		super.setViewPort(viewPortX, viewPortY, viewPortW, viewPortH);
		calcRatio();
	}

	public void setHeightInterval(double heightInterval) {
		this.heightInterval = heightInterval;
		zoomScale = 2.0/heightInterval;
		calcRatio();
	}
	public void setDepthInterval(double depthInterval) {this.depthInterval = depthInterval;}
	public void setOrigin(double originX, double originY) {setOriginX(originX); setOriginY(originY);}
	public void setOriginX(double originX) {origin.setX(originX);}
	public void setOriginY(double originY) {origin.setY(originY);}
	public void setOffset(double offsetX, double offsetY) {setOffsetX(offsetX); setOffsetY(offsetY);}
	public void setOffsetX(double offsetX) {offset.setX(offsetX);}
	public void setOffsetY(double offsetY) {offset.setY(offsetY);}

	public void calcRatio() {
		screenToVirtualRatio = heightInterval/viewPortH;
		virtualToScreenRatio = viewPortH/heightInterval;
	}

	public double getHeightInterval() {return heightInterval;}
	public double getDepthInterval() {return depthInterval;}
	public double getOriginX() {return origin.getX();}
	public double getOriginY() {return origin.getY();}
	public double getOffsetX() {return offset.getX();}
	public double getOffsetY() {return offset.getY();}

	public double getScreenToVirtualRatio() {return screenToVirtualRatio;}
	public double getVirtualToScreenRatio() {return virtualToScreenRatio;}

	public double getScreenToVirtualX(double x) {
		return (x-viewPortX-origin.getX()*viewPortW)*screenToVirtualRatio+offset.getX();
	}
	public double getScreenToVirtualY(double y) {
		return ((screenHeight-y)-viewPortY-origin.getY()*viewPortH)*screenToVirtualRatio+offset.getY();
	}
	public double getVirtualToScreenX(double x) {
		return (x-offset.getX())*virtualToScreenRatio+origin.getX()*viewPortW+viewPortX;
	}
	public double getVirtualToScreenY(double y) {
		return screenHeight-((y-offset.getY())*virtualToScreenRatio+origin.getY()*viewPortH+viewPortY);
	}

	@Override
	public void projectView(DefaultShader sp) {
		sp.setOrthoProjection(-viewPortRatio, viewPortRatio, -1.0, 1.0, -depthInterval*0.5, depthInterval*0.5);
	}

	@Override
	public void adjustView(DefaultShader sp) {
		sp.pushMatrix();
		sp.loadIdentityMatrix();
		sp.translate(2.0*(origin.getX()-0.5)*viewPortRatio, 2.0*(origin.getY()-0.5), 0.0);
		sp.scale(zoomScale, zoomScale, 1.0);
		sp.translate(-offset.getX(), -offset.getY(), 0.0);
		sp.applyViewMatrix();
		sp.popMatrix();
	}

}
