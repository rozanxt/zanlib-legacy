package zan.lib.util.math;

public final class VecD implements IVecD<VecD> {

	private final double[] data;

	public VecD(double... data) {this.data = data;}

	@Override
	public int size() {return data.length;}

	@Override
	public double get(int component) {return data[component];}

	@Override
	public VecD add(VecD v) {
		if (size() != v.size()) {
			System.err.println("Warning: Incompatible vectors");
			return null;
		}
		double[] result = new double[size()];
		for (int i=0;i<size();i++) result[i] = get(i) + v.get(i);
		return new VecD(result);
	}

	@Override
	public VecD sub(VecD v) {
		if (size() != v.size()) {
			System.err.println("Warning: Incompatible vectors");
			return null;
		}
		double[] result = new double[size()];
		for (int i=0;i<size();i++) result[i] = get(i) - v.get(i);
		return new VecD(result);
	}

	@Override
	public VecD scalar(double s) {
		double[] result = new double[size()];
		for (int i=0;i<size();i++) result[i] = s * get(i);
		return new VecD(result);
	}

	@Override
	public VecD negate() {return scalar(-1.0);}

	@Override
	public double dot(VecD v) {
		double dot = 0.0;
		for (int i=0;i<size();i++) dot += get(i) * v.get(i);
		return dot;
	}

	@Override
	public double length() {return Math.sqrt(dot(this));}

	@Override
	public VecD normalize() {return scalar(1.0/length());}

	@Override
	public boolean is(VecD v) {
		if (size() != v.size()) return false;
		for (int i=0;i<size();i++) if (get(i) != v.get(i)) return false;
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
