package zan.lib.sample;

import zan.lib.core.BasePanel;
import zan.lib.input.InputManager;
import zan.lib.sound.SoundManager;

public class SoundPanel extends BasePanel {

	private SampleCore core;

	public SoundPanel(SampleCore core) {
		this.core = core;
	}

	@Override
	public void init() {
		SoundManager.init();
		SoundManager.loadMusic("humoresky", "res/snd/sample_music.ogg");
		SoundManager.loadSound("click", "res/snd/sample_sound.wav");
		SoundManager.playMusic("humoresky");
	}

	@Override
	public void destroy() {
		SoundManager.destroy();
	}

	@Override
	public void update(double time) {
		if (InputManager.isKeyReleased(InputManager.IM_KEY_ESCAPE)) core.close();
		else if (InputManager.isKeyReleased(InputManager.IM_KEY_F11)) core.toggleFullScreen();

		if (InputManager.isKeyPressed(InputManager.IM_KEY_SPACE)) SoundManager.toggleMusic("humoresky");
		if (InputManager.isKeyPressed(InputManager.IM_KEY_ENTER)) SoundManager.playSound("click");
	}

	@Override
	public void render(double ip) {

	}

}
