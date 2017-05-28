package zan.lib.sample;

import zan.lib.core.FramePanel;
import zan.lib.gfx.camera.Camera2D;
import zan.lib.gfx.object.VertexObject;
import zan.lib.gfx.scene.Scene2D;
import zan.lib.util.Utility;

public class SamplePanel extends FramePanel {

	private SampleCore core;

	private Scene2D scene;
	private Camera2D camera;

	private VertexObject object;

	private double tick;

	public SamplePanel(SampleCore core) {
		this.core = core;
	}

	@Override
	public void create() {
		scene = new Scene2D();
		scene.create();

		camera = new Camera2D(core.getScreenWidth(), core.getScreenHeight());
		camera.apply(scene);

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
		scene.destroy();
	}

	@Override
	public void update(double time) {
		tick += 3.6;
	}

	@Override
	public void render(double ip) {
		scene.begin();

		double iTick = Utility.interpolateLinear(tick - 3.0, tick, ip);

		scene.pushMatrix();
		scene.translate(0.0, 0.0, 0.0);
		scene.rotate(iTick, 0.0, 1.0, 1.0);
		scene.scale(1.0 + 0.2*Math.sin(0.01*iTick), 1.0 + 0.2*Math.sin(0.01*iTick), 1.0);
		scene.applyModelMatrix();
		object.render(scene);
		scene.popMatrix();

		scene.end();
	}

	@Override
	public void onScreenResize(int width, int height) {
		camera.setScreen(width, height);
		camera.setViewPort(0, 0, width, height);
		camera.apply(scene);
	}

}
