package zan.lib.util;

import static org.lwjgl.opengl.GL11.*;

public class ViewPort {
	
	private int viewPortX, viewPortY;
	private int viewPortW, viewPortH;
	
	private float viewPortRatio;
	private float screenToVirtualRatio;
	private float virtualToScreenRatio;
	
	private float heightInterval;
	private float originX, originY;
	private float offsetX, offsetY;
	
	public ViewPort(int viewPortX, int viewPortY, int viewPortW, int viewPortH) {
		setViewPort(viewPortX, viewPortY, viewPortW, viewPortH);
		setHeightInterval(viewPortH);
		setOrigin(0f, 0f);
		setOffset(0f, 0f);
	}
	
	public void setViewPort(int viewPortX, int viewPortY, int viewPortW, int viewPortH) {
		this.viewPortX = viewPortX;
		this.viewPortY = viewPortY;
		this.viewPortW = viewPortW;
		this.viewPortH = viewPortH;
		calcRatio();
	}
	
	public void setHeightInterval(float heightInterval) {this.heightInterval = heightInterval; calcRatio();}
	public void setOrigin(float originX, float originY) {this.originX = originX; this.originY = originY;}
	public void setOriginX(float originX) {this.originX = originX;}
	public void setOriginY(float originY) {this.originY = originY;}
	public void setOffset(float offsetX, float offsetY) {this.offsetX = offsetX; this.offsetY = offsetY;}
	public void setOffsetX(float offsetX) {this.offsetX = offsetX;}
	public void setOffsetY(float offsetY) {this.offsetY = offsetY;}
	
	private void calcRatio() {
		viewPortRatio = (float)viewPortW/(float)viewPortH;
		screenToVirtualRatio = heightInterval/(float)viewPortH;
		virtualToScreenRatio = (float)viewPortH/heightInterval;
	}
	
	public int getViewPortX() {return viewPortX;}
	public int getViewPortY() {return viewPortY;}
	public int getViewPortW() {return viewPortW;}
	public int getViewPortH() {return viewPortH;}
	
	public float getHeightInterval() {return heightInterval;}
	public float getOffsetX() {return offsetX;}
	public float getOffsetY() {return offsetY;}
	public float getOriginX() {return originX;}
	public float getOriginY() {return originY;}
	
	public float getViewPortRatio() {return viewPortRatio;}
	public float getScreenToVirtualRatio() {return screenToVirtualRatio;}
	public float getVirtualToScreenRatio() {return virtualToScreenRatio;}
	
	public float getVirtualLeft() {return offsetX - heightInterval * viewPortRatio * originX;}
	public float getVirtualRight() {return offsetX + heightInterval * viewPortRatio * (1 - originX);}
	public float getVirtualDown() {return offsetY - heightInterval * originY;}
	public float getVirtualUp() {return offsetY + heightInterval * (1 - originY);}
	
	public float getScreenToVirtualX(float x) {return getVirtualLeft() + screenToVirtualRatio * x;}
	public float getScreenToVirtualY(float y) {return getVirtualUp() - screenToVirtualRatio * y;}
	
	public float getVirtualToScreenX(float x) {return (x - getVirtualLeft()) * virtualToScreenRatio;}
	public float getVirtualToScreenY(float y) {return (getVirtualUp() - y) * virtualToScreenRatio;}
	
	public static void show(ViewPort viewPort) {
		glViewport(viewPort.viewPortX, viewPort.viewPortY, viewPort.viewPortW, viewPort.viewPortH);
	}
	
	public static void project2D(ViewPort viewPort) {
		glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(viewPort.getVirtualLeft(), viewPort.getVirtualRight(), viewPort.getVirtualDown(), viewPort.getVirtualUp(), -1f, 1f);
        glMatrixMode(GL_MODELVIEW);
	}
	
	public static void projectScreen(ViewPort viewPort) {
		glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0f, viewPort.viewPortW, 0f, viewPort.viewPortH, -1f, 1f);
        glMatrixMode(GL_MODELVIEW);
	}
	
	public static void showAxis(float limit) {
		glColor4f(0f, 1f, 0f, 0.5f);
		glBegin(GL_LINES);
			glVertex2f(-limit, 0f);
			glVertex2f(limit, 0f);
			glVertex2f(0f, -limit);
			glVertex2f(0f, limit);
		glEnd();
	}
	
	public static void showGrid(float grid, float limit) {
		int ng = (int)(2*limit/grid);
		glColor4f(0f, 1f, 0f, 0.2f);
		glBegin(GL_LINES);
			for (int i=1;i<ng;i++) {
				glVertex2f(-limit, -limit+i*grid);
				glVertex2f(limit, -limit+i*grid);
			}
			for (int i=1;i<ng;i++) {
				glVertex2f(-limit+i*grid, -limit);
				glVertex2f(-limit+i*grid, limit);
			}
		glEnd();
	}
	
}
