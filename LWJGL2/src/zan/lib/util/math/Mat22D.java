package zan.lib.util.math;

public class Mat22D extends MatD {

	public Mat22D() {super(2);}
	public Mat22D(boolean identity) {super(2, identity);}
	public Mat22D(double value) {super(2, value);}
	public Mat22D(MatD matrix) {super(2, matrix);}
	public Mat22D(Vec2D vec0, Vec2D vec1) {super(vec0, vec1);}
	public Mat22D(double a00, double a10,
			  double a01, double a11) {
		super(2, a00, a10, a01, a11);
	}

}
