package zan.lib.droid.util.math;

import android.util.Log;

import zan.lib.droid.util.Utility;

public class MatUtil {

	public static Mat22F identityMat22F() {return new Mat22F(true);}
	public static Mat33F identityMat33F() {return new Mat33F(true);}
	public static Mat44F identityMat44F() {return new Mat44F(true);}

	public static MatF elemRowAddMatD(int dim, int dest, int addend, float factor) {
		MatF matrix = new MatF(dim, true);
		matrix.set(dest, addend, factor);
		return matrix;
	}
	public static MatF elemColAddMatD(int dim, int dest, int addend, float factor) {
		MatF matrix = new MatF(dim, true);
		matrix.set(addend, dest, factor);
		return matrix;
	}
	public static MatF elemMultMatD(int dim, int i, float factor) {
		MatF matrix = new MatF(dim, true);
		matrix.set(i, i, factor);
		return matrix;
	}
	public static MatF elemSwapMatD(int dim, int i, int j) {
		MatF matrix = new MatF(dim, true);
		matrix.set(i, i, 0f);
		matrix.set(i, j, 1f);
		matrix.set(j, j, 0f);
		matrix.set(j, i, 1f);
		return matrix;
	}

	public static Mat22F translationMat22F(float x) {
		Mat22F matrix = identityMat22F();
		matrix.set(0, 1, x);
		return matrix;
	}
	public static Mat33F translationMat33F(float x, float y) {
		Mat33F matrix = identityMat33F();
		matrix.set(0, 2, x);
		matrix.set(1, 2, y);
		return matrix;
	}
	public static Mat44F translationMat44F(float x, float y, float z) {
		Mat44F matrix = identityMat44F();
		matrix.set(0, 3, x);
		matrix.set(1, 3, y);
		matrix.set(2, 3, z);
		return matrix;
	}

	public static Mat22F scaleMat22F(float x) {
		Mat22F matrix = identityMat22F();
		matrix.set(0, 0, x);
		return matrix;
	}
	public static Mat33F scaleMat33F(float x, float y) {
		Mat33F matrix = identityMat33F();
		matrix.set(0, 0, x);
		matrix.set(1, 1, y);
		return matrix;
	}
	public static Mat44F scaleMat44F(float x, float y, float z) {
		Mat44F matrix = identityMat44F();
		matrix.set(0, 0, x);
		matrix.set(1, 1, y);
		matrix.set(2, 2, z);
		return matrix;
	}

