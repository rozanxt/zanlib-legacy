package zan.lib.util.math;

import zan.lib.util.Utility;

public class MatUtil {

	public static Mat22D identityMat22D() {return new Mat22D(true);}
	public static Mat33D identityMat33D() {return new Mat33D(true);}
	public static Mat44D identityMat44D() {return new Mat44D(true);}

	public static MatD elemRowAddMatD(int dim, int dest, int addend, double factor) {
		MatD matrix = new MatD(dim, true);
		matrix.set(dest, addend, factor);
		return matrix;
	}
	public static MatD elemColAddMatD(int dim, int dest, int addend, double factor) {
		MatD matrix = new MatD(dim, true);
		matrix.set(addend, dest, factor);
		return matrix;
	}
	public static MatD elemMultMatD(int dim, int i, double factor) {
		MatD matrix = new MatD(dim, true);
		matrix.set(i, i, factor);
		return matrix;
	}
	public static MatD elemSwapMatD(int dim, int i, int j) {
		MatD matrix = new MatD(dim, true);
		matrix.set(i, i, 0.0);
		matrix.set(i, j, 1.0);
		matrix.set(j, j, 0.0);
		matrix.set(j, i, 1.0);
		return matrix;
	}

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

	public static Mat22D scaleMat22D(double x) {
		Mat22D matrix = identityMat22D();
		matrix.set(0, 0, x);
		return matrix;
	}
	public static Mat33D scaleMat33D(double x, double y) {
		Mat33D matrix = identityMat33D();
		matrix.set(0, 0, x);
		matrix.set(1, 1, y);
		return matrix;
	}
	public static Mat44D scaleMat44D(double x, double y, double z) {
		Mat44D matrix = identityMat44D();
		matrix.set(0, 0, x);
		matrix.set(1, 1, y);
		matrix.set(2, 2, z);
		return matrix;
	}

	public static Mat33D rotationMat33D(double angle) {
		double rad = Math.toRadians(angle);
		double sin = Math.sin(rad);
		double cos = Math.cos(rad);
		Mat33D matrix = identityMat33D();
		matrix.set(0, 0, cos);
		matrix.set(0, 1, -sin);
		matrix.set(1, 0, sin);
		matrix.set(1, 1, cos);
		return matrix;
	}
	public static Mat44D rotationMat44D(double angle, double x, double y, double z) {
		Vec3D axis = new Vec3D(x, y, z);
		axis.normalize();
		double a = axis.getX();
		double b = axis.getY();
		double c = axis.getZ();
		double rad = Math.toRadians(angle);
		double sin = Math.sin(rad);
		double cos = Math.cos(rad);
		Mat44D matrix = identityMat44D();
		matrix.set(0, 0, a*a*(1-cos)+cos);
		matrix.set(0, 1, a*b*(1-cos)-c*sin);
		matrix.set(0, 2, a*c*(1-cos)+b*sin);
		matrix.set(1, 0, b*a*(1-cos)+c*sin);
		matrix.set(1, 1, b*b*(1-cos)+cos);
		matrix.set(1, 2, b*c*(1-cos)-a*sin);
		matrix.set(2, 0, c*a*(1-cos)-b*sin);
		matrix.set(2, 1, c*b*(1-cos)+a*sin);
		matrix.set(2, 2, c*c*(1-cos)+cos);
		return matrix;
	}

	public static Mat44D orthoProjectionMatrix(double left, double right, double bottom, double top, double near, double far) {
		double sx = 2.0/(right-left);
		double sy = 2.0/(top-bottom);
		double sz = -2.0/(far-near);
		double tx = (right+left)/(right-left);
		double ty = (top+bottom)/(top-bottom);
		double tz = (far+near)/(far-near);
		Mat44D matrix = identityMat44D();
		matrix.set(0, 0, sx);
		matrix.set(1, 1, sy);
		matrix.set(2, 2, sz);
		matrix.set(0, 3, -tx);
		matrix.set(1, 3, -ty);
		matrix.set(2, 3, -tz);
		return matrix;
	}

