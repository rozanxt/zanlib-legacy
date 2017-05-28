package zan.lib.math.linalg;

import java.util.Arrays;

public class VecD implements IVecD<VecD> {

	private final double[] data;

	public VecD(double... data) {
		this.data = Arrays.copyOf(data, data.length);
	}

	@Override
	public int size() {
		return data.length;
	}

	@Override
	public double get(int index) {
		return data[index];
	}

	@Override
	public VecD add(VecD vec) {
		if (size() == vec.size()) {
			double[] result = new double[size()];
			for (int i=0;i<size();i++) result[i] = get(i) + vec.get(i);
			return new VecD(result);
		}
		System.err.println("Warning: Illegal vector operation");
		return null;
	}

	@Override
	public VecD sub(VecD vec) {
		if (size() == vec.size()) {
			double[] result = new double[size()];
			for (int i=0;i<size();i++) result[i] = get(i) - vec.get(i);
			return new VecD(result);
		}
		System.err.println("Warning: Illegal vector operation");
		return null;
	}

	@Override
	public VecD scalar(double scalar) {
		double[] result = new double[size()];
		for (int i=0;i<size();i++) result[i] = scalar * get(i);
		return new VecD(result);
	}

	@Override
	public VecD negate() {
		return scalar(-1.0);
	}

	@Override
	public double dot(VecD vec) {
		double dot = 0.0;
		for (int i=0;i<size();i++) dot += get(i) * vec.get(i);
		return dot;
	}

	@Override
	public double length() {
		return Math.sqrt(dot(this));
	}

	@Override
	public VecD normalize() {
		return scalar(1.0 / length());
	}

	@Override
	public boolean is(VecD vec) {
		if (size() != vec.size()) return false;
		for (int i=0;i<size();i++) if (get(i) != vec.get(i)) return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("(");
		for (int i=0;i<size();i++) {
			str.append(get(i));
			if (i < size()-1) str.append(",");
		}
		str.append(")");
		return str.toString();
	}

}
