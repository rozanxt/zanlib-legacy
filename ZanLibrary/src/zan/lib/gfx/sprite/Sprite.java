package zan.lib.gfx.sprite;

import zan.lib.gfx.ShaderProgram;
import zan.lib.gfx.obj.SpriteObject;
import zan.lib.gfx.obj.VertexObject;
import zan.lib.gfx.texture.TextureInfo;

public class Sprite extends BaseSprite {
	
	protected VertexObject sprite;
	
	public Sprite(VertexObject sprite) {
		super();
		this.sprite = sprite;
	}
	public Sprite(TextureInfo texture) {
		super();
		sprite = new SpriteObject(texture);
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
