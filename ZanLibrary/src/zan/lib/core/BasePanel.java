package zan.lib.core;

public abstract class BasePanel {

	public abstract void init();
	public abstract void destroy();
	public abstract void update(double time);
	public abstract void render(double ip);

	public BasePanel changePanel() {return null;}

	public void onKey(int key, int state, int mods, int scancode) {}
	public void onChar(char ch) {}
	public void onMouseButton(int button, int state, int mods) {}
	public void onMouseMove(double mouseX, double mouseY) {}
	public void onMouseScroll(double scrollX, double scrollY) {}
	public void onMouseEnter(boolean mouseEnter) {}

	public void onWindowClose() {}
	public void onWindowRefresh() {}
	public void onWindowResize(int width, int height) {}
	public void onScreenResize(int width, int height) {}
	public void onWindowMove(int windowX, int windowY) {}
	public void onWindowMinimize(boolean minimize) {}
	public void onWindowFocus(boolean focus) {}

}
