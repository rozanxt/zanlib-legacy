package zan.lib.gfx.shader;

import java.util.ArrayList;

import zan.lib.util.math.Mat44D;
import zan.lib.util.math.MatUtil;

public abstract class MatrixShader extends ShaderProgram {

	protected ArrayList<Mat44D> matrixStack = new ArrayList<Mat44D>();
	protected Mat44D modelMatrix = MatUtil.identityMat44D();
	protected Mat44D viewMatrix = MatUtil.identityMat44D();
	protected Mat44D projectionMatrix = MatUtil.identityMat44D();

	public void setModelMatrix(Mat44D matrix) {modelMatrix.set(matrix);}
	public void setViewMatrix(Mat44D matrix) {viewMatrix.set(matrix);}
	public void setProjectionMatrix(Mat44D matrix) {projectionMatrix.set(matrix);}

	public void applyModelMatrix() {setModelMatrix(getStackMatrix());}
	public void applyViewMatrix() {setViewMatrix(getStackMatrix());}
	public void applyProjectionMatrix() {setProjectionMatrix(getStackMatrix());}

	public void pushMatrix() {
		if (matrixStack.isEmpty()) matrixStack.add(MatUtil.identityMat44D());
		else matrixStack.add(new Mat44D(getStackMatrix()));
	}
	public void popMatrix() {
		if (!matrixStack.isEmpty()) matrixStack.remove(getStackMatrix());
	}

	public void setMatrix(Mat44D matrix) {getStackMatrix().set(matrix);}
	public void multMatrix(Mat44D matrix) {setMatrix(new Mat44D(MatUtil.mult(getStackMatrix(), matrix)));}
	public void loadIdentityMatrix() {setMatrix(MatUtil.identityMat44D());}

	public void translate(double x, double y, double z) {multMatrix(MatUtil.translationMat44D(x, y, z));}
	public void rotate(double angle, double x, double y, double z) {multMatrix(MatUtil.rotationMat44D(angle, x, y, z));}
	public void scale(double x, double y, double z) {multMatrix(MatUtil.scaleMat44D(x, y, z));}

	public void setOrthoProjection(double left, double right, double bottom, double top, double near, double far) {
		setProjectionMatrix(MatUtil.orthoProjectionMatrix(left, right, bottom, top, near, far));
	}
	public void setPerspectiveProjection(double fovy, double aspect, double near, double far) {
		setProjectionMatrix(MatUtil.perspectiveProjectionMatrix(fovy, aspect, near, far));
	}

	public Mat44D getStackMatrix() {
		if (matrixStack.isEmpty()) return MatUtil.identityMat44D();
		return matrixStack.get(matrixStack.size()-1);
	}
	public Mat44D getModelMatrix() {return modelMatrix;}
	public Mat44D getViewMatrix() {return viewMatrix;}
	public Mat44D getProjectionMatrix() {return projectionMatrix;}
	public Mat44D getModelViewMatrix() {return new Mat44D(MatUtil.mult(viewMatrix, modelMatrix));}
	public Mat44D getMVPMatrix() {return new Mat44D(MatUtil.mult(projectionMatrix, getModelViewMatrix()));}

}
