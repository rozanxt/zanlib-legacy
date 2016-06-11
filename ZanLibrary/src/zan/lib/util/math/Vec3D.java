package zan.lib.util.math;

public final class Vec3D implements IVecD<Vec3D> {

	public final double x, y, z;

	public Vec3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vec3D cross(Vec3D v) {return new Vec3D(y * v.z - v.y * z, z * v.x - v.z * x, x * v.y - v.x * y);}

	@Override
	public int size() {return 3;}

	@Override
	public double get(int component) {
		switch (component) {
		case 0:
			return x;
		case 1:
			return y;
		case 2:
			return z;
		default:
			return 0.0;
		}
	}

	@Override
	public Vec3D add(Vec3D v) {return new Vec3D(x + v.x, y + v.y, z + v.z);}

	@Override
	public Vec3D sub(Vec3D v) {return new Vec3D(x - v.x, y - v.y, z + v.z);}

	@Override
	public Vec3D scalar(double s) {return new Vec3D(s * x, s * y, s * z);}

	@Override
	public Vec3D negate() {return scalar(-1.0);}

	@Override
	public double dot(Vec3D v) {return x * v.x + y * v.y + z * v.z;}

	@Override
	public double length() {return Math.sqrt(dot(this));}

	@Override
	public Vec3D normalize() {return scalar(1.0/length());}

	@Override
	public boolean is(Vec3D v) {return (x == v.x && y == v.y && z == v.z);}

	@Override
	public String toString() {return "(" + x + "," + y + "," + z + ")";}

}
