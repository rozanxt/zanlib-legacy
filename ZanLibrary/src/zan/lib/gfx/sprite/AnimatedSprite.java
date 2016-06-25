package zan.lib.gfx.sprite;

import zan.lib.gfx.object.SpriteObject;
import zan.lib.gfx.scene.DefaultScene;

public class AnimatedSprite extends Sprite {

	protected double ticks = 0.0;
	protected double framePeriod = 1.0;
	protected double nextFrame = 0.0;
	protected int currentFrame = 0;

	public AnimatedSprite(SpriteObject sprite) {super(sprite);}

	public void setFrame(int frame) {currentFrame = frame;}
	public void setFPS(double fps, double tps) {framePeriod = tps/fps;}

	public void tick() {
		ticks += 1.0;
		if (ticks > nextFrame) {
			int times = (int)((ticks-nextFrame)/framePeriod)+1;
			currentFrame += times;
			if (currentFrame >= sprite.getNumFrames()) currentFrame %= sprite.getNumFrames();
			nextFrame += framePeriod * times;
		}
	}

	@Override
	public void draw(DefaultScene sc) {sprite.renderFrame(sc, currentFrame);}

}
