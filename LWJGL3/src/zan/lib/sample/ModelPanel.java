package zan.lib.sample;

import static zan.lib.input.InputManager.*;
import zan.lib.core.FramePanel;
import zan.lib.gfx.Gfx;
import zan.lib.gfx.camera.Camera3D;
import zan.lib.gfx.object.ModelObject;
import zan.lib.gfx.scene.Scene3D;
import zan.lib.math.linalg.Vec3D;

public class ModelPanel extends FramePanel {

	private SampleCore core;

	private Scene3D scene;
	private Camera3D camera;

	private ModelObject model;

	private Vec3D rotation;

	public ModelPanel(SampleCore core) {
		this.core = core;
	}

	@Override
	public void create() {
		Gfx.setClearColor(0.2, 0.2, 0.2, 1.0);

		scene = new Scene3D();
		scene.create();
		scene.enableCullFace(true);
		scene.enableDepthTest(true);

		camera = new Camera3D(core.getScreenWidth(), core.getScreenHeight());
		camera.setPos(0.0, 0.0, 5.0);
		camera.apply(scene);

		model = new ModelObject("res/obj/sample_model.obj");

		rotation = new Vec3D(0.0, 0.0, 0.0);
	}

	@Override
	public void destroy() {
		model.destroy();
		scene.destroy();
	}

	@Override
	public void update(double time) {
		if (isKeyDown(IM_KEY_W)) rotation = rotation.add(new Vec3D(5.0, 0.0, 0.0));
		if (isKeyDown(IM_KEY_S)) rotation = rotation.sub(new Vec3D(5.0, 0.0, 0.0));
		if (isKeyDown(IM_KEY_D)) rotation = rotation.add(new Vec3D(0.0, 5.0, 0.0));
		if (isKeyDown(IM_KEY_A)) rotation = rotation.sub(new Vec3D(0.0, 5.0, 0.0));
	}

	@Override
	public void render(double ip) {
		scene.begin();

		camera.apply(scene);

		scene.setColor(1.0, 1.0, 1.0, 1.0);
		scene.pushMatrix();
		scene.rotate(rotation.y, 0.0, 1.0, 0.0);
		scene.rotate(rotation.x, 1.0, 0.0, 0.0);
		scene.translate(0.0, -1.5, 0.0);
		scene.applyModelMatrix();
		model.render(scene);
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
