package zan.lib.sample;

import java.util.ArrayList;

import zan.lib.core.CoreEngine;
import zan.lib.gfx.ShaderProgram;
import zan.lib.gfx.TextureManager;
import zan.lib.gfx.obj.SpriteObject;
import zan.lib.gfx.sprite.AnimatedSprite;
import zan.lib.gfx.sprite.Sprite;
import zan.lib.gfx.view.ViewPortScreen;
import zan.lib.panel.BasePanel;

import static zan.lib.input.InputManager.*;

public class SpritePanel extends BasePanel {
	
	private ShaderProgram shaderProgram;
	private ViewPortScreen viewPort;
	
	private Sprite sprite;
	private AnimatedSprite aniSprite;
	
	private float var;
	
	public SpritePanel(CoreEngine core) {
		viewPort = new ViewPortScreen(core.getScreenWidth(), core.getScreenHeight());
	}
	
	@Override
	public void init() {
		shaderProgram = new ShaderProgram();
		shaderProgram.enableDepthTest(false);
		
		viewPort.showView();
		viewPort.projectView(shaderProgram);
		
		sprite = new Sprite(TextureManager.loadTexture("image", "res/img/sample_image.png"), 256f, 128f);
		sprite.setPos(320f, 240f);
		sprite.setScale(200f);
		
		int ani = TextureManager.loadTexture("animation", "res/img/sample_animation.png");
		ArrayList<SpriteObject> sheet = new ArrayList<SpriteObject>();
		for (int i=0;i<12;i++) sheet.add(new SpriteObject(ani, 1536f, 128f, 128f*i, 0f, 128f*(i+1), 128f));
		aniSprite = new AnimatedSprite(sheet);
		aniSprite.setFPS(24.0, 50.0);
		aniSprite.setPos(320f, 240f);
		aniSprite.setScale(200f);
		
		var = 320f;
	}
	
	@Override
	public void destroy() {
		sprite.destroy();
		aniSprite.destroy();
		shaderProgram.destroy();
	}
	
	@Override
	public void update(double time) {
		if (isKeyDown(IM_KEY_RIGHT)) {
			var += 5f;
			aniSprite.tick();
			aniSprite.setFlip(0);
		} else if (isKeyDown(IM_KEY_LEFT)) {
			var -= 5f;
			aniSprite.tick();
			aniSprite.setFlip(1);
		}
		
		sprite.setScale(var);
		sprite.setAngle(var);
		aniSprite.setX(var);
		
		sprite.update();
		aniSprite.update();
	}
	
	@Override
	public void render(double ip) {
		shaderProgram.bind();
		shaderProgram.pushMatrix();
		
		sprite.render(shaderProgram, ip);
		aniSprite.render(shaderProgram, ip);
		
		shaderProgram.popMatrix();
		shaderProgram.unbind();
	}
	
	@Override
	public void onScreenResize(int width, int height) {
		shaderProgram.bindState();
		viewPort.setViewPort(width, height);
		viewPort.showView();
		viewPort.projectView(shaderProgram);
	}
	
}
