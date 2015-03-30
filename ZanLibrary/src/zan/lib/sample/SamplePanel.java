package zan.lib.sample;

import zan.lib.core.CoreEngine;
import zan.lib.gfx.Sprite;
import zan.lib.gfx.TextureManager;
import zan.lib.panel.BasePanel;
import zan.lib.util.ViewPort;

/** Sample panel for ZanLibrary
 * 
 * @author Rozan I. Rosandi
 *
 */
public class SamplePanel extends BasePanel {
	
	private ViewPort viewPort;
	
	private Sprite sprite;
	
	private double rotation;
	
	public SamplePanel(CoreEngine core) {
		viewPort = new ViewPort(0, 0, core.getScreenWidth(), core.getScreenHeight());
	}
	
	@Override
	public void init() {
		viewPort.setHeightInterval(2f);
		viewPort.setOrigin(0.5f, 0.5f);
		
		rotation = 0.0;
		
		sprite = new Sprite(TextureManager.loadTexture("sample_texture", "res/img/sample_image.png"), 256, 128);
	}
	
	@Override
	public void destroy() {}
	
	@Override
	public void update(double time) {
		rotation += 2.0;
		sprite.setAngle((float)rotation);
	}
	
	@Override
	public void render(double ip) {
		ViewPort.show(viewPort);
		ViewPort.project2D(viewPort);
		
		sprite.render((float)ip);
	}
	
	@Override
	public void onScreenResize(int width, int height) {
		viewPort.setViewPort(0, 0, width, height);
	}
	
}
