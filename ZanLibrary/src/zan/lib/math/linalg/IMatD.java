package zan.lib.math.linalg;

public interface IMatD<T> extends IVecSpaceD<T> {

	public int rows();
	public int cols();
	public int size();

	public double get(int index);
	public double get(int row, int col);

	public T mult(T m);
	public T transpose();

}
