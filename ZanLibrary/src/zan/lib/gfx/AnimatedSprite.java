package zan.lib.gfx;

import java.util.ArrayList;

public class AnimatedSprite extends BaseSprite {
	
	private ArrayList<RawSprite> sprites;
	
	private double ticks;
	private double framePeriod;
	private double nextFrame;
	private int currentFrame;
	
	public AnimatedSprite(ArrayList<RawSprite> sprites) {
		super();
		this.sprites = sprites;
		ticks = 0.0;
		framePeriod = 1.0;
		nextFrame = 0.0;
		currentFrame = 0;
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
	
	public void draw() {sprites.get(currentFrame).draw();}
	
}
