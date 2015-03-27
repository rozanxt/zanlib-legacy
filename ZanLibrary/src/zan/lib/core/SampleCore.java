package zan.lib.core;

import zan.lib.input.InputManager;
import zan.lib.panel.SamplePanel;

public class SampleCore extends CoreEngine {
	
	@Override
	protected void onKey(int key, int action, int mods, int scancode) {
		if (key == InputManager.IM_KEY_ESCAPE && action == InputManager.IM_RELEASE) close();
		else if (key == InputManager.IM_KEY_F11 && action == InputManager.IM_RELEASE) toggleFullScreen();
	}
	
	@Override
	protected void onScreenResize(int width, int height) {
		getPanel().onScreenResize(width, height);
	}
	
	public static void main(String[] args) {
		SampleCore core = new SampleCore();
		core.setTitle("Sample Title");
		core.setScreenSize(800, 600);
		core.setPanel(new SamplePanel(core));
		core.run();
	}
	
}
