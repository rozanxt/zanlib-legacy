package zan.lib.math.linalg;

public class Mat22D implements IMatD<Mat22D> {

	public final double a11, a12,
						a21, a22;

	public Mat22D(double a11, double a12,
				  double a21, double a22) {
		this.a11 = a11; this.a12 = a12;
		this.a21 = a21; this.a22 = a22;
	}

	@Override
	public int rows() {
		return 2;
	}

	@Override
	public int cols() {
		return 2;
	}

	@Override
	public int size() {
		return 4;
	}

	@Override
	public double get(int index) {
		switch (index) {
		case 0:
			return a11;
		case 1:
			return a12;
		case 2:
			return a21;
		case 3:
			return a22;
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
			default:
				return 0.0;
			}
		case 1:
			switch (col) {
			case 0:
				return a21;
			case 1:
				return a22;
			default:
				return 0.0;
			}
		default:
			return 0.0;
		}
	}

	@Override
	public Mat22D add(Mat22D mat) {
		return new Mat22D(a11 + mat.a11, a12 + mat.a12,
						  a21 + mat.a21, a22 + mat.a22);
	}

	@Override
	public Mat22D sub(Mat22D mat) {
		return new Mat22D(a11 - mat.a11, a12 - mat.a12,
						  a21 - mat.a21, a22 - mat.a22);
	}

	@Override
	public Mat22D scalar(double scalar) {
		return new Mat22D(scalar * a11, scalar * a12,
						  scalar * a21, scalar * a22);
	}

	@Override
	public Mat22D negate() {
		return scalar(-1.0);
	}

	@Override
	public Mat22D mult(Mat22D mat) {
		return new Mat22D(a11 * mat.a11 + a12 * mat.a21, a11 * mat.a12 + a12 * mat.a22,
						  a21 * mat.a11 + a22 * mat.a21, a21 * mat.a12 + a22 * mat.a22);
	}

	@Override
	public Mat22D transpose() {
		return new Mat22D(a11, a21,
						  a12, a22);
	}

	@Override
	public boolean is(Mat22D mat) {
		return (a11 == mat.a11 && a12 == mat.a12 &&
				a21 == mat.a21 && a22 == mat.a22);
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