	public static Mat44D perspectiveProjectionMatrix(double fovy, double aspect, double near, double far) {
		double f = 1.0/Math.tan(fovy/2.0);
		double sx = f/aspect;
		double sy = f;
		double sz = (near+far)/(near-far);
		double tz = (2*near*far)/(near-far);
		Mat44D matrix = identityMat44D();
		matrix.set(0, 0, sx);
		matrix.set(1, 1, sy);
		matrix.set(2, 2, sz);
		matrix.set(2, 3, tz);
		matrix.set(3, 2, -1.0);
		matrix.set(3, 3, 0.0);
		return matrix;
	}

	public static MatD appendRowMatrix(MatD top, MatD bottom) {
		MatD result = new MatD(top.rows()+bottom.rows(), Math.max(top.cols(), bottom.cols()), 0.0);
		for (int i=0;i<top.rows();i++) {
			for (int j=0;j<top.cols();j++) {
				result.set(i, j, top.get(i, j));
			}
		}
		for (int i=0;i<bottom.rows();i++) {
			for (int j=0;j<bottom.cols();j++) {
				result.set(top.rows()+i, j, bottom.get(i, j));
			}
		}
		return result;
	}
	public static MatD appendColMatrix(MatD left, MatD right) {
		MatD result = new MatD(Math.max(left.rows(), right.rows()), left.cols()+right.cols(), 0.0);
		for (int i=0;i<left.rows();i++) {
			for (int j=0;j<left.cols();j++) {
				result.set(i, j, left.get(i, j));
			}
		}
		for (int i=0;i<right.rows();i++) {
			for (int j=0;j<right.cols();j++) {
				result.set(i, left.cols()+j, right.get(i, j));
			}
		}
		return result;
	}
	public static MatD appendDiagMatrix(MatD topleft, MatD bottomright) {
		MatD result = new MatD(topleft.rows()+bottomright.rows(), topleft.cols()+bottomright.cols(), 0.0);
		for (int i=0;i<topleft.rows();i++) {
			for (int j=0;j<topleft.cols();j++) {
				result.set(i, j, topleft.get(i, j));
			}
		}
		for (int i=0;i<bottomright.rows();i++) {
			for (int j=0;j<bottomright.cols();j++) {
				result.set(topleft.rows()+i, topleft.cols()+j, bottomright.get(i, j));
			}
		}
		return result;
	}

	public static void add(MatD left, MatD right, MatD result) {
		if (checkSize(left, right, result)) {
			for (int i=0;i<result.size();i++) result.set(i, left.get(i) + right.get(i));
		}
	}
	public static MatD add(MatD left, MatD right) {
		MatD result = new MatD(left.rows(), left.cols());
		add(left, right, result);
		return result;
	}

	public static void sub(MatD left, MatD right, MatD result) {
		if (checkSize(left, right, result)) {
			for (int i=0;i<result.size();i++) result.set(i, left.get(i) - right.get(i));
		}
	}
	public static MatD sub(MatD left, MatD right) {
		MatD result = new MatD(left.rows(), left.cols());
		sub(left, right, result);
		return result;
	}

	public static void mult(MatD left, MatD right, MatD result) {
		if (checkMultipliable(left, right, result)) {
			MatD temp = new MatD(result.rows(), result.cols());
			int rows = left.rows();
			int cols = right.cols();
			int nums = left.cols();
			for (int i=0;i<rows;i++) {
				for (int j=0;j<cols;j++) {
					double sum = 0.0;
					for (int k=0;k<nums;k++) sum += left.get(i, k) * right.get(k, j);
					temp.set(i, j, sum);
				}
			}
			result.set(temp);
		}
	}
	public static MatD mult(MatD left, MatD right) {
		MatD result = new MatD(left.rows(), right.cols());
		if (checkMultipliable(left, right, result)) {
			int rows = left.rows();
			int cols = right.cols();
			int nums = left.cols();
			for (int i=0;i<rows;i++) {
				for (int j=0;j<cols;j++) {
					double sum = 0.0;
					for (int k=0;k<nums;k++) sum += left.get(i, k) * right.get(k, j);
					result.set(i, j, sum);
				}
			}
		}
		return result;
	}

