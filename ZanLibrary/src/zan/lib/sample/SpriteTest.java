package zan.lib.sample;

import java.util.ArrayList;

import zan.lib.core.CoreEngine;
import zan.lib.gfx.AnimatedSprite;
import zan.lib.gfx.RawSprite;
import zan.lib.gfx.Sprite;
import zan.lib.gfx.TextureManager;
import zan.lib.panel.BasePanel;
import zan.lib.util.ViewPort;

import static zan.lib.input.InputManager.*;

public class SpriteTest extends BasePanel {
	
	private CoreEngine core;
	private ViewPort viewPort;
	
	private Sprite sprite;
	private AnimatedSprite aniSprite;
	
	private double entityVar;
	private int entityFlip;
	
	public SpriteTest(CoreEngine core) {
		this.core = core;
		viewPort = new ViewPort(0, 0, core.getScreenWidth(), core.getScreenHeight());
	}
	
	public void init() {
		viewPort.setHeightInterval(10f);
		viewPort.setOrigin(0.5f, 0.5f);

		entityVar = 0.0;
		entityFlip = 0;
		
		sprite = new Sprite(TextureManager.loadTexture("image_texture", "res/img/sample_image.png"), 256, 128);
		sprite.setScale(0f);
		sprite.amendState();
		
		TextureManager.loadTexture("animation_texture", "res/img/sample_animation.png");
		int animation_texture = TextureManager.getTextureID("animation_texture");
		ArrayList<RawSprite> sprites = new ArrayList<RawSprite>();
		for (int i=0;i<12;i++) sprites.add(new RawSprite(animation_texture, 1536, 128, 128*i, 0, 128*(i+1), 128));
		aniSprite = new AnimatedSprite(sprites);
		aniSprite.setFPS(24.0, core.getTPS());
		aniSprite.setScale(3f);
		aniSprite.amendState();
	}
	
	public void destroy() {}
	
	public void update(double time) {
		if (isKeyDown(IM_KEY_RIGHT) && !isKeyDown(IM_KEY_LEFT)) {
			if (entityFlip != 0) {
				entityFlip = 0;
				aniSprite.setFlip(entityFlip);
				aniSprite.amendState();
			}
			entityVar += 0.1;
			aniSprite.tick();
		}
		if (isKeyDown(IM_KEY_LEFT) && !isKeyDown(IM_KEY_RIGHT)) {
			if (entityFlip != 1) {
				entityFlip = 1;
				aniSprite.setFlip(entityFlip);
				aniSprite.amendState();
			}
			entityVar -= 0.1;
			aniSprite.tick();
		}
		sprite.setOpacity(1f-Math.abs((float)entityVar*0.1f));
		sprite.setAngle((float)entityVar*36f);
		sprite.setScale(Math.abs((float)entityVar*2f));
		aniSprite.setX((float)entityVar);
	}
	
	public void render(double ip) {
		ViewPort.show(viewPort);
		ViewPort.project2D(viewPort);
		
		sprite.render((float)ip);
		aniSprite.render((float)ip);
	}
	
	@Override
	public void onScreenResize(int width, int height) {
		viewPort.setViewPort(0, 0, width, height);
	}
	
}
