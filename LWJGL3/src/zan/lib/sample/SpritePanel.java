package zan.lib.sample;

import static zan.lib.input.InputManager.*;
import zan.lib.core.FramePanel;
import zan.lib.gfx.camera.CameraScreen;
import zan.lib.gfx.object.SpriteObject;
import zan.lib.gfx.scene.Scene2D;
import zan.lib.gfx.sprite.AnimatedSprite;
import zan.lib.gfx.sprite.Sprite;
import zan.lib.gfx.texture.TextureManager;

public class SpritePanel extends FramePanel {

	private SampleCore core;

	private Scene2D scene;
	private CameraScreen camera;

	private Sprite spriteA;
	private AnimatedSprite spriteB;

	private double value;

	public SpritePanel(SampleCore core) {
		this.core = core;
	}

	@Override
	public void create() {
		TextureManager.create();

		scene = new Scene2D();
		scene.create();
		scene.enableBlend(true);

		camera = new CameraScreen(core.getScreenWidth(), core.getScreenHeight());
		camera.apply(scene);

		spriteA = new Sprite(TextureManager.loadTexture("image", "res/img/sample_image.png"));
		spriteA.setPos(320.0, 240.0);
		spriteA.setScale(200.0, 200.0);

		spriteB = new AnimatedSprite(new SpriteObject(TextureManager.loadTexture("animation", "res/img/sample_animation.png"), 12, 1, SpriteObject.ALIGN_HORIZONTAL));
		spriteB.setPos(320.0, 240.0);
		spriteB.setScale(200.0, 200.0);
		spriteB.setFPS(24.0, 50.0);

		value = 320.0;
	}

	@Override
	public void destroy() {
		spriteA.destroy();
		spriteB.destroy();
		scene.destroy();
		TextureManager.destroy();
	}

	@Override
	public void update(double time) {
		if (isKeyDown(IM_KEY_RIGHT)) {
			value += 5.0;
			spriteB.tick();
			spriteB.setFlip(0);
		} else if (isKeyDown(IM_KEY_LEFT)) {
			value -= 5.0;
			spriteB.tick();
			spriteB.setFlip(1);
		}

		spriteA.setScale(value, value);
		spriteA.setAngle(value);
		spriteB.setPosX(value);

		spriteA.update();
		spriteB.update();
	}

	@Override
	public void render(double ip) {
		scene.begin();

		camera.apply(scene);

		spriteA.render(scene, ip);
		spriteB.render(scene, ip);

		scene.end();
	}

	@Override
	public void onScreenResize(int width, int height) {
		camera.setScreen(width, height);
		camera.setViewPort(0, 0, width, height);
		camera.apply(scene);
	}

}
