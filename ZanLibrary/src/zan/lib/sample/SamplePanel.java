package zan.lib.sample;

import zan.lib.core.CoreEngine;
import zan.lib.gfx.ShaderProgram;
import zan.lib.gfx.obj.VertexObject;
import zan.lib.gfx.view.ViewPort2D;
import zan.lib.math.matrix.MatUtil;
import zan.lib.panel.BasePanel;
import zan.lib.util.Utility;
import static org.lwjgl.opengl.GL11.*;

public class SamplePanel extends BasePanel {
	
	private ShaderProgram shaderProgram;
	private ViewPort2D viewPort;
	
	private VertexObject vObject;
	
	private double vTick;
	
	public SamplePanel(CoreEngine core) {
		viewPort = new ViewPort2D(0, 0, core.getScreenWidth(), core.getScreenHeight());
	}
	
	@Override
	public void init() {
		shaderProgram = new ShaderProgram("res/shader/sample.glvs", "res/shader/sample.glfs");
		
		viewPort.showView();
		viewPort.projectView(shaderProgram);
		
		int[] indices = {0, 1, 2};
		float[] vertices = {
			-0.6f, -0.4f,
			0.6f, -0.4f,
			0f, 0.6f,
		};
		vObject = new VertexObject(vertices, indices);
		vObject.setNumCoords(2);
		vObject.setDrawMode(GL_TRIANGLE_FAN);
		
		vTick = 0.0;
	}
	
	@Override
	public void destroy() {
		vObject.destroy();
		shaderProgram.destroy();
	}
	
	@Override
	public void update(double time) {
		vTick += 3.0;
	}
	
	@Override
	public void render(double ip) {
		shaderProgram.bind();
		shaderProgram.pushMatrix();
		viewPort.adjustView(shaderProgram);
		
		float iVTick = Utility.interpolateLinear((float)vTick - 3f, (float)vTick, (float)ip);
		
		shaderProgram.pushMatrix();
		shaderProgram.multMatrix(MatUtil.translationMat44D(0.0, 0.0, 0.0));
		shaderProgram.multMatrix(MatUtil.rotationMat44D(iVTick, 0.0, 1.0, 1.0));
		shaderProgram.multMatrix(MatUtil.scaleMat44D(1.0, 1.0, 1.0));
		shaderProgram.applyModelView();
		
		shaderProgram.setColor(1.0, 0.5, 0.0, 1.0);
		vObject.render(shaderProgram);
		
		shaderProgram.popMatrix();
		shaderProgram.unbind();
	}
	
	@Override
	public void onScreenResize(int width, int height) {
		shaderProgram.bindState();
		viewPort.setScreenSize(width, height);
		viewPort.setViewPort(0, 0, width, height);
		viewPort.showView();
		viewPort.projectView(shaderProgram);
	}
	
}
