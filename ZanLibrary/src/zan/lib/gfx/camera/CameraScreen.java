package zan.lib.gfx.camera;

import zan.lib.math.linalg.LinAlgUtil;
import zan.lib.math.linalg.Mat44D;
import zan.lib.math.linalg.Vec2D;

public class CameraScreen extends Camera{

	protected Vec2D virtualScreen;

	public CameraScreen(double screenWidth, double screenHeight) {
		super(screenWidth, screenHeight);
		setVirtualScreen(screenWidth, screenHeight);
	}

	public void setVirtualScreen(double screenWidth, double screenHeight) {virtualScreen = new Vec2D(screenWidth, screenHeight);}
	public Vec2D getVirtualScreen() {return virtualScreen;}

	@Override
	public Mat44D getViewMatrix() {
		return LinAlgUtil.idMat44D;
	}

	@Override
	public Mat44D getProjectionMatrix() {
		double clipOffset = 0.5*((virtualScreen.y*screen.x/screen.y)-virtualScreen.x);
		return LinAlgUtil.orthoProjectionMat44D(-clipOffset, -clipOffset+virtualScreen.y*(viewPort.z/viewPort.w), 0.0, virtualScreen.y, -1.0, 1.0);
	}

}
