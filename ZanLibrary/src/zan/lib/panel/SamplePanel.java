package zan.lib.panel;

import static org.lwjgl.opengl.GL11.*;
import zan.lib.core.CoreEngine;
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
		viewPort.setHeightInterval(2f);
		viewPort.setOrigin(0.5f, 0.5f);
	}
	
	@Override
	public void destroy() {}
	
	@Override
	public void update(double time) {}
	
	@Override
	public void render(double ip) {
		ViewPort.show(viewPort);
		ViewPort.project2D(viewPort);
		
		glPushMatrix();
		glRotatef((float)(core.getTicks()+ip)*2f, 0f, 0f, 1f);
		glBegin(GL_TRIANGLES);
			glColor3f(1f, 0f, 0f);
			glVertex3f(-0.6f, -0.4f, 0f);
			glColor3f(0f, 1f, 0f);
			glVertex3f(0.6f, -0.4f, 0f);
			glColor3f(0f, 0f, 1f);
			glVertex3f(0f, 0.6f, 0f);
		glEnd();
		glPopMatrix();
	}
	
	@Override
	public void onScreenResize(int width, int height) {
		viewPort.setViewPort(0, 0, width, height);
	}
	
}
