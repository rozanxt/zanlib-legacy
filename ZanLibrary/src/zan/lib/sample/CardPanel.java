package zan.lib.sample;

import zan.lib.core.CoreEngine;
import zan.lib.gfx.ShaderProgram;
import zan.lib.gfx.TextureManager;
import zan.lib.gfx.obj.TextureObject;
import zan.lib.gfx.view.ViewPort3D;
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
		shaderProgram = new ShaderProgram();
		
		viewPort.setOffsetZ(4.0);
		viewPort.showView();
		viewPort.projectView(shaderProgram);
		
		TextureManager.loadTexture("card", "res/img/sample_card.png");
		
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
		
		double IVTick = Utility.interpolateLinear(vTick, vTick - 3f, ip);
		
		shaderProgram.pushMatrix();
		shaderProgram.translate(0.0, 0.0, 0.0);
		shaderProgram.rotate(IVTick, 0.0, 1.0, 0.0);
		shaderProgram.rotate(32.0, 0.0, 0.0, 1.0);
		shaderProgram.scale(0.5, 0.8, 1.0);
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
