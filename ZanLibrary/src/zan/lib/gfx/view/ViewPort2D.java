package zan.lib.gfx.view;

import zan.lib.gfx.shader.DefaultShader;
import zan.lib.util.math.MathUtil;
import zan.lib.util.math.Vec2D;

public class ViewPort2D extends ViewPort {

	private double heightInterval;
	private double depthInterval;
	private double zoomScale;
	private Vec2D origin = MathUtil.zeroVec2D;
	private Vec2D offset = MathUtil.zeroVec2D;

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
	public void setOrigin(double originX, double originY) {origin = new Vec2D(originX, originY);}
	public void setOffset(double offsetX, double offsetY) {offset = new Vec2D(offsetX, offsetY);}

	public void calcRatio() {
		screenToVirtualRatio = heightInterval/viewPortH;
		virtualToScreenRatio = viewPortH/heightInterval;
	}

	public double getHeightInterval() {return heightInterval;}
	public double getDepthInterval() {return depthInterval;}
	public double getOriginX() {return origin.x;}
	public double getOriginY() {return origin.y;}
	public double getOffsetX() {return offset.x;}
	public double getOffsetY() {return offset.y;}

	public double getScreenToVirtualRatio() {return screenToVirtualRatio;}
	public double getVirtualToScreenRatio() {return virtualToScreenRatio;}

	public double getScreenToVirtualX(double x) {
		return (x-viewPortX-origin.x*viewPortW)*screenToVirtualRatio+offset.x;
	}
	public double getScreenToVirtualY(double y) {
		return ((screenHeight-y)-viewPortY-origin.y*viewPortH)*screenToVirtualRatio+offset.y;
	}
	public double getVirtualToScreenX(double x) {
		return (x-offset.x)*virtualToScreenRatio+origin.x*viewPortW+viewPortX;
	}
	public double getVirtualToScreenY(double y) {
		return screenHeight-((y-offset.y)*virtualToScreenRatio+origin.y*viewPortH+viewPortY);
	}

	@Override
	public void projectView(DefaultShader sp) {
		sp.setOrthoProjection(-viewPortRatio, viewPortRatio, -1.0, 1.0, -depthInterval*0.5, depthInterval*0.5);
	}

	@Override
	public void adjustView(DefaultShader sp) {
		sp.pushMatrix();
		sp.loadIdentityMatrix();
		sp.translate(2.0*(origin.x-0.5)*viewPortRatio, 2.0*(origin.y-0.5), 0.0);
		sp.scale(zoomScale, zoomScale, 1.0);
		sp.translate(-offset.x, -offset.y, 0.0);
		sp.applyViewMatrix();
		sp.popMatrix();
	}

}
