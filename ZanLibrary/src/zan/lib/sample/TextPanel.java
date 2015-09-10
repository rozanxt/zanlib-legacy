package zan.lib.sample;

import zan.lib.core.BasePanel;
import zan.lib.gfx.shader.DefaultShader;
import zan.lib.gfx.text.TextManager;
import zan.lib.gfx.texture.TextureManager;
import zan.lib.gfx.view.ViewPort2D;

public class TextPanel extends BasePanel {

	private DefaultShader shader;
	private ViewPort2D viewPort;

	public TextPanel(SampleCore core) {
		shader = new DefaultShader();
		viewPort = new ViewPort2D(0, 0, core.getScreenWidth(), core.getScreenHeight());
	}

	@Override
	public void init() {
		shader.loadProgram();
		shader.enableBlend(true);

		viewPort.showView();
		viewPort.projectView(shader);

		TextureManager.init();
		TextManager.init();
		TextManager.loadFontFile("res/font/fonts.res");
	}

	@Override
	public void destroy() {
		shader.destroy();
		TextManager.destroy();
		TextureManager.destroy();
	}

	@Override
	public void update(double time) {

	}

	@Override
	public void render(double ip) {
		shader.bind();
		viewPort.adjustView(shader);

		shader.pushMatrix();
		shader.translate(-0.3, 0.0, 0.0);
		shader.scale(0.1, 0.1, 1.0);
		shader.applyModelMatrix();
		shader.setColor(0.0, 0.5, 0.8, 0.8);
		TextManager.renderText(shader, "ZanLibrary", "defont");
		shader.popMatrix();

		shader.unbind();
	}

	@Override
	public void onScreenResize(int width, int height) {
		shader.bindState();
		viewPort.setScreenSize(width, height);
		viewPort.setViewPort(0, 0, width, height);
		viewPort.showView();
		viewPort.projectView(shader);
	}

}
