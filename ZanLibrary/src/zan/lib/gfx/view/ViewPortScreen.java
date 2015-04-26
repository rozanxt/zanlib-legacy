package zan.lib.gfx.view;

import zan.lib.gfx.ShaderProgram;
import zan.lib.math.matrix.MatUtil;

public class ViewPortScreen extends ViewPort {
	
	private float heightInterval;
	private float depthInterval;
	private float aspectRatio;
	
	public ViewPortScreen(int screenWidth, int screenHeight) {
		super(0, 0, screenWidth, screenHeight);
		setHeightInterval(screenHeight);
		setDepthInterval(2f);
		aspectRatio = (float)screenWidth/(float)screenHeight;
	}
	
	public void setViewPort(int screenWidth, int screenHeight) {
		setScreenSize(screenWidth, screenHeight);
		setViewPort(0, 0, screenWidth, screenHeight);
	}
	
	public void setHeightInterval(float heightInterval) {this.heightInterval = heightInterval;}
	public void setDepthInterval(float depthInterval) {this.depthInterval = depthInterval;}
	
	public float getHeightInterval() {return heightInterval;}
	public float getDepthInterval() {return depthInterval;}
	
	@Override
	public void projectView(ShaderProgram sp) {
		float clipOffset = (heightInterval*(screenRatio-aspectRatio))*0.5f;
		sp.setProjection(MatUtil.orthoProjectionMatrix(-clipOffset, -clipOffset+heightInterval*viewPortRatio, 0f, heightInterval, -depthInterval*0.5f, depthInterval*0.5f));
	}
	
	@Override
	public void adjustView(ShaderProgram sp) {}
	
}
