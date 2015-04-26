package zan.lib.gfx.sprite;

import zan.lib.gfx.ShaderProgram;
import zan.lib.gfx.obj.SpriteObject;

public class Sprite extends BaseSprite {
	
	private SpriteObject sprite;
	
	public Sprite(SpriteObject sprite) {
		super();
		this.sprite = sprite;
	}
	public Sprite(int textureID, float width, float height) {
		super();
		sprite = new SpriteObject(textureID, width, height);
	}
	
	@Override
	public void destroy() {
		sprite.destroy();
	}
	
	@Override
	public void draw(ShaderProgram sp) {
		sprite.render(sp);
	}
	
}
