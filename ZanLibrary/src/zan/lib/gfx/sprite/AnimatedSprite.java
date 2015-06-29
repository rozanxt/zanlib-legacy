package zan.lib.gfx.sprite;

import java.util.ArrayList;

import zan.lib.gfx.ShaderProgram;
import zan.lib.gfx.obj.SpriteObject;

public class AnimatedSprite extends BaseSprite {
	
	private ArrayList<SpriteObject> sprites;
	
	private double ticks;
	private double framePeriod;
	private double nextFrame;
	private int currentFrame;
	
	public AnimatedSprite(ArrayList<SpriteObject> sprites) {
		super();
		this.sprites = sprites;
		ticks = 0.0;
		framePeriod = 1.0;
		nextFrame = 0.0;
		currentFrame = 0;
	}
	
	@Override
	public void destroy() {
		for (int i=0;i<sprites.size();i++) sprites.get(i).destroy();
	}
	
	public void setCurrentFrame(int currentFrame) {this.currentFrame = currentFrame;}
	public void setFPS(double fps, double tps) {framePeriod = tps/fps;}
	
	public void tick() {
		ticks += 1.0;
		if (ticks > nextFrame) {
			int times = (int)((ticks-nextFrame)/framePeriod)+1;
			currentFrame += times;
			if (currentFrame >= sprites.size()) currentFrame %= sprites.size();
			nextFrame += framePeriod * times;
		}
	}
	
	@Override
	public void draw(ShaderProgram sp) {
		sprites.get(currentFrame).render(sp);
	}
	
}