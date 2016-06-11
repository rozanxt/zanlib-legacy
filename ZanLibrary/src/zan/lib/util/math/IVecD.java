package zan.lib.util.math;

public interface IVecD<V> extends IVecSpaceD<V> {

	public int size();

	public double get(int index);

	public double dot(V v);
	public double length();
	public V normalize();

}
