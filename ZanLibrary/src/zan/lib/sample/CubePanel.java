package zan.lib.sample;

import static zan.lib.input.InputManager.*;
import zan.lib.core.FramePanel;
import zan.lib.gfx.Gfx;
import zan.lib.gfx.camera.Camera3D;
import zan.lib.gfx.object.ModelObject;
import zan.lib.gfx.object.VertexObject;
import zan.lib.gfx.scene.Scene3D;
import zan.lib.gfx.texture.TextureManager;
import zan.lib.math.linalg.LinAlgUtil;
import zan.lib.math.linalg.Vec3D;

public class CubePanel extends FramePanel {

	private SampleCore core;

	private Scene3D scene;
	private Camera3D camera;

	private VertexObject[] cubes;

	private int mode;
	private Vec3D rotation;

	public CubePanel(SampleCore core) {
		this.core = core;
	}

	@Override
	public void create() {
		Gfx.setClearColor(0.2, 0.2, 0.2, 1.0);

		TextureManager.create();
		TextureManager.loadTexture("card", "res/img/sample_card.png");

		scene = new Scene3D();
		scene.create();
		scene.enableDepthTest(true);
		scene.enableCullFace(true);

		camera = new Camera3D(core.getScreenWidth(), core.getScreenHeight());
		camera.setPos(0.0, 0.0, 5.0);
		camera.apply(scene);

		final int[] ind = {
			0, 1, 3, 1, 2, 3,
			4, 5, 7, 5, 6, 7,
			8, 9, 11, 9, 10, 11,
			12, 13, 15, 13, 14, 15,
			16, 17, 19, 17, 18, 19,
			20, 21, 23, 21, 22, 23
		};
		final float[] ver = {
			// Front Face
			-1f, -1f, 1f, 0f, 0f, 1f, 1f, 0f, 0f, 1f, 0f, 1f,
			1f, -1f, 1f, 0f, 0f, 1f, 1f, 0f, 0f, 1f, 1f, 1f,
			1f, 1f, 1f, 0f, 0f, 1f, 1f, 0f, 0f, 1f, 1f, 0f,
			-1f, 1f, 1f, 0f, 0f, 1f, 1f, 0f, 0f, 1f, 0f, 0f,
			// Right Face
			1f, -1f, 1f, 1f, 0f, 0f, 0f, 1f, 0f, 1f, 0f, 1f,
			1f, -1f, -1f, 1f, 0f, 0f, 0f, 1f, 0f, 1f, 1f, 1f,
			1f, 1f, -1f, 1f, 0f, 0f, 0f, 1f, 0f, 1f, 1f, 0f,
			1f, 1f, 1f, 1f, 0f, 0f, 0f, 1f, 0f, 1f, 0f, 0f,
			// Back Face
			1f, -1f, -1f, 0f, 0f, -1f, 1f, 0f, 0f, 1f, 0f, 1f,
			-1f, -1f, -1f, 0f, 0f, -1f, 1f, 0f, 0f, 1f, 1f, 1f,
			-1f, 1f, -1f, 0f, 0f, -1f, 1f, 0f, 0f, 1f, 1f, 0f,
			1f, 1f, -1f, 0f, 0f, -1f, 1f, 0f, 0f, 1f, 0f, 0f,
			// Left Face
			-1f, -1f, -1f, -1f, 0f, 0f, 0f, 1f, 0f, 1f, 0f, 1f,
			-1f, -1f, 1f, -1f, 0f, 0f, 0f, 1f, 0f, 1f, 1f, 1f,
			-1f, 1f, 1f, -1f, 0f, 0f, 0f, 1f, 0f, 1f, 1f, 0f,
			-1f, 1f, -1f, -1f, 0f, 0f, 0f, 1f, 0f, 1f, 0f, 0f,
			// Top Face
			-1f, 1f, 1f, 0f, 1f, 0f, 1f, 1f, 1f, 1f, 0f, 1f,
			1f, 1f, 1f, 0f, 1f, 0f, 1f, 1f, 1f, 1f, 1f, 1f,
			1f, 1f, -1f, 0f, 1f, 0f, 1f, 1f, 1f, 1f, 1f, 0f,
			-1f, 1f, -1f, 0f, 1f, 0f, 1f, 1f, 1f, 1f, 0f, 0f,
			// Bottom Face
			-1f, -1f, -1f, 0f, -1f, 0f, 1f, 1f, 1f, 1f, 0f, 1f,
			1f, -1f, -1f, 0f, -1f, 0f, 1f, 1f, 1f, 1f, 1f, 1f,
			1f, -1f, 1f, 0f, -1f, 0f, 1f, 1f, 1f, 1f, 1f, 0f,
			-1f, -1f, 1f, 0f, -1f, 0f, 1f, 1f, 1f, 1f, 0f, 0f
		};

		cubes = new VertexObject[2];
		cubes[0] = new ModelObject("res/obj/sample_cube.obj");
		cubes[1] = new VertexObject(ver, ind, 3, 3, 4, 2);

		mode = 0;
		rotation = LinAlgUtil.zeroVec3D;
	}

	@Override
	public void destroy() {
		for (int i=0;i<cubes.length;i++) cubes[i].destroy();
		scene.destroy();
		TextureManager.destroy();
	}

	@Override
	public void update(double time) {
		if (isKeyPressed(IM_KEY_SPACE)) {
			mode++;
			if (mode == 2) mode = 0;
		}
		if (isKeyDown(IM_KEY_W)) rotation = rotation.add(new Vec3D(5.0, 0.0, 0.0));
		if (isKeyDown(IM_KEY_S)) rotation = rotation.sub(new Vec3D(5.0, 0.0, 0.0));
		if (isKeyDown(IM_KEY_D)) rotation = rotation.add(new Vec3D(0.0, 5.0, 0.0));
		if (isKeyDown(IM_KEY_A)) rotation = rotation.sub(new Vec3D(0.0, 5.0, 0.0));
	}

	@Override
	public void render(double ip) {
		scene.begin();

		camera.apply(scene);

		scene.pushMatrix();
		scene.rotate(rotation.y, 0.0, 1.0, 0.0);
		scene.rotate(rotation.x, 1.0, 0.0, 0.0);
		scene.scale(0.8, 0.8, 0.8);
		scene.applyModelMatrix();
		scene.setColor(0.0, 0.5, 0.8, 0.8);
		if (mode == 1) scene.bindTexture(TextureManager.getTextureID("card"));
		cubes[mode].render(scene);
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
