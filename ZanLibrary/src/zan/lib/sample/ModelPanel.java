package zan.lib.sample;

import zan.lib.core.CoreEngine;
import zan.lib.gfx.ShaderProgram;
import zan.lib.gfx.obj.ModelObject;
import zan.lib.gfx.view.ViewPort3D;
import zan.lib.math.matrix.MatUtil;
import zan.lib.panel.BasePanel;

import static zan.lib.input.InputManager.*;
import static org.lwjgl.opengl.GL11.*;

public class ModelPanel extends BasePanel {
	
	private ShaderProgram shaderProgram;
	private ViewPort3D viewPort;
	
	private ModelObject model;
	
	private double rotation;
	
	public ModelPanel(CoreEngine core) {
		viewPort = new ViewPort3D(0, 0, core.getScreenWidth(), core.getScreenHeight());
	}
	
	@Override
	public void init() {
		shaderProgram = new ShaderProgram();
		
		viewPort.setFOVY(120f);
		viewPort.setDepthInterval(100f);
		viewPort.setOffsetZ(4f);
		
		viewPort.showView();
		viewPort.projectView(shaderProgram);
		
		model = new ModelObject("res/obj/sample.obj");
		model.setDrawMode(GL_LINE_STRIP);
		
		rotation = 0.0;
	}
	
	@Override
	public void destroy() {
		model.destroy();
		shaderProgram.destroy();
	}
	
	@Override
	public void update(double time) {
		if (isKeyDown(IM_KEY_RIGHT)) viewPort.setOffsetX(viewPort.getOffsetX()+0.1f);
		if (isKeyDown(IM_KEY_LEFT)) viewPort.setOffsetX(viewPort.getOffsetX()-0.1f);
		if (isKeyDown(IM_KEY_UP)) viewPort.setOffsetY(viewPort.getOffsetY()+0.1f);
		if (isKeyDown(IM_KEY_DOWN)) viewPort.setOffsetY(viewPort.getOffsetY()-0.1f);
		if (isKeyDown(IM_KEY_W)) viewPort.setOffsetZ(viewPort.getOffsetZ()-0.1f);
		if (isKeyDown(IM_KEY_S)) viewPort.setOffsetZ(viewPort.getOffsetZ()+0.1f);
		if (isKeyDown(IM_KEY_A)) rotation-=5.0;
		if (isKeyDown(IM_KEY_D)) rotation+=5.0;
	}
	
	@Override
	public void render(double ip) {
		shaderProgram.bind();
		shaderProgram.pushMatrix();
		viewPort.adjustView(shaderProgram);
		
		shaderProgram.multMatrix(MatUtil.rotationMat44D(rotation, 0.0, 1.0, 0.0));
		shaderProgram.applyModelView();
		
		shaderProgram.setColor(0.0, 1.0, 1.0, 1.0);
		model.render(shaderProgram);
		
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
