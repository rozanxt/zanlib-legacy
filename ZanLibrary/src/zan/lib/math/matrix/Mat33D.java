package zan.lib.math.matrix;

public class Mat33D extends SquareMatD {
	
	public Mat33D() {super(3);}
	public Mat33D(Mat33D matrix) {super(matrix);}
	public Mat33D(double value, boolean diagonal) {super(3, value, diagonal);}
	
}
