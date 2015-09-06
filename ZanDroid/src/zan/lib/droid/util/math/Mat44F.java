package zan.lib.droid.util.math;

public class Mat44F extends MatF {
	
	public Mat44F() {super(4);}
	public Mat44F(boolean identity) {super(4, identity);}
	public Mat44F(float value) {super(4, value);}
	public Mat44F(MatF matrix) {super(4, matrix);}
	public Mat44F(Vec4F vec0, Vec4F vec1, Vec4F vec2, Vec4F vec3) {super(vec0, vec1, vec2, vec3);}
	public Mat44F(float a00, float a10, float a20, float a30,
				  float a01, float a11, float a21, float a31,
				  float a02, float a12, float a22, float a32,
				  float a03, float a13, float a23, float a33) {
		super(4, a00, a10, a20, a30, a01, a11, a21, a31, a02, a12, a22, a32, a03, a13, a23, a33);
	}
	
}
