package zan.lib.gfx.view;

import zan.lib.gfx.ShaderProgram;
import zan.lib.math.matrix.MatUtil;

public class ViewPort3D extends ViewPort {
	
	private float fovy;
	private float nearClip, farClip;
	private float offsetX, offsetY, offsetZ;
	
	public ViewPort3D(int viewPortX, int viewPortY, int viewPortW, int viewPortH) {
		super(viewPortX, viewPortY, viewPortW, viewPortH);
		setFOVY(120f);
		setDepthInterval(0.1f, 100f);
		setOffset(0f, 0f, 0f);
	}
	
	public void setFOVY(float fovy) {this.fovy = fovy;}
	public void setDepthInterval(float depthInterval) {setNearClip(0.1f); setFarClip(0.1f+depthInterval);}
	public void setDepthInterval(float nearClip, float farClip) {setNearClip(nearClip); setFarClip(farClip);}
	public void setNearClip(float nearClip) {this.nearClip = nearClip;}
	public void setFarClip(float farClip) {this.farClip = farClip;}
	public void setOffset(float offsetX, float offsetY) {setOffsetX(offsetX); setOffsetY(offsetY);}
	public void setOffset(float offsetX, float offsetY, float offsetZ) {setOffsetX(offsetX); setOffsetY(offsetY); setOffsetZ(offsetZ);}
	public void setOffsetX(float offsetX) {this.offsetX = offsetX;}
	public void setOffsetY(float offsetY) {this.offsetY = offsetY;}
	public void setOffsetZ(float offsetZ) {this.offsetZ = offsetZ;}
	
	public float getFOVY() {return fovy;}
	public float getDepthInterval() {return farClip-nearClip;}
	public float getNearClip() {return nearClip;}
	public float getFarClip() {return farClip;}
	public float getOffsetX() {return offsetX;}
	public float getOffsetY() {return offsetY;}
	public float getOffsetZ() {return offsetZ;}
	
	@Override
	public void projectView(ShaderProgram sp) {
		sp.setProjection(MatUtil.perspectiveProjectionMatrix(fovy, viewPortRatio, nearClip, farClip));
	}
	
	@Override
	public void adjustView(ShaderProgram sp) {
		sp.multMatrix(MatUtil.translationMat44D(-offsetX, -offsetY, -offsetZ));
	}
	
}
