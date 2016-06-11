package zan.lib.util.math;

public final class MatD implements IMatD<MatD> {

	private final double[] data;
	private final int rows, cols;

	public MatD(int rows, int cols, double... data) {
		this.rows = rows;
		this.cols = cols;
		if (size() != data.length) {
			System.err.println("Warning: Inconsistent matrix");
			this.data = null;
		} else {
			this.data = data;
		}
	}

	@Override
	public int rows() {return rows;}

	@Override
	public int cols() {return cols;}

	@Override
	public int size() {return rows * cols;}

	@Override
	public double get(int component) {return data[component];}

	@Override
	public double get(int row, int col) {return data[cols*row+col];}

	@Override
	public MatD add(MatD m) {
		if (rows() != m.rows() || cols() != m.cols()) {
			System.err.println("Warning: Incompatible matrices");
			return null;
		}
		double[] result = new double[size()];
		for (int i=0;i<size();i++) result[i] = get(i) + m.get(i);
		return new MatD(rows(), cols(), result);
	}

	@Override
	public MatD sub(MatD m) {
		if (rows() != m.rows() || cols() != m.cols()) {
			System.err.println("Warning: Incompatible matrices");
			return null;
		}
		double[] result = new double[size()];
		for (int i=0;i<size();i++) result[i] = get(i) - m.get(i);
		return new MatD(rows(), cols(), result);
	}

	@Override
	public MatD mult(MatD m) {
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
		return new MatD(rows(), m.cols(), result);
	}

	@Override
	public MatD scalar(double s) {
		double[] result = new double[size()];
		for (int i=0;i<size();i++) result[i] = s * get(i);
		return new MatD(rows(), cols(), result);
	}

	@Override
	public MatD negate() {return scalar(-1.0);}

	@Override
	public double dot(MatD v) {
		double dot = 0.0;
		for (int i=0;i<size();i++) dot += get(i) * v.get(i);
		return dot;
	}

	@Override
	public double length() {return Math.sqrt(dot(this));}

	@Override
	public MatD normalize() {return scalar(1.0/length());}

	@Override
	public MatD transpose() {
		double[] result = new double[cols() * rows()];
		for (int i=0;i<cols();i++) {
			for (int j=0;j<rows();j++) {
				result[rows()*i+j] = get(j, i);
			}
		}
		return new MatD(cols(), rows(), result);
	}

	@Override
	public boolean is(MatD m) {
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
