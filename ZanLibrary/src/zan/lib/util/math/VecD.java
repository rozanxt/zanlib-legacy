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
	public void put(int offset, double... entry) {
		int size = Math.min(size()-offset, entry.length);
		for (int i=0;i<size;i++) set(offset+i, entry[i]);
	}
	public void put(double... entry) {put(0, entry);}
	public void set(int offset, VecD vector) {
		int size = Math.min(size()-offset, vector.size());
		for (int i=0;i<size;i++) set(offset+i, vector.get(i));
	}
	public void set(VecD vector) {set(0, vector);}
	
	public double get(int component) {return data[component];}
	public VecD get(int start, int end) {
		VecD result = new VecD(end-start+1);
		int size = Math.min(size()-start, result.size());
		for (int i=0;i<size;i++) result.set(i, get(start+i));
		return result;
	}
	
	public double sum() {
		double sum = 0.0;
		for (int i=0;i<size();i++) sum += get(i);
		return sum;
	}
	public double lengthSquared() {
		double lenSq = 0.0;
		for (int i=0;i<size();i++) lenSq += get(i) * get(i);
		return lenSq;
	}
	public double length() {return Math.sqrt(lengthSquared());}
	
	public VecD scalar(double factor) {
		for (int i=0;i<size();i++) data[i] *= factor;
		return this;
	}
	public VecD negate() {return scalar(-1.0);}
	public VecD normalize() {return scalar(1.0/length());}
	
	public VecD round(int dp) {
		for (int i=0;i<size();i++) set(i, Math.round(get(i)*Math.pow(10, dp))*Math.pow(10, -dp));
		return this;
	}
	public VecD round() {return round(0);}
	
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