	public static Mat33F rotationMat33F(float angle) {
		float rad = (float)Math.toRadians(angle);
		float sin = (float)Math.sin(rad);
		float cos = (float)Math.cos(rad);
		Mat33F matrix = identityMat33F();
		matrix.set(0, 0, cos);
		matrix.set(0, 1, -sin);
		matrix.set(1, 0, sin);
		matrix.set(1, 1, cos);
		return matrix;
	}
	public static Mat44F rotationMat44F(float angle, float x, float y, float z) {
		Vec3F axis = new Vec3F(x, y, z);
		axis.normalize();
		float a = axis.getX();
		float b = axis.getY();
		float c = axis.getZ();
		float rad = (float)Math.toRadians(angle);
		float sin = (float)Math.sin(rad);
		float cos = (float)Math.cos(rad);
		Mat44F matrix = identityMat44F();
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

	public static Mat44F orthoProjectionMatrix(float left, float right, float bottom, float top, float near, float far) {
		float sx = 2f/(right-left);
		float sy = 2f/(top-bottom);
		float sz = -2f/(far-near);
		float tx = (right+left)/(right-left);
		float ty = (top+bottom)/(top-bottom);
		float tz = (far+near)/(far-near);
		Mat44F matrix = identityMat44F();
		matrix.set(0, 0, sx);
		matrix.set(1, 1, sy);
		matrix.set(2, 2, sz);
		matrix.set(0, 3, -tx);
		matrix.set(1, 3, -ty);
		matrix.set(2, 3, -tz);
		return matrix;
	}

	public static Mat44F perspectiveProjectionMatrix(float fovy, float aspect, float near, float far) {
		float f = 1f/(float)Math.tan(fovy/2f);
		float sx = f/aspect;
		float sy = f;
		float sz = (near+far)/(near-far);
		float tz = (2*near*far)/(near-far);
		Mat44F matrix = identityMat44F();
		matrix.set(0, 0, sx);
		matrix.set(1, 1, sy);
		matrix.set(2, 2, sz);
		matrix.set(2, 3, tz);
		matrix.set(3, 2, -1f);
		matrix.set(3, 3, 0f);
		return matrix;
	}

	public static MatF appendRowMatrix(MatF top, MatF bottom) {
		MatF result = new MatF(top.rows()+bottom.rows(), Math.max(top.cols(), bottom.cols()), 0f);
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
	public static MatF appendColMatrix(MatF left, MatF right) {
		MatF result = new MatF(Math.max(left.rows(), right.rows()), left.cols()+right.cols(), 0f);
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
	public static MatF appendDiagMatrix(MatF topleft, MatF bottomright) {
		MatF result = new MatF(topleft.rows()+bottomright.rows(), topleft.cols()+bottomright.cols(), 0f);
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

	public static void add(MatF left, MatF right, MatF result) {
		if (checkSize(left, right, result)) {
			for (int i=0;i<result.size();i++) result.set(i, left.get(i) + right.get(i));
		}
	}
	public static MatF add(MatF left, MatF right) {
		MatF result = new MatF(left.rows(), left.cols());
		add(left, right, result);
		return result;
	}

	public static void sub(MatF left, MatF right, MatF result) {
		if (checkSize(left, right, result)) {
			for (int i=0;i<result.size();i++) result.set(i, left.get(i) - right.get(i));
		}
	}
	public static MatF sub(MatF left, MatF right) {
		MatF result = new MatF(left.rows(), left.cols());
		sub(left, right, result);
		return result;
	}

	public static void mult(MatF left, MatF right, MatF result) {
		if (checkMultipliable(left, right, result)) {
			MatF temp = new MatF(result.rows(), result.cols());
			int rows = left.rows();
			int cols = right.cols();
			int nums = left.cols();
			for (int i=0;i<rows;i++) {
				for (int j=0;j<cols;j++) {
					float sum = 0f;
					for (int k=0;k<nums;k++) sum += left.get(i, k) * right.get(k, j);
					temp.set(i, j, sum);
				}
			}
			result.set(temp);
		}
	}
	public static MatF mult(MatF left, MatF right) {
		MatF result = new MatF(left.rows(), right.cols());
		if (checkMultipliable(left, right, result)) {
			int rows = left.rows();
			int cols = right.cols();
			int nums = left.cols();
			for (int i=0;i<rows;i++) {
				for (int j=0;j<cols;j++) {
					float sum = 0f;
					for (int k=0;k<nums;k++) sum += left.get(i, k) * right.get(k, j);
					result.set(i, j, sum);
				}
			}
		}
		return result;
	}

	public static void mult(MatF matrix, VecF vector, VecF result) {
		if (checkMultipliable(matrix, vector, result)) {
			VecF temp = new VecF(result.size());
			for (int i=0;i<matrix.rows();i++) {
				float sum = 0f;
				for (int j=0;j<matrix.cols();j++) sum += matrix.get(i, j) * vector.get(j);
				temp.set(i, sum);
			}
			result.set(temp);
		}
	}
	public static VecF mult(MatF matrix, VecF vector) {
		VecF result = new VecF(matrix.rows());
		if (checkMultipliable(matrix, vector, result)) {
			for (int i=0;i<matrix.rows();i++) {
				float sum = 0f;
				for (int j=0;j<matrix.cols();j++) sum += matrix.get(i, j) * vector.get(j);
				result.set(i, sum);
			}
		}
		return result;
	}

	public static float calcDeterminant(MatF matrix) {
		if (matrix.isSquare()) {
			if (matrix.dim() > 1) {
				float det = 0f;
				for (int i=0;i<matrix.dim();i++) {
					MatF subcut = matrix.getSubCutMatrix(0, i);
					float d = matrix.get(0, i) * calcDeterminant(subcut);
					if (i%2 == 0) det += d;
					else det -= d;
				}
				return det;
			} else {
				return matrix.get(0);
			}
		}
		return 0f;
	}

	public static MatF calcRowEchelonForm(MatF matrix) {
		MatF result = new MatF(matrix);
		int r = 0;
		int s = 0;
		while (r < result.rows() && s < result.cols()) {
			int k = 0;
			for (int i=r;i<result.rows();i++) if (k == 0 && !Utility.evaluate(result.get(i, s), 0f)) k = i;
			if (k != 0) {
				result.swapRow(r, k);
				result.multRow(r, 1f/result.get(r, s));
				for (int i=0;i<result.rows();i++) {
					if (i != r) result.addRow(i, result.getRow(r).scalar(-result.get(i, s)));
				}
				r++;
			}
			s++;
		}
		return result;
	}

	public static MatF calcInverse(MatF matrix) {
		if (matrix.isSquare()) return calcRowEchelonForm(MatUtil.appendColMatrix(matrix, new MatF(matrix.dim(), true))).getSubMatrix(0, matrix.cols(), matrix.rows()-1, 2*matrix.cols()-1);
		return null;
	}

	public static int calcRank(MatF matrix) {
		MatF result = calcRowEchelonForm(matrix);
		int rank = 0;
		for (int i=0;i<result.rows();i++) {
			for (int j=0;j<result.cols();j++) {
				if (!Utility.evaluate(result.get(i, j), 0f)) {
					rank++;
					break;
				}
			}
		}
		return rank;
	}

	private static boolean checkSize(MatF... matrices) {
		for (int i=0;i<matrices.length-1;i++) {
			if (matrices[i].rows() != matrices[i+1].rows() ||
					matrices[i].cols() != matrices[i+1].cols()) {
				Log.e("MatUtil", "Incompatible matrices!");
				return false;
			}
		}
		return true;
	}
	private static boolean checkMultipliable(MatF left, MatF right, MatF result) {
		if (left.cols() != right.rows() ||
				result.rows() != left.rows() ||
				result.cols() != right.cols()) {
			Log.e("MatUtil", "Incompatible matrices!");
			return false;
		}
		return true;
	}
	private static boolean checkMultipliable(MatF matrix, VecF vector, VecF result) {
		if (matrix.cols() != vector.size() || matrix.rows() != result.size()) {
			Log.e("MatUtil", "Incompatible matrices or vertices!");
			return false;
		}
		return true;
	}

}
