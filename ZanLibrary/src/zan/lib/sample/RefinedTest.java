package zan.lib.sample;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import zan.lib.core.CoreEngine;
import zan.lib.gfx.ShaderProgram;
import zan.lib.gfx.VertexObject;
import zan.lib.panel.BasePanel;
import static org.lwjgl.opengl.GL11.*;

public class RefinedTest extends BasePanel {
	
	private CoreEngine core;
	
	private VertexObject vertexObject;
	
	public RefinedTest(CoreEngine core) {
		this.core = core;
	}
	
	public void init() {
		//glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glDisable(GL_DEPTH_TEST);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glClearColor(0f, 0f, 0f, 1f);
		
		glViewport(0, 0, core.getScreenWidth(), core.getScreenHeight());
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0.0, core.getScreenWidth(), 0.0, core.getScreenHeight(), 1.0, -1.0);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		vertexObject = new VertexObject();
		vertexObject.setShaderProgram(new ShaderProgram("res/shader/sample.gvs", "res/shader/sample.gfs"));
		
		final float[] ver = {
			-100f, -100f,
			100f, -100f,
			100f, 100f,
			-100f, 100f
		};
		FloatBuffer vertices = BufferUtils.createFloatBuffer(ver.length);
		vertices.put(ver);
		vertices.flip();
		vertexObject.createVBO(vertices);
		
		final int[] ind = {0, 1, 2, 3};
		IntBuffer indices = BufferUtils.createIntBuffer(ind.length);
		indices.put(ind);
		indices.flip();
		vertexObject.createIBO(indices);
	}
	
	public void destroy() {
		
	}
	
	public void update(double time) {
		
	}
	
	public void render(double ip) {
		glPushMatrix();
		glTranslatef(core.getScreenWidth()/2f, core.getScreenHeight()/2f, 0f);
		vertexObject.render(ip);
		glPopMatrix();
	}
	
	@Override
	public void onScreenResize(int width, int height) {
		glViewport(0, 0, width, height);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0.0, width, 0.0, height, 1.0, -1.0);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}
	
}
