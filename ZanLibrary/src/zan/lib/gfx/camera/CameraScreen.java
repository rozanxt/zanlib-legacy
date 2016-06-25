package zan.lib.gfx.camera;

import zan.lib.math.linalg.LinAlgUtil;
import zan.lib.math.linalg.Mat44D;

public class CameraScreen extends Camera{

	public CameraScreen(double screenWidth, double screenHeight) {super(screenWidth, screenHeight);}

	@Override
	public Mat44D getViewMatrix() {
		return LinAlgUtil.idMat44D;
	}

	@Override
	public Mat44D getProjectionMatrix() {
		return LinAlgUtil.orthoProjectionMat44D(0.0, screen.y*(viewPort.z/viewPort.w), 0.0, screen.y, -1.0, 1.0);
	}

}
