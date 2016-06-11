package zan.lib.util.math;

public final class Mat33D implements IMatD<Mat33D> {

	private final double[] data;

	public Mat33D(double... data) {
		if (size() != data.length) {
			System.err.println("Warning: Inconsistent matrix");
			this.data = null;
		} else {
			this.data = data;
		}
	}

	@Override
	public int rows() {return 3;}

	@Override
	public int cols() {return 3;}

	@Override
	public int size() {return 9;}

	@Override
	public double get(int component) {return data[component];}

	@Override
	public double get(int row, int col) {return data[cols()*row+col];}

	@Override
	public Mat33D add(Mat33D m) {
		if (rows() != m.rows() || cols() != m.cols()) {
			System.err.println("Warning: Incompatible matrices");
			return null;
		}
		double[] result = new double[size()];
		for (int i=0;i<size();i++) result[i] = get(i) + m.get(i);
		return new Mat33D(result);
	}

	@Override
	public Mat33D sub(Mat33D m) {
		if (rows() != m.rows() || cols() != m.cols()) {
			System.err.println("Warning: Incompatible matrices");
			return null;
		}
		double[] result = new double[size()];
		for (int i=0;i<size();i++) result[i] = get(i) - m.get(i);
		return new Mat33D(result);
	}

	@Override
	public Mat33D mult(Mat33D m) {
		if (cols() != m.rows()) {
			System.err.println("Warning: Incompatible matrices");
			return null;
		}
		double[] result = new double[rows() * m.cols()];
		for (int i=0;i<rows();i++) {
			for (int j=0;j<m.cols();j++) {
				double sum = 0.0;
				for (int k=0;k<cols();k++) sum += get(i, k) * m.get(k, j);
				result[m.cols()*i+j] = sum;
			}
		}
		return new Mat33D(result);
	}

	@Override
	public Mat33D scalar(double s) {
		double[] result = new double[size()];
		for (int i=0;i<size();i++) result[i] = s * get(i);
		return new Mat33D(result);
	}

	@Override
	public Mat33D negate() {return scalar(-1.0);}

	@Override
	public double dot(Mat33D v) {
		double dot = 0.0;
		for (int i=0;i<size();i++) dot += get(i) * v.get(i);
		return dot;
	}

	@Override
	public double length() {return Math.sqrt(dot(this));}

	@Override
	public Mat33D normalize() {return scalar(1.0/length());}

	@Override
	public Mat33D transpose() {
		double[] result = new double[cols() * rows()];
		for (int i=0;i<cols();i++) {
			for (int j=0;j<rows();j++) {
				result[rows()*i+j] = get(j, i);
			}
		}
		return new Mat33D(result);
	}

	@Override
	public boolean is(Mat33D m) {
		if (rows() != m.rows() || cols() != m.cols()) return false;
		for (int i=0;i<size();i++) if (get(i) != m.get(i)) return false;
		return true;
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
