package zan.lib.math.linalg;

public class Vec4D implements IVecD<Vec4D> {

	public final double x, y, z, w;

	public Vec4D(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	@Override
	public int size() {return 4;}

	@Override
	public double get(int index) {
		switch (index) {
		case 0:
			return x;
		case 1:
			return y;
		case 2:
			return z;
		case 3:
			return w;
		default:
			System.err.println("Warning: Illegal index argument");
			return 0.0;
		}
	}

	@Override
	public Vec4D add(Vec4D v) {return new Vec4D(x + v.x, y + v.y, z + v.z, w + v.w);}

	@Override
	public Vec4D sub(Vec4D v) {return new Vec4D(x - v.x, y - v.y, z - v.z, w - v.w);}

	@Override
	public Vec4D scalar(double s) {return new Vec4D(s * x, s * y, s * z, s * w);}

	@Override
	public Vec4D negate() {return scalar(-1.0);}

	@Override
	public double dot(Vec4D v) {return x * v.x + y * v.y + z * v.z + w * v.w;}

	@Override
	public double length() {return Math.sqrt(dot(this));}

	@Override
	public Vec4D normalize() {return scalar(1.0 / length());}

	@Override
	public boolean is(Vec4D v) {return (x == v.x && y == v.y && z == v.z && w == v.w);}

	@Override
	public String toString() {return "(" + x + "," + y + "," + z + "," + w + ")";}

}
