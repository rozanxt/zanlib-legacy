package zan.lib.gfx.view;

import zan.lib.gfx.ShaderProgram;

public class ViewPort3D extends ViewPort {
	
	private double fovy;
	private double nearClip, farClip;
	private double offsetX, offsetY, offsetZ;
	
	public ViewPort3D(int viewPortX, int viewPortY, int viewPortW, int viewPortH) {
		super(viewPortX, viewPortY, viewPortW, viewPortH);
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
	public void setOffsetX(double offsetX) {this.offsetX = offsetX;}
	public void setOffsetY(double offsetY) {this.offsetY = offsetY;}
	public void setOffsetZ(double offsetZ) {this.offsetZ = offsetZ;}
	
	public double getFOVY() {return fovy;}
	public double getDepthInterval() {return farClip-nearClip;}
	public double getNearClip() {return nearClip;}
	public double getFarClip() {return farClip;}
	public double getOffsetX() {return offsetX;}
	public double getOffsetY() {return offsetY;}
	public double getOffsetZ() {return offsetZ;}
	
	@Override
	public void projectView(ShaderProgram sp) {
		sp.setPerspectiveProjection(fovy, viewPortRatio, nearClip, farClip);
	}
	
	@Override
	public void adjustView(ShaderProgram sp) {
		sp.translate(-offsetX, -offsetY, -offsetZ);
	}
	
}
