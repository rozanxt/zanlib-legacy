package zan.lib.math.linalg;

public class Mat33D implements IMatD<Mat33D> {

	public final double a11, a12, a13,
						a21, a22, a23,
						a31, a32, a33;

	public Mat33D(double a11, double a12, double a13,
				  double a21, double a22, double a23,
				  double a31, double a32, double a33) {
		this.a11 = a11; this.a12 = a12; this.a13 = a13;
		this.a21 = a21; this.a22 = a22; this.a23 = a23;
		this.a31 = a31; this.a32 = a32; this.a33 = a33;
	}

	@Override
	public int rows() {
		return 3;
	}

	@Override
	public int cols() {
		return 3;
	}

	@Override
	public int size() {
		return 9;
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
			return a21;
		case 4:
			return a22;
		case 5:
			return a23;
		case 6:
			return a31;
		case 7:
			return a32;
		case 8:
			return a33;
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
			default:
				return 0.0;
			}
		default:
			return 0.0;
		}
	}

	@Override
	public Mat33D add(Mat33D mat) {
		return new Mat33D(a11 + mat.a11, a12 + mat.a12, a13 + mat.a13,
						  a21 + mat.a21, a22 + mat.a22, a23 + mat.a23,
						  a31 + mat.a31, a32 + mat.a32, a33 + mat.a33);
	}

	@Override
	public Mat33D sub(Mat33D mat) {
		return new Mat33D(a11 - mat.a11, a12 - mat.a12, a13 - mat.a13,
						  a21 - mat.a21, a22 - mat.a22, a23 - mat.a23,
						  a31 - mat.a31, a32 - mat.a32, a33 - mat.a33);
	}

	@Override
	public Mat33D scalar(double scalar) {
		return new Mat33D(scalar * a11, scalar * a12, scalar * a13,
						  scalar * a21, scalar * a22, scalar * a23,
						  scalar * a31, scalar * a32, scalar * a33);
	}

	@Override
	public Mat33D negate() {
		return scalar(-1.0);
	}

	@Override
	public Mat33D mult(Mat33D mat) {
		return new Mat33D(a11 * mat.a11 + a12 * mat.a21 + a13 * mat.a31, a11 * mat.a12 + a12 * mat.a22 + a13 * mat.a32, a11 * mat.a13 + a12 * mat.a23 + a13 * mat.a33,
						  a21 * mat.a11 + a22 * mat.a21 + a23 * mat.a31, a21 * mat.a12 + a22 * mat.a22 + a23 * mat.a32, a21 * mat.a13 + a22 * mat.a23 + a23 * mat.a33,
						  a31 * mat.a11 + a32 * mat.a21 + a33 * mat.a31, a31 * mat.a12 + a32 * mat.a22 + a33 * mat.a32, a31 * mat.a13 + a32 * mat.a23 + a33 * mat.a33);
	}

	@Override
	public Mat33D transpose() {
		return new Mat33D(a11, a21, a31,
						  a12, a22, a32,
						  a13, a23, a33);
	}

	@Override
	public boolean is(Mat33D mat) {
		return (a11 == mat.a11 && a12 == mat.a12 && a13 == mat.a13 &&
				a21 == mat.a21 && a22 == mat.a22 && a23 == mat.a23 &&
				a31 == mat.a31 && a32 == mat.a32 && a33 == mat.a33);
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
