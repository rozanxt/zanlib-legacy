package zan.lib.sample;

import static zan.lib.input.InputManager.*;
import zan.lib.core.BasePanel;
import zan.lib.gfx.obj.ModelObject;
import zan.lib.gfx.obj.VertexObject;
import zan.lib.gfx.shader.DefaultShader;
import zan.lib.gfx.texture.TextureManager;
import zan.lib.gfx.view.ViewPort3D;
import zan.lib.util.math.Vec3D;

public class CubePanel extends BasePanel {

	private DefaultShader shader;
	private ViewPort3D viewPort;

	private VertexObject[] cubes;

	private int mode;
	private Vec3D rotation;

	public CubePanel(SampleCore core) {
		shader = new Sample3DShader();
		viewPort = new ViewPort3D(0, 0, core.getScreenWidth(), core.getScreenHeight());
	}

	@Override
	public void init() {
		shader.loadProgram();
		shader.setClearColor(0.2, 0.2, 0.2, 1.0);
		shader.enableDepthTest(true);
		shader.enableCullFace(true);

		viewPort.setOffsetZ(5.0);
		viewPort.showView();
		viewPort.projectView(shader);

		TextureManager.init();
		TextureManager.loadTexture("card", "res/img/sample_card.png");

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
		rotation = new Vec3D(0.0, 0.0, 0.0);
	}

	@Override
	public void destroy() {
		for (int i=0;i<cubes.length;i++) cubes[i].destroy();
		shader.destroy();
		TextureManager.destroy();
	}

	@Override
	public void update(double time) {
		if (isKeyPressed(IM_KEY_SPACE)) {
			mode++;
			if (mode == 2) mode = 0;
		}
		if (isKeyDown(IM_KEY_W)) rotation.addX(5.0);
		if (isKeyDown(IM_KEY_S)) rotation.subX(5.0);
		if (isKeyDown(IM_KEY_D)) rotation.addY(5.0);
		if (isKeyDown(IM_KEY_A)) rotation.subY(5.0);
	}

	@Override
	public void render(double ip) {
		shader.bind();
		viewPort.adjustView(shader);

		shader.pushMatrix();
		shader.rotate(rotation.getY(), 0.0, 1.0, 0.0);
		shader.rotate(rotation.getX(), 1.0, 0.0, 0.0);
		shader.scale(0.8, 0.8, 0.8);
		shader.applyModelMatrix();
		shader.setColor(0.0, 0.5, 0.8, 0.8);
		if (mode == 1) shader.bindTexture(TextureManager.getTextureID("card"));
		cubes[mode].render(shader);
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
