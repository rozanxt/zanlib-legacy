package zan.lib.sample;

import zan.lib.core.FramePanel;
import zan.lib.gfx.object.VertexObject;
import zan.lib.gfx.shader.DefaultShader;
import zan.lib.gfx.view.ViewPort2D;
import zan.lib.util.Utility;

public class SamplePanel extends FramePanel {

	private SampleCore core;

	private DefaultShader shader;
	private ViewPort2D viewPort;

	private VertexObject object;

	private double tick;

	public SamplePanel(SampleCore core) {
		this.core = core;
	}

	@Override
	public void create() {
		shader = new DefaultShader();
		shader.loadProgram();

		viewPort = new ViewPort2D(0, 0, core.getScreenWidth(), core.getScreenHeight());
		viewPort.showView();
		viewPort.projectView(shader);

		final int[] indices = {0, 1, 2};
		final float[] vertices = {
			-0.6f, -0.4f, 1.0f, 0.0f, 0.0f,
			0.6f, -0.4f, 0.0f, 1.0f, 0.0f,
			0.0f, 0.6f, 0.0f, 0.0f, 1.0f
		};

		object = new VertexObject(vertices, indices);
		object.setAttributes(2, 0, 3, 0);
		object.setDrawMode(VertexObject.GL_TRIANGLES);

		tick = 0.0;
	}

	@Override
	public void destroy() {
		object.destroy();
		shader.destroy();
	}

	@Override
	public void update(double time) {
		tick += 3.6;
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
