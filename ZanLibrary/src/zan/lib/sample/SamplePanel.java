package zan.lib.sample;

import zan.lib.core.BasePanel;
import zan.lib.gfx.obj.VertexObject;
import zan.lib.gfx.shader.DefaultShader;
import zan.lib.gfx.view.ViewPort2D;
import zan.lib.util.Utility;

public class SamplePanel extends BasePanel {

	private DefaultShader shader;
	private ViewPort2D viewPort;

	private VertexObject object;

	private double tick;

	public SamplePanel(SampleCore core) {
		shader = new DefaultShader();
		viewPort = new ViewPort2D(0, 0, core.getScreenWidth(), core.getScreenHeight());
	}

	@Override
	public void init() {
		shader.init();

		viewPort.showView();
		viewPort.projectView(shader);

		final int[] ind = {0, 1, 2};
		final float[] ver = {
			-0.6f, -0.4f, 1f, 0f, 0f,
			0.6f, -0.4f, 0f, 1f, 0f,
			0f, 0.6f, 0f, 0f, 1f
		};
		object = new VertexObject(ver, ind, 2, 0, 3, 0);

		tick = 0.0;
	}

	@Override
	public void destroy() {
		object.destroy();
		shader.destroy();
	}

	@Override
	public void update(double time) {
		tick += 3.0;
	}

	@Override
	public void render(double ip) {
		shader.bind();
		viewPort.adjustView(shader);

		double iTick = Utility.interpolateLinear(tick - 3.0, tick, ip);

		shader.pushMatrix();
		shader.translate(0.0, 0.0, 0.0);
		shader.rotate(iTick, 0.0, 1.0, 1.0);
		shader.scale(1.0 + 0.2*Math.sin(0.01*iTick), 1.0 + 0.2*Math.sin(0.01*iTick), 1.0);
		shader.applyModelMatrix();
		object.render(shader);
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