	public static void mult(MatD matrix, VecD vector, VecD result) {
		if (checkMultipliable(matrix, vector, result)) {
			VecD temp = new VecD(result.size());
			for (int i=0;i<matrix.rows();i++) {
				double sum = 0.0;
				for (int j=0;j<matrix.cols();j++) sum += matrix.get(i, j) * vector.get(j);
				temp.set(i, sum);
			}
			result.set(temp);
		}
	}
	public static VecD mult(MatD matrix, VecD vector) {
		VecD result = new VecD(matrix.rows());
		if (checkMultipliable(matrix, vector, result)) {
			for (int i=0;i<matrix.rows();i++) {
				double sum = 0.0;
				for (int j=0;j<matrix.cols();j++) sum += matrix.get(i, j) * vector.get(j);
				result.set(i, sum);
			}
		}
		return result;
	}

	public static double calcDeterminant(MatD matrix) {
		if (matrix.isSquare()) {
			if (matrix.dim() > 1) {
				double det = 0.0;
				for (int i=0;i<matrix.dim();i++) {
					MatD subcut = matrix.getSubCutMatrix(0, i);
					double d = matrix.get(0, i) * calcDeterminant(subcut);
					if (i%2 == 0) det += d;
					else det -= d;
				}
				return det;
			} else {
				return matrix.get(0);
			}
		}
		return 0.0;
	}

	public static MatD calcRowEchelonForm(MatD matrix) {
		MatD result = new MatD(matrix);
		int r = 0;
		int s = 0;
		while (r < result.rows() && s < result.cols()) {
			int k = 0;
			for (int i=r;i<result.rows();i++) if (k == 0 && !Utility.evaluate(result.get(i, s), 0.0)) k = i;
			if (k != 0) {
				result.swapRow(r, k);
				result.multRow(r, 1.0/result.get(r, s));
				for (int i=0;i<result.rows();i++) {
					if (i != r) result.addRow(i, result.getRow(r).scalar(-result.get(i, s)));
				}
				r++;
			}
			s++;
		}
		return result;
	}

	public static MatD calcInverse(MatD matrix) {
		if (matrix.isSquare()) return calcRowEchelonForm(MatUtil.appendColMatrix(matrix, new MatD(matrix.dim(), true))).getSubMatrix(0, matrix.cols(), matrix.rows()-1, 2*matrix.cols()-1);
		return null;
	}

	public static int calcRank(MatD matrix) {
		MatD result = calcRowEchelonForm(matrix);
		int rank = 0;
		for (int i=0;i<result.rows();i++) {
			for (int j=0;j<result.cols();j++) {
				if (!Utility.evaluate(result.get(i, j), 0.0)) {
					rank++;
					break;
				}
			}
		}
		return rank;
	}

	private static boolean checkSize(MatD... matrices) {
		for (int i=0;i<matrices.length-1;i++) {
			if (matrices[i].rows() != matrices[i+1].rows() ||
				matrices[i].cols() != matrices[i+1].cols()) {
				System.err.println("Incompatible matrices!");
				return false;
			}
		}
		return true;
	}
	private static boolean checkMultipliable(MatD left, MatD right, MatD result) {
		if (left.cols() != right.rows() ||
			result.rows() != left.rows() ||
			result.cols() != right.cols()) {
			System.err.println("Incompatible matrices!");
			return false;
		}
		return true;
	}
	private static boolean checkMultipliable(MatD matrix, VecD vector, VecD result) {
		if (matrix.cols() != vector.size() || matrix.rows() != result.size()) {
			System.err.println("Incompatible matrices or vertices!");
			return false;
		}
		return true;
	}

}
