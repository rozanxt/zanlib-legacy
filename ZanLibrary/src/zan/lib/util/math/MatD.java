package zan.lib.util.math;

import java.util.Arrays;

public final class MatD implements IMatD<MatD> {

	private final int rows, cols;
	private final double[] data;

	public MatD(int rows, int cols, double... data) {
		this.rows = rows;
		this.cols = cols;
		if (size() == data.length) {
			this.data = Arrays.copyOf(data, data.length);
		} else {
			System.out.println("Warning: Illegal matrix entry");
			this.data = null;
		}
	}

	@Override
	public int rows() {
		return rows;
	}

	@Override
	public int cols() {
		return cols;
	}

	@Override
	public int size() {
		return rows * cols;
	}

	@Override
	public double get(int index) {
		return data[index];
	}

	@Override
	public double get(int row, int col) {
		return data[cols()*row+col];
	}

	@Override
	public MatD add(MatD mat) {
		if (rows() == mat.rows() && cols() == mat.cols()) {
			double[] result = new double[size()];
			for (int i=0;i<size();i++) result[i] = get(i) + mat.get(i);
			return new MatD(rows(), cols(), result);
		}
		System.err.println("Warning: Illegal matrix operation");
		return null;
	}

	@Override
	public MatD sub(MatD mat) {
		if (rows() == mat.rows() && cols() == mat.cols()) {
			double[] result = new double[size()];
			for (int i=0;i<size();i++) result[i] = get(i) - mat.get(i);
			return new MatD(rows(), cols(), result);
		}
		System.err.println("Warning: Illegal matrix operation");
		return null;
	}

	@Override
	public MatD scalar(double scalar) {
		double[] result = new double[size()];
		for (int i=0;i<size();i++) result[i] = scalar * get(i);
		return new MatD(rows(), cols(), result);
	}

	@Override
	public MatD negate() {
		return scalar(-1.0);
	}

	@Override
	public MatD mult(MatD mat) {
		if (cols() == mat.rows()) {
			double[] result = new double[rows() * mat.cols()];
			for (int i=0;i<rows();i++) {
				for (int j=0;j<mat.cols();j++) {
					double sum = 0.0;
					for (int k=0;k<cols();k++) sum += get(i, k) * mat.get(k, j);
					result[mat.cols()*i+j] = sum;
				}
			}
			return new MatD(rows(), mat.cols(), result);
		}
		System.err.println("Warning: Illegal matrix operation");
		return null;
	}

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
	public boolean is(MatD mat) {
		if (rows() != mat.rows() || cols() != mat.cols()) return false;
		for (int i=0;i<size();i++) if (get(i) != mat.get(i)) return false;
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
