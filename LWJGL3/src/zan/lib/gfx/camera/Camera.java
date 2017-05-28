package zan.lib.gfx.camera;

import zan.lib.gfx.Gfx;
import zan.lib.gfx.scene.DefaultScene;
import zan.lib.math.linalg.Mat44D;
import zan.lib.math.linalg.Vec2D;
import zan.lib.math.linalg.Vec4D;

public abstract class Camera {

	protected Vec2D screen;
	protected Vec4D viewPort;

	public Camera(double screenWidth, double screenHeight) {
		screen = new Vec2D(screenWidth, screenHeight);
		viewPort = new Vec4D(0.0, 0.0, screenWidth, screenHeight);
	}

	public void applyViewPort() {Gfx.setViewPort(viewPort.x, viewPort.y, viewPort.z, viewPort.w);}
	public void applyView(DefaultScene sc) {sc.setViewMatrix(getViewMatrix());}
	public void applyProjection(DefaultScene sc) {sc.setProjectionMatrix(getProjectionMatrix());}
	public void apply(DefaultScene sc) {
		applyViewPort();
		applyView(sc);
		applyProjection(sc);
	}

	public void setScreen(double w, double h) {screen = new Vec2D(w, h);}
	public void setViewPort(double x, double y, double w, double h) {viewPort = new Vec4D(x, y, w, h);}

	public Vec2D getScreen() {return screen;}
	public Vec4D getViewPort() {return viewPort;}

	public abstract Mat44D getViewMatrix();
	public abstract Mat44D getProjectionMatrix();

}
