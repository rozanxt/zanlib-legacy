package zan.lib.panel;

public abstract class BasePanel {
	
	public BasePanel() {}
	
	public abstract void init();
	public abstract void destroy();
	public abstract void update(double time);
	public abstract void render(double ip);
	
	public BasePanel changePanel() {return null;}
	
}
