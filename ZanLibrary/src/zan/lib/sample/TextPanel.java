package zan.lib.sample;

import zan.lib.core.CoreEngine;
import zan.lib.gfx.ShaderProgram;
import zan.lib.gfx.text.TextManager;
import zan.lib.gfx.view.ViewPort2D;
import zan.lib.panel.BasePanel;
import zan.lib.res.ResourceReader;

public class TextPanel extends BasePanel {
	
	private ShaderProgram shaderProgram;
	private ViewPort2D viewPort;
	
	public TextPanel(CoreEngine core) {
		viewPort = new ViewPort2D(0, 0, core.getScreenWidth(), core.getScreenHeight());
	}
	
	@Override
	public void init() {
		shaderProgram = new ShaderProgram();
		
		viewPort.showView();
		viewPort.projectView(shaderProgram);
		
		TextManager.loadFontFile(new ResourceReader("res/font/fonts.res").getData().getNode("defont"));
	}
	
	@Override
	public void destroy() {
		shaderProgram.destroy();
	}
	
	@Override
	public void update(double time) {
		
	}
	
	@Override
	public void render(double ip) {
		shaderProgram.bind();
		shaderProgram.pushMatrix();
		viewPort.adjustView(shaderProgram);
		
		shaderProgram.pushMatrix();
		shaderProgram.translate(-0.65, -0.22, 0.0);
		shaderProgram.rotate(10.0, 0.0, 0.0, 1.0);
		shaderProgram.scale(0.2, 0.3, 1.0);
		shaderProgram.setColor(0.0, 0.5, 0.8, 0.8);
		TextManager.renderText(shaderProgram, "ZanLibrary", "defont");
		shaderProgram.popMatrix();
		
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
