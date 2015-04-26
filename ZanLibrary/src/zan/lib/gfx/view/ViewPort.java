package zan.lib.gfx.view;

import zan.lib.gfx.ShaderProgram;

import static org.lwjgl.opengl.GL11.glViewport;

public abstract class ViewPort {
	
	protected int screenWidth, screenHeight;
	
	protected int viewPortX, viewPortY;
	protected int viewPortW, viewPortH;
	
	protected float screenRatio;
	protected float viewPortRatio;
	
	public ViewPort(int viewPortX, int viewPortY, int viewPortW, int viewPortH) {
		setScreenSize(viewPortW, viewPortH);
		setViewPort(viewPortX, viewPortY, viewPortW, viewPortH);
	}
	
	public void setScreenSize(int screenWidth, int screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		screenRatio = (float)screenWidth/(float)screenHeight; 
	}
	
	public void setViewPort(int viewPortX, int viewPortY, int viewPortW, int viewPortH) {
		this.viewPortX = viewPortX;
		this.viewPortY = viewPortY;
		this.viewPortW = viewPortW;
		this.viewPortH = viewPortH;
		viewPortRatio = (float)viewPortW/(float)viewPortH;
	}
	
	public int getScreenWidth() {return screenWidth;}
	public int getScreenHeight() {return screenHeight;}
	
	public int getViewPortX() {return viewPortX;}
	public int getViewPortY() {return viewPortY;}
	public int getViewPortW() {return viewPortW;}
	public int getViewPortH() {return viewPortH;}
	
	public float getScreenRatio() {return screenRatio;}
	public float getViewPortRatio() {return viewPortRatio;}
	
	public void showView() {
		glViewport(viewPortX, viewPortY, viewPortW, viewPortH);
	}
	
	public abstract void projectView(ShaderProgram sp);
	public abstract void adjustView(ShaderProgram sp);
	
}
