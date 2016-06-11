package zan.lib.sample;

import static zan.lib.input.InputManager.*;
import zan.lib.core.FramePanel;
import zan.lib.gfx.obj.SpriteObject;
import zan.lib.gfx.shader.DefaultShader;
import zan.lib.gfx.sprite.AnimatedSprite;
import zan.lib.gfx.sprite.Sprite;
import zan.lib.gfx.texture.TextureManager;
import zan.lib.gfx.view.ViewPortScreen;

public class SpritePanel extends FramePanel {

	private SampleCore core;

	private DefaultShader shader;
	private ViewPortScreen viewPort;

	private Sprite spriteA;
	private AnimatedSprite spriteB;

	private double value;

	public SpritePanel(SampleCore core) {
		this.core = core;
	}

	@Override
	public void create() {
		shader = new DefaultShader();
		shader.loadProgram();
		shader.enableBlend(true);

		viewPort = new ViewPortScreen(core.getScreenWidth(), core.getScreenHeight());
		viewPort.showView();
		viewPort.projectView(shader);

		TextureManager.create();

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
		shader.destroy();
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
		shader.bind();
		viewPort.adjustView(shader);

		spriteA.render(shader, ip);
		spriteB.render(shader, ip);

		shader.unbind();
	}

	@Override
	public void onScreenResize(int width, int height) {
		shader.bindState();
		viewPort.setScreenSize(width, height);
		viewPort.setViewPort(0, 0, width, height);
		viewPort.showView();
		viewPort.projectView(shader);
	}

}
