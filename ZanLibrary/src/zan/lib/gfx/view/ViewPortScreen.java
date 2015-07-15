package zan.lib.gfx.view;

import zan.lib.gfx.ShaderProgram;

public class ViewPortScreen extends ViewPort {
	
	private double heightInterval;
	private double depthInterval;
	private double aspectRatio;
	
	public ViewPortScreen(int screenWidth, int screenHeight) {
		super(0, 0, screenWidth, screenHeight);
		setHeightInterval(screenHeight);
		setDepthInterval(2.0);
		aspectRatio = (double)screenWidth/(double)screenHeight;
	}
	
	public void setViewPort(int screenWidth, int screenHeight) {
		setScreenSize(screenWidth, screenHeight);
		setViewPort(0, 0, screenWidth, screenHeight);
	}
	
	public void setHeightInterval(double heightInterval) {this.heightInterval = heightInterval;}
	public void setDepthInterval(double depthInterval) {this.depthInterval = depthInterval;}
	
	public double getHeightInterval() {return heightInterval;}
	public double getDepthInterval() {return depthInterval;}
	
	@Override
	public void projectView(ShaderProgram sp) {
		double clipOffset = (heightInterval*(screenRatio-aspectRatio))*0.5;
		sp.setOrthoProjection(-clipOffset, -clipOffset+heightInterval*viewPortRatio, 0.0, heightInterval, -depthInterval*0.5, depthInterval*0.5);
	}
	
	@Override
	public void adjustView(ShaderProgram sp) {}
	
}
