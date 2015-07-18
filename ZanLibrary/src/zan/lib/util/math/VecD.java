package zan.lib.util.math;

public class VecD {
	
	private double[] data;
	
	public VecD() {this(1);}
	public VecD(int size) {data = new double[size];}
	public VecD(int size, double value) {this(size); setAll(value);}
	public VecD(double... entry) {this(entry.length); put(entry);}
	public VecD(VecD vector) {this(vector.size()); set(vector);}
	public VecD(int size, VecD vector) {this(size, 0.0); set(vector);}
	
	public void set(int component, double value) {data[component] = value;}
	public void setAll(double value) {for (int i=0;i<size();i++) set(i, value);}
	public void put(double... entry) {
		int size = Math.min(size(), entry.length);
		for (int i=0;i<size;i++) set(i, entry[i]);
	}
	public void set(VecD vector) {
		int size = Math.min(size(), vector.size());
		for (int i=0;i<size;i++) set(i, vector.get(i));
	}
	
	public double get(int component) {return data[component];}
	
	public double length() {return Math.sqrt(lengthSquared());}
	public double lengthSquared() {
		double lenSq = 0.0;
		for (int i=0;i<size();i++) lenSq += get(i) * get(i);
		return lenSq;
	}
	
	public VecD scalar(double factor) {
		for (int i=0;i<size();i++) data[i] *= factor;
		return this;
	}
	public VecD negate() {return scalar(-1.0);}
	public VecD normalize() {return scalar(1.0/length());}
	
	public VecD getScalar(double factor) {
		VecD result = new VecD(this);
		return result.scalar(factor);
	}
	public VecD getNegate() {return getScalar(-1.0);}
	public VecD getNormalize() {return getScalar(1.0/length());}
	
	public double add(int component, double value) {return data[component] += value;}
	public double sub(int component, double value) {return data[component] -= value;}
	public double mul(int component, double value) {return data[component] *= value;}
	public double div(int component, double value) {return data[component] /= value;}
	
	public int size() {return data.length;}
	
	@Override
	public String toString() {
		String str = "";
		for (int i=0;i<size();i++) {
			str += get(i);
			if (i < size()-1) str += " ";
		}
		return str;
	}
	
}
