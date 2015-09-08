package zan.lib.gfx.view;

import static org.lwjgl.opengl.GL11.glViewport;

import zan.lib.gfx.shader.DefaultShader;

public abstract class ViewPort {

	protected int screenWidth, screenHeight;

	protected int viewPortX, viewPortY;
	protected int viewPortW, viewPortH;

	protected double screenRatio;
	protected double viewPortRatio;

	public ViewPort(int viewPortX, int viewPortY, int viewPortW, int viewPortH) {
		setScreenSize(viewPortW, viewPortH);
		setViewPort(viewPortX, viewPortY, viewPortW, viewPortH);
	}

	public void setScreenSize(int screenWidth, int screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		screenRatio = (double)screenWidth/(double)screenHeight;
	}

	public void setViewPort(int viewPortX, int viewPortY, int viewPortW, int viewPortH) {
		this.viewPortX = viewPortX;
		this.viewPortY = viewPortY;
		this.viewPortW = viewPortW;
		this.viewPortH = viewPortH;
		viewPortRatio = (double)viewPortW/(double)viewPortH;
	}

	public int getScreenWidth() {return screenWidth;}
	public int getScreenHeight() {return screenHeight;}

	public int getViewPortX() {return viewPortX;}
	public int getViewPortY() {return viewPortY;}
	public int getViewPortW() {return viewPortW;}
	public int getViewPortH() {return viewPortH;}

	public double getScreenRatio() {return screenRatio;}
	public double getViewPortRatio() {return viewPortRatio;}

	public void showView() {glViewport(viewPortX, viewPortY, viewPortW, viewPortH);}

	public abstract void projectView(DefaultShader sp);
	public abstract void adjustView(DefaultShader sp);

}
