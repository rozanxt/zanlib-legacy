package zan.lib.droid.gfx.view;

import zan.lib.droid.gfx.shader.DefaultShader;
import zan.lib.droid.util.math.MatUtil;
import zan.lib.droid.util.math.Vec3F;

public class ViewPort3D extends ViewPort {

	private float fovy;
	private float nearClip, farClip;
	private Vec3F offset;

	public ViewPort3D(int viewPortX, int viewPortY, int viewPortW, int viewPortH) {
		super(viewPortX, viewPortY, viewPortW, viewPortH);
		offset = new Vec3F();
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
	public void setOffsetX(float offsetX) {offset.setX(offsetX);}
	public void setOffsetY(float offsetY) {offset.setY(offsetY);}
	public void setOffsetZ(float offsetZ) {offset.setZ(offsetZ);}

	public float getFOVY() {return fovy;}
	public float getDepthInterval() {return farClip-nearClip;}
	public float getNearClip() {return nearClip;}
	public float getFarClip() {return farClip;}
	public float getOffsetX() {return offset.getX();}
	public float getOffsetY() {return offset.getY();}
	public float getOffsetZ() {return offset.getZ();}

	@Override
	public void projectView(DefaultShader sp) {
		sp.setPerspectiveProjection(fovy, viewPortRatio, nearClip, farClip);
	}

	@Override
	public void adjustView(DefaultShader sp) {
		sp.setViewMatrix(MatUtil.translationMat44F(-offset.getX(), -offset.getY(), -offset.getZ()));
	}

}
