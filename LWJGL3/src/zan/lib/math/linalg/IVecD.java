package zan.lib.math.linalg;

public interface IVecD<T> extends IVecSpaceD<T> {

	public int size();

	public double get(int index);

	public double dot(T v);
	public double length();
	public T normalize();

}
