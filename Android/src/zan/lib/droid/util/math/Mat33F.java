package zan.lib.droid.util.math;

public class Mat33F extends MatF {
	
	public Mat33F() {super(3);}
	public Mat33F(boolean identity) {super(3, identity);}
	public Mat33F(float value) {super(3, value);}
	public Mat33F(MatF matrix) {super(3, matrix);}
	public Mat33F(Vec3F vec0, Vec3F vec1, Vec3F vec2) {super(vec0, vec1, vec2);}
	public Mat33F(float a00, float a10, float a20,
				  float a01, float a11, float a21,
				  float a02, float a12, float a22) {
		super(3, a00, a10, a20, a01, a11, a21, a02, a12, a22);
	}
	
}
