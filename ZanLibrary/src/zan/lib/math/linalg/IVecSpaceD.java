package zan.lib.math.linalg;

public interface IVecSpaceD<T> {

	public boolean is(T v);

	public T add(T v);
	public T sub(T v);
	public T scalar(double s);
	public T negate();

}
