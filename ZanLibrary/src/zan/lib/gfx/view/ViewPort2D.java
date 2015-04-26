package zan.lib.gfx.view;

import zan.lib.gfx.ShaderProgram;
import zan.lib.math.matrix.MatUtil;

public class ViewPort2D extends ViewPort {
	
	private float heightInterval;
	private float depthInterval;
	private float zoomScale;
	private float originX, originY;
	private float offsetX, offsetY;
	
	private float screenToVirtualRatio;
	private float virtualToScreenRatio;
	
	public ViewPort2D(int viewPortX, int viewPortY, int viewPortW, int viewPortH) {
		super(viewPortX, viewPortY, viewPortW, viewPortH);
		setHeightInterval(2f);
		setDepthInterval(2f);
		setOrigin(0.5f, 0.5f);
		setOffset(0f, 0f);
	}
	
	@Override
	public void setViewPort(int viewPortX, int viewPortY, int viewPortW, int viewPortH) {
		super.setViewPort(viewPortX, viewPortY, viewPortW, viewPortH);
		calcRatio();
	}
	
	public void setHeightInterval(float heightInterval) {
		this.heightInterval = heightInterval;
		zoomScale = 2f/heightInterval;
		calcRatio();
	}
	public void setDepthInterval(float depthInterval) {this.depthInterval = depthInterval;}
	public void setOrigin(float originX, float originY) {setOriginX(originX); setOriginY(originY);}
	public void setOriginX(float originX) {this.originX = originX;}
	public void setOriginY(float originY) {this.originY = originY;}
	public void setOffset(float offsetX, float offsetY) {setOffsetX(offsetX); setOffsetY(offsetY);}
	public void setOffsetX(float offsetX) {this.offsetX = offsetX;}
	public void setOffsetY(float offsetY) {this.offsetY = offsetY;}
	
	public void calcRatio() {
		screenToVirtualRatio = heightInterval/(float)viewPortH;
		virtualToScreenRatio = (float)viewPortH/heightInterval;
	}
	
	public float getHeightInterval() {return heightInterval;}
	public float getDepthInterval() {return depthInterval;}
	public float getOriginX() {return originX;}
	public float getOriginY() {return originY;}
	public float getOffsetX() {return offsetX;}
	public float getOffsetY() {return offsetY;}
	
	public float getScreenToVirtualRatio() {return screenToVirtualRatio;}
	public float getVirtualToScreenRatio() {return virtualToScreenRatio;}
	
	public float getScreenToVirtualX(float x) {
		return (x-viewPortX-originX*viewPortW)*screenToVirtualRatio+offsetX;
	}
	public float getScreenToVirtualY(float y) {
		return ((screenHeight-y)-viewPortY-originY*viewPortH)*screenToVirtualRatio+offsetY;
	}
	public float getVirtualToScreenX(float x) {
		return (x-offsetX)*virtualToScreenRatio+originX*viewPortW+viewPortX;
	}
	public float getVirtualToScreenY(float y) {
		return screenHeight-((y-offsetY)*virtualToScreenRatio+originY*viewPortH+viewPortY);
	}
	
	@Override
	public void projectView(ShaderProgram sp) {
		sp.setProjection(MatUtil.orthoProjectionMatrix(-viewPortRatio, viewPortRatio, -1f, 1f, -depthInterval*0.5f, depthInterval*0.5f));
	}
	
	@Override
	public void adjustView(ShaderProgram sp) {
		sp.multMatrix(MatUtil.scaleMat44D(zoomScale, zoomScale, 1f));
		sp.multMatrix(MatUtil.translationMat44D(-offsetX, -offsetY, 0f));
	}
	
}
