package zan.lib.math.matrix;

public class SquareMatD extends MatD {
	
	public SquareMatD(int size) {super(size, size);}
	public SquareMatD(SquareMatD matrix) {super(matrix);}
	public SquareMatD(int size, double value, boolean diagonal) {
		this(size);
		if (diagonal) set(value);
		else setAll(value);
	}
	
	public void set(double value) {
		for (int i=0;i<rows();i++) {
			for (int j=0;j<cols();j++) {
				if (i == j) set(i, j, value);
				else set(i, j, 0.0);
			}
		}
	}
	
	public void loadIdentity() {set(1.0);}
	
}
