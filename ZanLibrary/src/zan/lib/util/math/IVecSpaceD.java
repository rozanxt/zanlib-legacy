package zan.lib.util.math;

public interface IVecSpaceD<V> {

	public boolean is(V v);

	public V add(V v);
	public V sub(V v);
	public V scalar(double s);
	public V negate();

}
