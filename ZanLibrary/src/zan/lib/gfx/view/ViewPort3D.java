package zan.lib.gfx.view;

import zan.lib.gfx.ShaderProgram;
import zan.lib.util.math.Vec3D;

public class ViewPort3D extends ViewPort {
	
	private double fovy;
	private double nearClip, farClip;
	private Vec3D offset;
	
	public ViewPort3D(int viewPortX, int viewPortY, int viewPortW, int viewPortH) {
		super(viewPortX, viewPortY, viewPortW, viewPortH);
		offset = new Vec3D();
		setFOVY(120.0);
		setDepthInterval(0.1, 100.0);
		setOffset(0.0, 0.0, 0.0);
	}
	
	public void setFOVY(double fovy) {this.fovy = fovy;}
	public void setDepthInterval(double depthInterval) {setNearClip(0.1); setFarClip(0.1+depthInterval);}
	public void setDepthInterval(double nearClip, double farClip) {setNearClip(nearClip); setFarClip(farClip);}
	public void setNearClip(double nearClip) {this.nearClip = nearClip;}
	public void setFarClip(double farClip) {this.farClip = farClip;}
	public void setOffset(double offsetX, double offsetY) {setOffsetX(offsetX); setOffsetY(offsetY);}
	public void setOffset(double offsetX, double offsetY, double offsetZ) {setOffsetX(offsetX); setOffsetY(offsetY); setOffsetZ(offsetZ);}
	public void setOffsetX(double offsetX) {offset.setX(offsetX);}
	public void setOffsetY(double offsetY) {offset.setY(offsetY);}
	public void setOffsetZ(double offsetZ) {offset.setZ(offsetZ);}
	
	public double getFOVY() {return fovy;}
	public double getDepthInterval() {return farClip-nearClip;}
	public double getNearClip() {return nearClip;}
	public double getFarClip() {return farClip;}
	public double getOffsetX() {return offset.getX();}
	public double getOffsetY() {return offset.getY();}
	public double getOffsetZ() {return offset.getZ();}
	
	@Override
	public void projectView(ShaderProgram sp) {
		sp.setPerspectiveProjection(fovy, viewPortRatio, nearClip, farClip);
	}
	
	@Override
	public void adjustView(ShaderProgram sp) {
		sp.translate(-offset.getX(), -offset.getY(), -offset.getZ());
		sp.applyModelView();
	}
	
}
