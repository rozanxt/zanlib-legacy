package zan.lib.util.math;

public interface IVec<V> {

	public int size();

	public V add(V v);
	public V sub(V v);

	public V negate();
	public V normalize();

	public boolean is(V v);

}
