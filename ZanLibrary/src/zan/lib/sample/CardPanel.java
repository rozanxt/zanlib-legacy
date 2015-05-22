package zan.lib.sample;

import zan.lib.core.CoreEngine;
import zan.lib.gfx.ShaderProgram;
import zan.lib.gfx.TextureManager;
import zan.lib.gfx.obj.TextureObject;
import zan.lib.gfx.view.ViewPort3D;
import zan.lib.math.matrix.MatUtil;
import zan.lib.panel.BasePanel;
import zan.lib.util.Utility;
import static org.lwjgl.opengl.GL11.*;

public class CardPanel extends BasePanel {
	
	private ShaderProgram shaderProgram;
	private ViewPort3D viewPort;
	
	private TextureObject vObject;
	
	private double vTick;
	
	public CardPanel(CoreEngine core) {
		viewPort = new ViewPort3D(0, 0, core.getScreenWidth(), core.getScreenHeight());
	}
	
	@Override
	public void init() {
		TextureManager.loadTexture("card", "res/img/sample_card.png");
		
		shaderProgram = new ShaderProgram("res/shader/sample.glvs", "res/shader/sample.glfs");
		
		viewPort.setOffsetZ(4f);
		viewPort.showView();
		viewPort.projectView(shaderProgram);
		
		int[] indices = {0, 1, 2, 3};
		float[] vertices = {
			-1f, -1f, 0f, 1f,
			1f, -1f, 1f, 1f,
			1f, 1f, 1f, 0f,
			-1f, 1f, 0f, 0f
		};
		vObject = new TextureObject(TextureManager.getTextureID("card"), vertices, indices);
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
		
		float IVTick = Utility.interpolateLinear((float)vTick, (float)vTick - 3f, (float)ip);
		
		shaderProgram.pushMatrix();
		shaderProgram.multMatrix(MatUtil.translationMat44D(0.0, 0.0, 0.0));
		shaderProgram.multMatrix(MatUtil.rotationMat44D(IVTick, 0.0, 1.0, 0.0));
		shaderProgram.multMatrix(MatUtil.rotationMat44D(32.0, 0.0, 0.0, 1.0));
		shaderProgram.multMatrix(MatUtil.scaleMat44D(0.5, 0.8, 1.0));
		shaderProgram.applyModelView();
		shaderProgram.popMatrix();
		
		shaderProgram.setColor(0.32, 0.54, 0.7, 1.0);
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
