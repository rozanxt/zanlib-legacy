package zan.lib.util.math;

public class Mat44D extends MatD {
	
	public Mat44D() {super(4);}
	public Mat44D(boolean identity) {super(4, identity);}
	public Mat44D(double value) {super(4, value);}
	public Mat44D(MatD matrix) {super(4, matrix);}
	public Mat44D(Vec4D vec0, Vec4D vec1, Vec4D vec2, Vec4D vec3) {super(vec0, vec1, vec2, vec3);}
	public Mat44D(double a00, double a10, double a20, double a30,
			  double a01, double a11, double a21, double a31,
			  double a02, double a12, double a22, double a32,
			  double a03, double a13, double a23, double a33) {
		super(4, a00, a10, a20, a30, a01, a11, a21, a31, a02, a12, a22, a32, a03, a13, a23, a33);
	}
	
}
