package zan.lib.gfx.camera;

import zan.lib.math.linalg.LinAlgUtil;
import zan.lib.math.linalg.Mat44D;
import zan.lib.math.linalg.Vec2D;

public class Camera2D extends Camera{

	protected Vec2D origin = new Vec2D(0.5, 0.5);
	protected Vec2D offset = LinAlgUtil.zeroVec2D;

	protected double heightInterval = 2.0;
	protected double depthInterval = 2.0;
	protected double zoomScale = 1.0;

	public Camera2D(double screenWidth, double screenHeight) {super(screenWidth, screenHeight);}

	public void setOrigin(double x, double y) {origin = new Vec2D(x, y);}
	public void setOffset(double x, double y) {offset = new Vec2D(x, y);}
	public void setHeightInterval(double heightInterval) {this.heightInterval = heightInterval;}
	public void setDepthInterval(double depthInterval) {this.depthInterval = depthInterval;}
	public void setZoomScale(double zoomScale) {this.zoomScale = zoomScale;}

	public Vec2D getOrigin() {return origin;}
	public Vec2D getOffset() {return offset;}
	public double getHeightInterval() {return heightInterval;}
	public double getDepthInterval() {return depthInterval;}
	public double getZoomScale() {return zoomScale;}

	public double getScreenToVirtualX(double x) {
		return (x-viewPort.x-origin.x*viewPort.z)*(heightInterval/viewPort.w)+offset.x;
	}
	public double getScreenToVirtualY(double y) {
		return ((screen.y-y)-viewPort.y-origin.y*viewPort.w)*(heightInterval/viewPort.w)+offset.y;
	}
	public double getVirtualToScreenX(double x) {
		return (x-offset.x)*(viewPort.w/heightInterval)+origin.x*viewPort.z+viewPort.x;
	}
	public double getVirtualToScreenY(double y) {
		return screen.y-((y-offset.y)*(viewPort.w/heightInterval)+origin.y*viewPort.w+viewPort.y);
	}

	@Override
	public Mat44D getViewMatrix() {
		double viewPortRatio = viewPort.z / viewPort.w;
		Mat44D viewMatrix = LinAlgUtil.translationMat44D(2.0*(origin.x-0.5)*viewPortRatio, 2.0*(origin.y-0.5), 0.0);
		viewMatrix = viewMatrix.mult(LinAlgUtil.scaleMat44D(zoomScale, zoomScale, 0.0));
		viewMatrix = viewMatrix.mult(LinAlgUtil.translationMat44D(-offset.x, -offset.y, 0.0));
		return viewMatrix;
	}

	@Override
	public Mat44D getProjectionMatrix() {
		double viewPortRatio = viewPort.z / viewPort.w;
		return LinAlgUtil.orthoProjectionMat44D(-viewPortRatio, viewPortRatio, -1.0, 1.0, -0.5*depthInterval, 0.5*depthInterval);
	}

}
