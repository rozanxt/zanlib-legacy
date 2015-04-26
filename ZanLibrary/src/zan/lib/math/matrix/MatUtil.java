package zan.lib.math.matrix;

import zan.lib.math.vector.Vec3D;

public class MatUtil {
	
	// Identity matrix
	
	public static Mat22D identityMat22D() {
		Mat22D matrix = new Mat22D();
		matrix.loadIdentity();
		return matrix;
	}
	public static Mat33D identityMat33D() {
		Mat33D matrix = new Mat33D();
		matrix.loadIdentity();
		return matrix;
	}
	public static Mat44D identityMat44D() {
		Mat44D matrix = new Mat44D();
		matrix.loadIdentity();
		return matrix;
	}
	
	// Translation matrix
	
	public static Mat22D translationMat22D(double x) {
		Mat22D matrix = identityMat22D();
		matrix.set(0, 1, x);
		return matrix;
	}
	public static Mat33D translationMat33D(double x, double y) {
		Mat33D matrix = identityMat33D();
		matrix.set(0, 2, x);
		matrix.set(1, 2, y);
		return matrix;
	}
	public static Mat44D translationMat44D(double x, double y, double z) {
		Mat44D matrix = identityMat44D();
		matrix.set(0, 3, x);
		matrix.set(1, 3, y);
		matrix.set(2, 3, z);
		return matrix;
	}
	
	// Scale matrix
	
	public static Mat22D scaleMat22D(double x, double y) {
		Mat22D matrix = identityMat22D();
		matrix.set(0, 0, x);
		matrix.set(1, 1, y);
		return matrix;
	}
	public static Mat33D scaleMat33D(double x, double y, double z) {
		Mat33D matrix = identityMat33D();
		matrix.set(0, 0, x);
		matrix.set(1, 1, y);
		matrix.set(2, 2, z);
		return matrix;
	}
	public static Mat44D scaleMat44D(double x, double y, double z) {
		Mat44D matrix = identityMat44D();
		matrix.set(0, 0, x);
		matrix.set(1, 1, y);
		matrix.set(2, 2, z);
		return matrix;
	}
	
	// Rotation matrix
	
	public static Mat22D rotationMat22D(double angle) {
		double rad = Math.toRadians(angle);
		double sin = Math.sin(rad);
		double cos = Math.cos(rad);
		Mat22D matrix = identityMat22D();
		matrix.set(0, 0, cos);
		matrix.set(0, 1, -sin);
		matrix.set(1, 0, sin);
		matrix.set(1, 1, cos);
		return matrix;
	}
	public static Mat33D rotationMat33D(double angle, double x, double y, double z) {
		Mat33D matrix = identityMat33D();
		calcRotationMatrix(matrix, angle, x, y, z);
		return matrix;
	}
	public static Mat44D rotationMat44D(double angle, double x, double y, double z) {
		Mat44D matrix = identityMat44D();
		calcRotationMatrix(matrix, angle, x, y, z);
		return matrix;
	}
	private static void calcRotationMatrix(SquareMatD matrix, double angle, double x, double y, double z) {
		if (matrix.rows() < 3) return;
		Vec3D axis = new Vec3D(x, y, z);
		axis.normalize();
		double a = axis.getX();
		double b = axis.getY();
		double c = axis.getZ();
		double rad = Math.toRadians(angle);
		double sin = Math.sin(rad);
		double cos = Math.cos(rad);
		matrix.set(0, 0, a*a*(1-cos)+cos);
		matrix.set(0, 1, a*b*(1-cos)-c*sin);
		matrix.set(0, 2, a*c*(1-cos)+b*sin);
		matrix.set(1, 0, b*a*(1-cos)+c*sin);
		matrix.set(1, 1, b*b*(1-cos)+cos);
		matrix.set(1, 2, b*c*(1-cos)-a*sin);
		matrix.set(2, 0, c*a*(1-cos)-b*sin);
		matrix.set(2, 1, c*b*(1-cos)+a*sin);
		matrix.set(2, 2, c*c*(1-cos)+cos);
	}
	
