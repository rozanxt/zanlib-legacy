package zan.lib.core;

public abstract class BasePanel {

	public abstract void init();
	public abstract void destroy();
	public abstract void update(double time);
	public abstract void render(double ip);

	public BasePanel changePanel() {return null;}

	public void onScreenResize(int width, int height) {}

}
