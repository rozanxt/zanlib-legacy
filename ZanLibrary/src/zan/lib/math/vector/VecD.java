package zan.lib.math.vector;

public class VecD {
	
	private double[] data;
	
	public VecD(int size) {
		data = new double[size];
	}
	public VecD(int size, double value) {
		this(size);
		setAll(value);
	}
	public VecD(VecD vector) {
		this(vector.size());
		set(vector);
	}
	
	public void set(int component, double value) {data[component] = value;}
	public void set(VecD vector) {
		int size = Math.min(size(), vector.size());
		for (int i=0;i<size;i++) set(i, vector.get(i));
	}
	public void setAll(double value) {for (int i=0;i<size();i++) set(i, value);}
	
	public double get(int component) {return data[component];}
	public double[] getAll() {return data;}
	
	public double length() {return Math.sqrt(lengthSquared());}
	public double lengthSquared() {
		double lenSq = 0.0;
		for (int i=0;i<size();i++) lenSq += get(i) * get(i);
		return lenSq;
	}
	
	public double add(int component, double value) {return data[component] += value;}
	public double sub(int component, double value) {return data[component] -= value;}
	public double mult(int component, double value) {return data[component] *= value;}
	public double div(int component, double value) {return data[component] /= value;}
	
	public void scalar(double value) {for (int i=0;i<size();i++) mult(i, value);}
	public void negate() {scalar(-1.0);}
	public void normalize() {scalar(1.0/length());}
	
	public int size() {return data.length;}
	
	public void print() {
		for (int i=0;i<size();i++) System.out.print(get(i) + " ");
		System.out.print("\n");
	}
	
}
