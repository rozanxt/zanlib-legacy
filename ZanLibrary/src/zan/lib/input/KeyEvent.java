package zan.lib.input;

public class KeyEvent {

	private final int key;

	private int mods;
	private boolean pressed;
	private boolean released;
	private boolean repeated;

	public KeyEvent(int key) {
		this.key = key;
		clear();
	}

	public void clear() {
		mods = 0;
		pressed = false;
		released = false;
		repeated = false;
	}

	public void setMods(int mods) {this.mods = mods;}
	public void setPressed(boolean pressed) {this.pressed = pressed;}
	public void setReleased(boolean released) {this.released = released;}
	public void setRepeated(boolean repeated) {this.repeated = repeated;}

	public int getKey() {return key;}
	public int getMods() {return mods;}
	public boolean isPressed() {return pressed;}
	public boolean isReleased() {return released;}
	public boolean isRepeated() {return repeated;}

}
