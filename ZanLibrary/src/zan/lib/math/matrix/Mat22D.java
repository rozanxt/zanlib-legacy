package zan.lib.math.matrix;

public class Mat22D extends SquareMatD {
	
	public Mat22D() {super(2);}
	public Mat22D(Mat22D matrix) {super(matrix);}
	public Mat22D(double value, boolean diagonal) {super(2, value, diagonal);}
	
}
