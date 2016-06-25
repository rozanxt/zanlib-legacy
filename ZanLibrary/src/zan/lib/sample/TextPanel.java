package zan.lib.sample;

import zan.lib.core.FramePanel;
import zan.lib.gfx.camera.Camera2D;
import zan.lib.gfx.scene.Scene2D;
import zan.lib.gfx.text.TextManager;
import zan.lib.gfx.texture.TextureManager;

public class TextPanel extends FramePanel {

	private SampleCore core;

	private Scene2D scene;
	private Camera2D camera;

	public TextPanel(SampleCore core) {
		this.core = core;
	}

	@Override
	public void create() {
		TextureManager.create();
		TextManager.create();
		TextManager.loadFontFile("res/fnt/fonts.res");

		scene = new Scene2D();
		scene.create();
		scene.enableBlend(true);

		camera = new Camera2D(core.getScreenWidth(), core.getScreenHeight());
		camera.apply(scene);
	}

	@Override
	public void destroy() {
		scene.destroy();
		TextManager.destroy();
		TextureManager.destroy();
	}

	@Override
	public void update(double time) {

	}

	@Override
	public void render(double ip) {
		scene.begin();

		camera.apply(scene);

		scene.pushMatrix();
		scene.translate(-0.33, 0.1, 0.0);
		scene.scale(0.1, 0.1, 1.0);
		scene.applyModelMatrix();
		scene.setColor(0.0, 0.5, 0.8, 0.8);
		TextManager.renderText(scene, "ZanLibrary", "defont");
		scene.popMatrix();

		scene.pushMatrix();
		scene.translate(-0.25, -0.1, 0.0);
		scene.scale(0.1, 0.1, 1.0);
		scene.applyModelMatrix();
		scene.setColor(0.0, 0.5, 0.8, 0.8);
		TextManager.renderText(scene, "FPS: " + core.getFPS(), "defont");
		scene.popMatrix();

		scene.end();
	}

	@Override
	public void onScreenResize(int width, int height) {
		camera.setScreen(width, height);
		camera.setViewPort(0, 0, width, height);
		camera.apply(scene);
	}

}
