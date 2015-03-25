package zan.lib.input;

public class MouseEvent {
	
	private final int button;
	
	private int mods;
	private boolean pressed;
	private boolean released;
	
	public MouseEvent(int button) {
		this.button = button;
		clear();
	}
	
	public void clear() {
		mods = 0;
		pressed = false;
		released = false;
	}
	
	public void setMods(int mods) {this.mods = mods;}
	public void setPressed(boolean pressed) {this.pressed = pressed;}
	public void setReleased(boolean released) {this.released = released;}
	
	public int getButton() {return button;}
	public int getMods() {return mods;}
	public boolean isPressed() {return pressed;}
	public boolean isReleased() {return released;}
	
}
