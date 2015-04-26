package zan.lib.math.matrix;

public class Mat44D extends SquareMatD {
	
	public Mat44D() {super(4);}
	public Mat44D(Mat44D matrix) {super(matrix);}
	public Mat44D(double value, boolean diagonal) {super(4, value, diagonal);}
	
}
