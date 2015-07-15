package zan.lib.gfx.view;

import zan.lib.gfx.ShaderProgram;

public class ViewPort2D extends ViewPort {
	
	private double heightInterval;
	private double depthInterval;
	private double zoomScale;
	private double originX, originY;
	private double offsetX, offsetY;
	
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
	public void setOriginX(double originX) {this.originX = originX;}
	public void setOriginY(double originY) {this.originY = originY;}
	public void setOffset(double offsetX, double offsetY) {setOffsetX(offsetX); setOffsetY(offsetY);}
	public void setOffsetX(double offsetX) {this.offsetX = offsetX;}
	public void setOffsetY(double offsetY) {this.offsetY = offsetY;}
	
	public void calcRatio() {
		screenToVirtualRatio = heightInterval/(double)viewPortH;
		virtualToScreenRatio = (double)viewPortH/heightInterval;
	}
	
	public double getHeightInterval() {return heightInterval;}
	public double getDepthInterval() {return depthInterval;}
	public double getOriginX() {return originX;}
	public double getOriginY() {return originY;}
	public double getOffsetX() {return offsetX;}
	public double getOffsetY() {return offsetY;}
	
	public double getScreenToVirtualRatio() {return screenToVirtualRatio;}
	public double getVirtualToScreenRatio() {return virtualToScreenRatio;}
	
	public double getScreenToVirtualX(double x) {
		return (x-viewPortX-originX*viewPortW)*screenToVirtualRatio+offsetX;
	}
	public double getScreenToVirtualY(double y) {
		return ((screenHeight-y)-viewPortY-originY*viewPortH)*screenToVirtualRatio+offsetY;
	}
	public double getVirtualToScreenX(double x) {
		return (x-offsetX)*virtualToScreenRatio+originX*viewPortW+viewPortX;
	}
	public double getVirtualToScreenY(double y) {
		return screenHeight-((y-offsetY)*virtualToScreenRatio+originY*viewPortH+viewPortY);
	}
	
	@Override
	public void projectView(ShaderProgram sp) {
		sp.setOrthoProjection(-viewPortRatio, viewPortRatio, -1.0, 1.0, -depthInterval*0.5, depthInterval*0.5);
	}
	
	@Override
	public void adjustView(ShaderProgram sp) {
		sp.scale(zoomScale, zoomScale, 1.0);
		sp.translate(-offsetX, -offsetY, 0.0);
	}
	
}
