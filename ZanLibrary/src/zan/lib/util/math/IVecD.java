package zan.lib.util.math;

public interface IVecD<V> extends IVec<V> {

	public double get(int index);

	public V scalar(double s);

	public double dot(V v);
	public double length();

}
