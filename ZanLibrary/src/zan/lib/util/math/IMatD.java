package zan.lib.util.math;

public interface IMatD<M> extends IVecSpaceD<M> {

	public int rows();
	public int cols();
	public int size();

	public double get(int index);
	public double get(int row, int col);

	public M mult(M m);
	public M transpose();

}
