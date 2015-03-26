package zan.lib.panel;

import static org.lwjgl.opengl.GL11.*;
import zan.lib.core.CoreEngine;
import zan.lib.input.InputManager;

public class SamplePanel extends BasePanel {
	
	private CoreEngine core;
	
	public SamplePanel(CoreEngine core) {
		this.core = core;
	}
	
	@Override
	public void init() {}
	
	@Override
	public void destroy() {}
	
	@Override
	public void update(double time) {
		if (InputManager.isKeyReleased(InputManager.IM_KEY_ESCAPE)) core.close();
		else if (InputManager.isKeyReleased(InputManager.IM_KEY_F11)) core.toggleFullScreen();
	}
	
	@Override
	public void render(double ip) {
		glLoadIdentity();
		glRotatef((float)(core.getTicks()+ip)*2f, 0f, 0f, 1f);
		glBegin(GL_TRIANGLES);
			glColor3f(1f, 0f, 0f);
			glVertex3f(-0.6f, -0.4f, 0f);
			glColor3f(0f, 1f, 0f);
			glVertex3f(0.6f, -0.4f, 0f);
			glColor3f(0f, 0f, 1f);
			glVertex3f(0f, 0.6f, 0f);
		glEnd();
	}
	
}
