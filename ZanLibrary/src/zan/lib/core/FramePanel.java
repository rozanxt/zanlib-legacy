package zan.lib.core;

public abstract class FramePanel {

	public abstract void create();
	public abstract void destroy();
	public abstract void update(double time);
	public abstract void render(double ip);

	public void onKey(int key, int state, int mods, int scancode) {}
	public void onChar(char ch) {}
	public void onMouseButton(int button, int state, int mods) {}
	public void onMouseMove(double x, double y) {}
	public void onMouseScroll(double x, double y) {}
	public void onMouseEnter(boolean mouseEnter) {}

	public void onWindowClose() {}
	public void onWindowRefresh() {}
	public void onWindowResize(int width, int height) {}
	public void onScreenResize(int width, int height) {}
	public void onWindowMove(int x, int y) {}
	public void onWindowMinimize(boolean minimized) {}
	public void onWindowFocus(boolean focus) {}

}
