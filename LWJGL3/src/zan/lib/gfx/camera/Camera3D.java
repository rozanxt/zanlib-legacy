package zan.lib.gfx.camera;

import zan.lib.math.linalg.LinAlgUtil;
import zan.lib.math.linalg.Mat44D;
import zan.lib.math.linalg.Vec3D;

public class Camera3D extends Camera {

	protected Vec3D pos = LinAlgUtil.zeroVec3D;
	protected Vec3D front = LinAlgUtil.unitZVec3D.negate();
	protected Vec3D up = LinAlgUtil.unitYVec3D;

	protected double fovy = 120.0;
	protected double nearClip = 0.1;
	protected double farClip = 100.0;

	public Camera3D(double screenWidth, double screenHeight) {super(screenWidth, screenHeight);}

	public void setPos(double x, double y, double z) {pos = new Vec3D(x, y, z);}
	public void setFront(double x, double y, double z) {front = new Vec3D(x, y, z);}
	public void setUp(double x, double y, double z) {up = new Vec3D(x, y, z);}

	public Vec3D getPos() {return pos;}
	public Vec3D getFront() {return front;}
	public Vec3D getUp() {return up;}

	@Override
	public Mat44D getViewMatrix() {
		//Mat44D viewMatrix = LinAlgUtil.orientationMat44D(front, up);
		//viewMatrix = viewMatrix.mult(LinAlgUtil.translationMat44D(-pos.x, -pos.y, -pos.z));
		return LinAlgUtil.translationMat44D(-pos.x, -pos.y, -pos.z);//viewMatrix;
	}

	@Override
	public Mat44D getProjectionMatrix() {
		return LinAlgUtil.perspectiveProjectionMat44D(fovy, viewPort.z / viewPort.w, nearClip, farClip);
	}

}
