package zan.lib.gfx.view;

import zan.lib.gfx.shader.DefaultShader;
import zan.lib.util.math.MathUtil;
import zan.lib.util.math.Vec3D;

public class ViewPort3D extends ViewPort {

	private double fovy;
	private double nearClip, farClip;
	private Vec3D offset;

	public ViewPort3D(int viewPortX, int viewPortY, int viewPortW, int viewPortH) {
		super(viewPortX, viewPortY, viewPortW, viewPortH);
		offset = MathUtil.zeroVec3D;
		setFOVY(120.0);
		setDepthInterval(0.1, 100.0);
		setOffset(0.0, 0.0, 0.0);
	}

	public void setFOVY(double fovy) {this.fovy = fovy;}
	public void setDepthInterval(double depthInterval) {setNearClip(0.1); setFarClip(0.1+depthInterval);}
	public void setDepthInterval(double nearClip, double farClip) {setNearClip(nearClip); setFarClip(farClip);}
	public void setNearClip(double nearClip) {this.nearClip = nearClip;}
	public void setFarClip(double farClip) {this.farClip = farClip;}
	public void setOffset(double offsetX, double offsetY, double offsetZ) {offset = new Vec3D(offsetX, offsetY, offsetZ);}

	public double getFOVY() {return fovy;}
	public double getDepthInterval() {return farClip-nearClip;}
	public double getNearClip() {return nearClip;}
	public double getFarClip() {return farClip;}
	public double getOffsetX() {return offset.x;}
	public double getOffsetY() {return offset.y;}
	public double getOffsetZ() {return offset.z;}

	@Override
	public void projectView(DefaultShader sp) {
		sp.setPerspectiveProjection(fovy, viewPortRatio, nearClip, farClip);
	}

	@Override
	public void adjustView(DefaultShader sp) {
		sp.setViewMatrix(MathUtil.translationMat44D(-offset.x, -offset.y, -offset.z));
	}

}
