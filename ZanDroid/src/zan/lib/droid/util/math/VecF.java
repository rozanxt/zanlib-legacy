package zan.lib.droid.util.math;

public class VecF {
	
	private float[] data;
	
	public VecF() {this(1);}
	public VecF(int size) {data = new float[size];}
	public VecF(int size, float value) {this(size); setAll(value);}
	public VecF(float... entry) {this(entry.length); put(entry);}
	public VecF(VecF vector) {this(vector.size()); set(vector);}
	public VecF(int size, VecF vector) {this(size, 0f); set(vector);}
	
	public void set(int component, float value) {data[component] = value;}
	public void setAll(float value) {for (int i=0;i<size();i++) set(i, value);}
	public void put(int offset, float... entry) {
		int size = Math.min(size()-offset, entry.length);
		for (int i=0;i<size;i++) set(offset+i, entry[i]);
	}
	public void put(float... entry) {put(0, entry);}
	public void set(int offset, VecF vector) {
		int size = Math.min(size()-offset, vector.size());
		for (int i=0;i<size;i++) set(offset+i, vector.get(i));
	}
	public void set(VecF vector) {set(0, vector);}
	
	public float get(int component) {return data[component];}
	public VecF get(int start, int end) {
		VecF result = new VecF(end-start+1);
		int size = Math.min(size()-start, result.size());
		for (int i=0;i<size;i++) result.set(i, get(start+i));
		return result;
	}
	
	public float sum() {
		float sum = 0f;
		for (int i=0;i<size();i++) sum += get(i);
		return sum;
	}
	public float lengthSquared() {
		float lenSq = 0f;
		for (int i=0;i<size();i++) lenSq += get(i) * get(i);
		return lenSq;
	}
	public float length() {return (float)Math.sqrt(lengthSquared());}
	
	public VecF scalar(float factor) {
		for (int i=0;i<size();i++) data[i] *= factor;
		return this;
	}
	public VecF negate() {return scalar(-1f);}
	public VecF normalize() {return scalar(1f/length());}
	
	public VecF round(int dp) {
		for (int i=0;i<size();i++) set(i, (float)(Math.round(get(i)*Math.pow(10, dp))*Math.pow(10, -dp)));
		return this;
	}
	public VecF round() {return round(0);}
	
	public float add(int component, float value) {return data[component] += value;}
	public float sub(int component, float value) {return data[component] -= value;}
	public float mul(int component, float value) {return data[component] *= value;}
	public float div(int component, float value) {return data[component] /= value;}
	
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
