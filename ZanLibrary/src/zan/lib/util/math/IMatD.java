package zan.lib.util.math;

public interface IMatD<M> extends IVecD<M> {

	public int rows();
	public int cols();

	public double get(int row, int col);

	public M mult(M m);
	public M transpose();

}
