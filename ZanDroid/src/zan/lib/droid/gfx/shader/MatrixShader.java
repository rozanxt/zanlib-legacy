package zan.lib.droid.gfx.shader;

import java.util.ArrayList;

import zan.lib.droid.util.math.Mat44F;
import zan.lib.droid.util.math.MatUtil;

public abstract class MatrixShader extends ShaderProgram {

	protected ArrayList<Mat44F> matrixStack = new ArrayList<Mat44F>();
	protected Mat44F modelMatrix = MatUtil.identityMat44F();
	protected Mat44F viewMatrix = MatUtil.identityMat44F();
	protected Mat44F projectionMatrix = MatUtil.identityMat44F();

	public void setModelMatrix(Mat44F matrix) {modelMatrix.set(matrix);}
	public void setViewMatrix(Mat44F matrix) {viewMatrix.set(matrix);}
	public void setProjectionMatrix(Mat44F matrix) {projectionMatrix.set(matrix);}

	public void applyModelMatrix() {setModelMatrix(getStackMatrix());}
	public void applyViewMatrix() {setViewMatrix(getStackMatrix());}
	public void applyProjectionMatrix() {setProjectionMatrix(getStackMatrix());}

	public void pushMatrix() {
		if (matrixStack.isEmpty()) matrixStack.add(MatUtil.identityMat44F());
		else matrixStack.add(new Mat44F(getStackMatrix()));
	}
	public void popMatrix() {
		if (!matrixStack.isEmpty()) matrixStack.remove(getStackMatrix());
	}

	public void setMatrix(Mat44F matrix) {getStackMatrix().set(matrix);}
	public void multMatrix(Mat44F matrix) {setMatrix(new Mat44F(MatUtil.mult(getStackMatrix(), matrix)));}
	public void loadIdentityMatrix() {setMatrix(MatUtil.identityMat44F());}

	public void translate(float x, float y, float z) {multMatrix(MatUtil.translationMat44F(x, y, z));}
	public void rotate(float angle, float x, float y, float z) {multMatrix(MatUtil.rotationMat44F(angle, x, y, z));}
	public void scale(float x, float y, float z) {multMatrix(MatUtil.scaleMat44F(x, y, z));}

	public void setOrthoProjection(float left, float right, float bottom, float top, float near, float far) {
		setProjectionMatrix(MatUtil.orthoProjectionMatrix(left, right, bottom, top, near, far));
	}
	public void setPerspectiveProjection(float fovy, float aspect, float near, float far) {
		setProjectionMatrix(MatUtil.perspectiveProjectionMatrix(fovy, aspect, near, far));
	}

	public Mat44F getStackMatrix() {
		if (matrixStack.isEmpty()) return MatUtil.identityMat44F();
		return matrixStack.get(matrixStack.size()-1);
	}
	public Mat44F getModelMatrix() {return modelMatrix;}
	public Mat44F getViewMatrix() {return viewMatrix;}
	public Mat44F getProjectionMatrix() {return projectionMatrix;}
	public Mat44F getModelViewMatrix() {return new Mat44F(MatUtil.mult(viewMatrix, modelMatrix));}
	public Mat44F getMVPMatrix() {return new Mat44F(MatUtil.mult(projectionMatrix, getModelViewMatrix()));}

}
