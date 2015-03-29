package zan.lib.sample;

import zan.lib.core.CoreEngine;
import zan.lib.input.InputManager;

/** Sample main class for ZanLibrary
 * 
 * @author Rozan I. Rosandi
 *
 */
public class SampleCore extends CoreEngine {
	
	@Override
	protected void onKey(int key, int state, int mods, int scancode) {
		if (key == InputManager.IM_KEY_ESCAPE && state == InputManager.IM_RELEASE) close();
		else if (key == InputManager.IM_KEY_F11 && state == InputManager.IM_RELEASE) toggleFullScreen();
		super.onKey(key, state, mods, scancode);
	}
	
	public static void main(String[] args) {
		SampleCore core = new SampleCore();
		core.setTitle("Sample Title");
		core.setScreenSize(800, 600);
		core.setPanel(new SamplePanel(core));
		core.run();
	}
	
}