	// Orthogonal Projection Matrix
	
	public static Mat44D orthoProjectionMatrix(double left, double right, double bottom, double top, double near, double far) {
		Mat44D matrix = identityMat44D();
		double sx = 2.0/(right-left);
		double sy = 2.0/(top-bottom);
		double sz = -2.0/(far-near);
		double tx = (right+left)/(right-left);
		double ty = (top+bottom)/(top-bottom);
		double tz = (far+near)/(far-near);
		matrix.set(0, 0, sx);
		matrix.set(1, 1, sy);
		matrix.set(2, 2, sz);
		matrix.set(0, 3, -tx);
		matrix.set(1, 3, -ty);
		matrix.set(2, 3, -tz);
		return matrix;
	}
	
	// Perspective Projection Matrix
	
	public static Mat44D perspectiveProjectionMatrix(double fovy, double aspect, double near, double far) {
		Mat44D matrix = identityMat44D();
		double f = 1.0/Math.tan(fovy/2.0);
		double sx = f/aspect;
		double sy = f;
		double sz = (near+far)/(near-far);
		double tz = (2*near*far)/(near-far);
		matrix.set(0, 0, sx);
		matrix.set(1, 1, sy);
		matrix.set(2, 2, sz);
		matrix.set(2, 3, tz);
		matrix.set(3, 2, -1.0);
		matrix.set(3, 3, 0.0);
		return matrix;
	}
	
	// Transpose
	
	public static void transpose(MatD origin, MatD result) {
		if (!checkTransposable(origin, result)) return;
		for (int i=0;i<origin.rows();i++) {
			for (int j=0;j<origin.cols();j++) {
				result.set(j, i, origin.get(i, j));
			}
		}
	}
	
	// Addition
	
	public static void add(MatD left, MatD right, MatD result) {
		if (!checkSize(left, right, result)) return;
		for (int i=0;i<left.size();i++) result.set(i, left.get(i) + right.get(i));
	}
	
	// Subtraction
	
	public static void sub(MatD left, MatD right, MatD result) {
		if (!checkSize(left, right, result)) return;
		for (int i=0;i<left.size();i++) result.set(i, left.get(i) - right.get(i));
	}
	
	// Matrix multiplication
	
	public static void mult(MatD left, MatD right, MatD result) {
		if (!checkMultipliable(left, right, result)) return;
		int row = left.rows();
		int col = right.cols();
		for (int i=0;i<row;i++) {
			for (int j=0;j<col;j++) {
				double sum = 0.0;
				for (int k=0;k<col;k++) sum += left.get(i, k) * right.get(k, j);
				result.set(i, j, sum);
			}
		}
	}
	
	// Utilities
	
	private static boolean checkSize(MatD... matrices) {
		for (int i=0;i<matrices.length-1;i++) {
			if (matrices[i].rows() != matrices[i+1].rows() ||
				matrices[i].cols() != matrices[i+1].cols()) {
				System.out.println("Incompatible matrices!");
				return false;
			}
		}
		return true;
	}
	private static boolean checkTransposable(MatD origin, MatD result) {
		if (origin.rows() != result.cols() ||
			origin.cols() != result.rows()) {
			System.out.println("Incompatible matrices!");
			return false;
		}
		return true;
	}
	private static boolean checkMultipliable(MatD left, MatD right) {
		if (left.cols() != right.rows()) {
			System.out.println("Incompatible matrices!");
			return false;
		}
		return true;
	}
	private static boolean checkMultipliable(MatD left, MatD right, MatD result) {
		if (!checkMultipliable(left, right)) return false;
		if (result.rows() != left.rows() ||
			result.cols() != right.cols()) {
			System.out.println("Incompatible matrices!");
			return false;
		}
		return true;
	}
	
}
