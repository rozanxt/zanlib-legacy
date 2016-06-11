package zan.lib.gfx.shader;

import java.util.ArrayList;

import zan.lib.util.math.MatD;
import zan.lib.util.math.MathUtil;

public abstract class MatrixShader extends ShaderProgram {

	protected ArrayList<MatD> matrixStack = new ArrayList<MatD>();
	protected MatD modelMatrix = MathUtil.idMat44D;
	protected MatD viewMatrix = MathUtil.idMat44D;
	protected MatD projectionMatrix = MathUtil.idMat44D;

	public void setModelMatrix(MatD matrix) {modelMatrix = matrix;}
	public void setViewMatrix(MatD matrix) {viewMatrix = matrix;}
	public void setProjectionMatrix(MatD matrix) {projectionMatrix = matrix;}

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

	public void setMatrix(MatD matrix) {if (!matrixStack.isEmpty()) matrixStack.set(matrixStack.size()-1, matrix);}
	public void multMatrix(MatD matrix) {setMatrix(getStackMatrix().mult(matrix));}
	public void loadIdentityMatrix() {setMatrix(MathUtil.idMat44D);}

	public void translate(double x, double y, double z) {multMatrix(MathUtil.translationMat44D(x, y, z));}
	public void rotate(double angle, double x, double y, double z) {multMatrix(MathUtil.rotationMat44D(angle, x, y, z));}
	public void scale(double x, double y, double z) {multMatrix(MathUtil.scaleMat44D(x, y, z));}

	public void setOrthoProjection(double left, double right, double bottom, double top, double near, double far) {
		setProjectionMatrix(MathUtil.orthoProjectionMat44D(left, right, bottom, top, near, far));
	}
	public void setPerspectiveProjection(double fovy, double aspect, double near, double far) {
		setProjectionMatrix(MathUtil.perspectiveProjectionMat44D(fovy, aspect, near, far));
	}

	public MatD getStackMatrix() {
		if (matrixStack.isEmpty()) return MathUtil.idMat44D;
		return matrixStack.get(matrixStack.size()-1);
	}
	public MatD getModelMatrix() {return modelMatrix;}
	public MatD getViewMatrix() {return viewMatrix;}
	public MatD getProjectionMatrix() {return projectionMatrix;}
	public MatD getModelViewMatrix() {return viewMatrix.mult(modelMatrix);}
	public MatD getMVPMatrix() {return projectionMatrix.mult(getModelViewMatrix());}

}
