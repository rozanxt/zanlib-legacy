package zan.lib.droid.util.math;

public class Mat22F extends MatF {
	
	public Mat22F() {super(2);}
	public Mat22F(boolean identity) {super(2, identity);}
	public Mat22F(float value) {super(2, value);}
	public Mat22F(MatF matrix) {super(2, matrix);}
	public Mat22F(Vec2F vec0, Vec2F vec1) {super(vec0, vec1);}
	public Mat22F(float a00, float a10,
				  float a01, float a11) {
		super(2, a00, a10, a01, a11);
	}
	
}
