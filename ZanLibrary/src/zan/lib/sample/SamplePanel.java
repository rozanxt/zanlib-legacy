package zan.lib.sample;

import static org.lwjgl.opengl.GL11.*;
import zan.lib.core.CoreEngine;
import zan.lib.gfx.TextureManager;
import zan.lib.panel.BasePanel;
import zan.lib.util.ViewPort;

/** Sample panel for ZanLibrary
 * 
 * @author Rozan I. Rosandi
 *
 */
public class SamplePanel extends BasePanel {
	
	private CoreEngine core;
	
	private ViewPort viewPort;
	
	public SamplePanel(CoreEngine core) {
		this.core = core;
		viewPort = new ViewPort(0, 0, core.getScreenWidth(), core.getScreenHeight());
	}
	
	@Override
	public void init() {
		viewPort.setHeightInterval(6f);
		viewPort.setOrigin(0.5f, 0.5f);
		
		TextureManager.loadTexture("sample_texture", "res/img/sample_image.png");
	}
	
	@Override
	public void destroy() {}
	
	@Override
	public void update(double time) {}
	
	@Override
	public void render(double ip) {
		ViewPort.show(viewPort);
		ViewPort.project2D(viewPort);
		
		glEnable(GL_TEXTURE_2D);
		glPushMatrix();
		glRotatef((float)(core.getTicks()+ip)*2f, 0f, 0f, 1f);
		glColor4f(1f, 1f, 1f, 1f);
		glBindTexture(GL_TEXTURE_2D, TextureManager.getTextureID("sample_texture"));
		glBegin(GL_QUADS);
			glTexCoord2f(0f, 1f);
			glVertex2f(-2f, -1f);
			glTexCoord2f(1f, 1f);
			glVertex2f(2f, -1f);
			glTexCoord2f(1f, 0f);
			glVertex2f(2f, 1f);
			glTexCoord2f(0f, 0f);
			glVertex2f(-2f, 1f);
		glEnd();
		glPopMatrix();
		glDisable(GL_TEXTURE_2D);
	}
	
	@Override
	public void onScreenResize(int width, int height) {
		viewPort.setViewPort(0, 0, width, height);
	}
	
}
