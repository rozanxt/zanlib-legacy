package zan.lib.util.math;

public final class Vec2D implements IVecD<Vec2D> {

	public final double x, y;

	public Vec2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double cross(Vec2D v) {return x * v.y - v.x * y;}

	@Override
	public int size() {return 2;}

	@Override
	public double get(int component) {
		switch (component) {
		case 0:
			return x;
		case 1:
			return y;
		default:
			return 0.0;
		}
	}

	@Override
	public Vec2D add(Vec2D v) {return new Vec2D(x + v.x, y + v.y);}

	@Override
	public Vec2D sub(Vec2D v) {return new Vec2D(x - v.x, y - v.y);}

	@Override
	public Vec2D scalar(double s) {return new Vec2D(s * x, s * y);}

	@Override
	public Vec2D negate() {return scalar(-1.0);}

	@Override
	public double dot(Vec2D v) {return x * v.x + y * v.y;}

	@Override
	public double length() {return Math.sqrt(dot(this));}

	@Override
	public Vec2D normalize() {return scalar(1.0/length());}

	@Override
	public boolean is(Vec2D v) {return (x == v.x && y == v.y);}

	@Override
	public String toString() {return "(" + x + "," + y + ")";}

}
