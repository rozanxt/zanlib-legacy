package zan.lib.sample;

import static zan.lib.input.InputManager.*;

import zan.lib.core.FrameEngine;

public class SampleCore extends FrameEngine {

	@Override
	protected void onKey(int key, int state, int mods, int scancode) {
		if (key == IM_KEY_ESCAPE && state == IM_RELEASE) close();
		else if (key == IM_KEY_F11 && state == IM_RELEASE) setFullScreen(!isFullScreen());
		super.onKey(key, state, mods, scancode);
	}

	public static void main(String[] args) {
		SampleCore core = new SampleCore();
		core.setTitle("Sample Program");
		core.setIcon("res/ico/sample_icon.png");
		core.setPanel(new SamplePanel(core));
		core.run();
	}

}
