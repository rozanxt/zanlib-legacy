package zan.lib.math.linalg;

public class Mat44D implements IMatD<Mat44D> {

	public final double a11, a12, a13, a14,
						a21, a22, a23, a24,
						a31, a32, a33, a34,
						a41, a42, a43, a44;

	public Mat44D(double a11, double a12, double a13, double a14,
				  double a21, double a22, double a23, double a24,
				  double a31, double a32, double a33, double a34,
				  double a41, double a42, double a43, double a44) {
		this.a11 = a11; this.a12 = a12; this.a13 = a13; this.a14 = a14;
		this.a21 = a21; this.a22 = a22; this.a23 = a23; this.a24 = a24;
		this.a31 = a31; this.a32 = a32; this.a33 = a33; this.a34 = a34;
		this.a41 = a41; this.a42 = a42; this.a43 = a43; this.a44 = a44;
	}

	@Override
	public int rows() {
		return 4;
	}

	@Override
	public int cols() {
		return 4;
	}

	@Override
	public int size() {
		return 16;
	}

	@Override
	public double get(int index) {
		switch (index) {
		case 0:
			return a11;
		case 1:
			return a12;
		case 2:
			return a13;
		case 3:
			return a14;
		case 4:
			return a21;
		case 5:
			return a22;
		case 6:
			return a23;
		case 7:
			return a24;
		case 8:
			return a31;
		case 9:
			return a32;
		case 10:
			return a33;
		case 11:
			return a34;
		case 12:
			return a41;
		case 13:
			return a42;
		case 14:
			return a43;
		case 15:
			return a44;
		default:
			return 0.0;
		}
	}

	@Override
	public double get(int row, int col) {
		switch (row) {
		case 0:
			switch (col) {
			case 0:
				return a11;
			case 1:
				return a12;
			case 2:
				return a13;
			case 3:
				return a14;
			default:
				return 0.0;
			}
		case 1:
			switch (col) {
			case 0:
				return a21;
			case 1:
				return a22;
			case 2:
				return a23;
			case 3:
				return a24;
			default:
				return 0.0;
			}
		case 2:
			switch (col) {
			case 0:
				return a31;
			case 1:
				return a32;
			case 2:
				return a33;
			case 3:
				return a34;
			default:
				return 0.0;
			}
		case 3:
			switch (col) {
			case 0:
				return a41;
			case 1:
				return a42;
			case 2:
				return a43;
			case 3:
				return a44;
			default:
				return 0.0;
			}
		default:
			return 0.0;
		}
	}

	@Override
	public Mat44D add(Mat44D mat) {
		return new Mat44D(a11 + mat.a11, a12 + mat.a12, a13 + mat.a13, a14 + mat.a14,
						  a21 + mat.a21, a22 + mat.a22, a23 + mat.a23, a24 + mat.a24,
						  a31 + mat.a31, a32 + mat.a32, a33 + mat.a33, a34 + mat.a34,
						  a41 + mat.a41, a42 + mat.a42, a43 + mat.a43, a44 + mat.a44);
	}

	@Override
	public Mat44D sub(Mat44D mat) {
		return new Mat44D(a11 - mat.a11, a12 - mat.a12, a13 - mat.a13, a14 - mat.a14,
						  a21 - mat.a21, a22 - mat.a22, a23 - mat.a23, a24 - mat.a24,
						  a31 - mat.a31, a32 - mat.a32, a33 - mat.a33, a34 - mat.a34,
						  a41 - mat.a41, a42 - mat.a42, a43 - mat.a43, a44 - mat.a44);
	}

	@Override
	public Mat44D scalar(double scalar) {
		return new Mat44D(scalar * a11, scalar * a12, scalar * a13, scalar * a14,
						  scalar * a21, scalar * a22, scalar * a23, scalar * a24,
						  scalar * a31, scalar * a32, scalar * a33, scalar * a34,
						  scalar * a41, scalar * a42, scalar * a43, scalar * a44);
	}

	@Override
	public Mat44D negate() {
		return scalar(-1.0);
	}

	@Override
	public Mat44D mult(Mat44D mat) {
		return new Mat44D(a11 * mat.a11 + a12 * mat.a21 + a13 * mat.a31 + a14 * mat.a41, a11 * mat.a12 + a12 * mat.a22 + a13 * mat.a32 + a14 * mat.a42, a11 * mat.a13 + a12 * mat.a23 + a13 * mat.a33 + a14 * mat.a43, a11 * mat.a14 + a12 * mat.a24 + a13 * mat.a34 + a14 * mat.a44,
						  a21 * mat.a11 + a22 * mat.a21 + a23 * mat.a31 + a24 * mat.a41, a21 * mat.a12 + a22 * mat.a22 + a23 * mat.a32 + a24 * mat.a42, a21 * mat.a13 + a22 * mat.a23 + a23 * mat.a33 + a24 * mat.a43, a21 * mat.a14 + a22 * mat.a24 + a23 * mat.a34 + a24 * mat.a44,
						  a31 * mat.a11 + a32 * mat.a21 + a33 * mat.a31 + a34 * mat.a41, a31 * mat.a12 + a32 * mat.a22 + a33 * mat.a32 + a34 * mat.a42, a31 * mat.a13 + a32 * mat.a23 + a33 * mat.a33 + a34 * mat.a43, a31 * mat.a14 + a32 * mat.a24 + a33 * mat.a34 + a34 * mat.a44,
						  a41 * mat.a11 + a42 * mat.a21 + a43 * mat.a31 + a44 * mat.a41, a41 * mat.a12 + a42 * mat.a22 + a43 * mat.a32 + a44 * mat.a42, a41 * mat.a13 + a42 * mat.a23 + a43 * mat.a33 + a44 * mat.a43, a41 * mat.a14 + a42 * mat.a24 + a43 * mat.a34 + a44 * mat.a44);
	}

	@Override
	public Mat44D transpose() {
		return new Mat44D(a11, a21, a31, a41,
						  a12, a22, a32, a42,
						  a13, a23, a33, a43,
						  a14, a24, a34, a44);
	}

	@Override
	public boolean is(Mat44D mat) {
		return (a11 == mat.a11 && a12 == mat.a12 && a13 == mat.a13 && a14 == mat.a14 &&
				a21 == mat.a21 && a22 == mat.a22 && a23 == mat.a23 && a24 == mat.a24 &&
				a31 == mat.a31 && a32 == mat.a32 && a33 == mat.a33 && a34 == mat.a34 &&
				a41 == mat.a41 && a42 == mat.a42 && a43 == mat.a43 && a44 == mat.a44);
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("(");
		for (int i=0;i<rows();i++) {
			for (int j=0;j<cols();j++) {
				str.append(get(i, j));
				if (j<cols()-1) str.append(",");
			}
			if (i<rows()-1) {
				str.append(";");
				str.append("\n ");
			}
		}
		str.append(")");
		return str.toString();
	}

}
