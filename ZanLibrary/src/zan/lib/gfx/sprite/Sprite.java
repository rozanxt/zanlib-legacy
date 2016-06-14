package zan.lib.gfx.sprite;

import zan.lib.gfx.object.SpriteObject;
import zan.lib.gfx.shader.DefaultShader;
import zan.lib.gfx.texture.TextureInfo;

public class Sprite extends BaseSprite {

	protected SpriteObject sprite;

	public Sprite(SpriteObject sprite) {this.sprite = sprite;}
	public Sprite(TextureInfo texture) {sprite = new SpriteObject(texture);}

	@Override
	public void destroy() {sprite.destroy();}

	@Override
	public void draw(DefaultShader sp) {sprite.render(sp);}

}
