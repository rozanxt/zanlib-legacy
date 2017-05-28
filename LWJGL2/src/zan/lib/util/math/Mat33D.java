package zan.lib.util.math;

public class Mat33D extends MatD {

	public Mat33D() {super(3);}
	public Mat33D(boolean identity) {super(3, identity);}
	public Mat33D(double value) {super(3, value);}
	public Mat33D(MatD matrix) {super(3, matrix);}
	public Mat33D(Vec3D vec0, Vec3D vec1, Vec3D vec2) {super(vec0, vec1, vec2);}
	public Mat33D(double a00, double a10, double a20,
			  double a01, double a11, double a21,
			  double a02, double a12, double a22) {
		super(3, a00, a10, a20, a01, a11, a21, a02, a12, a22);
	}

}
