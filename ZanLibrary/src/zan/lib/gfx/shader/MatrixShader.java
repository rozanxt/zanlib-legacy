package zan.lib.gfx.shader;

import java.util.ArrayList;

import zan.lib.util.math.Mat44D;
import zan.lib.util.math.MathUtil;

public abstract class MatrixShader extends ShaderProgram {

	protected ArrayList<Mat44D> matrixStack = new ArrayList<Mat44D>();
	protected Mat44D modelMatrix = MathUtil.idMat44D;
	protected Mat44D viewMatrix = MathUtil.idMat44D;
	protected Mat44D projectionMatrix = MathUtil.idMat44D;

	public void setModelMatrix(Mat44D matrix) {modelMatrix = matrix;}
	public void setViewMatrix(Mat44D matrix) {viewMatrix = matrix;}
	public void setProjectionMatrix(Mat44D matrix) {projectionMatrix = matrix;}

	public void applyModelMatrix() {setModelMatrix(getStackMatrix());}
	public void applyViewMatrix() {setViewMatrix(getStackMatrix());}
	public void applyProjectionMatrix() {setProjectionMatrix(getStackMatrix());}

	public void pushMatrix() {
		if (matrixStack.isEmpty()) matrixStack.add(MathUtil.idMat44D);
		else matrixStack.add(getStackMatrix());
	}
	public void popMatrix() {
		if (!matrixStack.isEmpty()) matrixStack.remove(getStackMatrix());
	}

	public void setMatrix(Mat44D matrix) {matrixStack.set(matrixStack.size()-1, matrix);}
	public void multMatrix(Mat44D matrix) {setMatrix(getStackMatrix().mult(matrix));}
	public void loadIdentityMatrix() {setMatrix(MathUtil.idMat44D);}

	public void translate(double x, double y, double z) {multMatrix(MathUtil.translationMat44D(x, y, z));}
	public void rotate(double angle, double x, double y, double z) {multMatrix(MathUtil.rotationMat44D(angle, x, y, z));}
	public void scale(double x, double y, double z) {multMatrix(MathUtil.scaleMat44D(x, y, z));}

	public void setOrthoProjection(double left, double right, double bottom, double top, double near, double far) {
		setProjectionMatrix(MathUtil.orthoProjectionMatrix(left, right, bottom, top, near, far));
	}
	public void setPerspectiveProjection(double fovy, double aspect, double near, double far) {
		setProjectionMatrix(MathUtil.perspectiveProjectionMatrix(fovy, aspect, near, far));
	}

	public Mat44D getStackMatrix() {
		if (matrixStack.isEmpty()) return MathUtil.idMat44D;
		return matrixStack.get(matrixStack.size()-1);
	}
	public Mat44D getModelMatrix() {return modelMatrix;}
	public Mat44D getViewMatrix() {return viewMatrix;}
	public Mat44D getProjectionMatrix() {return projectionMatrix;}
	public Mat44D getModelViewMatrix() {return viewMatrix.mult(modelMatrix);}
	public Mat44D getMVPMatrix() {return projectionMatrix.mult(getModelViewMatrix());}

}
