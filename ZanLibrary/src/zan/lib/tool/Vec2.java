package zan.lib.tool;

public class Vec2 {
	
	public double x, y;
	
	public Vec2() {set(0.0, 0.0);}
	public Vec2(double x, double y) {set(x, y);}
	
	public void set(double x, double y) {this.x = x; this.y = y;}
	public void setX(double x) {this.x = x;}
	public void setY(double y) {this.y = y;}
	public double getX() {return x;}
	public double getY() {return y;}
	
	public double length() {return Math.sqrt(x*x+y*y);}
	public double length2() {return x*x+y*y;}
	
	public Vec2 normalize() {
		double len = 1.0/length();
		set(x*len, y*len);
		return this;
	}
	public Vec2 normalize(Vec2 dest) {
		double len = 1.0/length();
		if (dest == null) return new Vec2(x*len, y*len);
		dest.set(x*len, y*len);
		return dest;
	}
	
	public Vec2 negate() {
		set(-x, -y);
		return this;
	}
	public Vec2 negate(Vec2 dest) {
		if (dest == null) return new Vec2(-x, -y);
		dest.set(-x, -y);
		return dest;
	}
	
	public Vec2 scalar(double scalar) {
		set(scalar*x, scalar*y);
		return this;
	}
	public Vec2 scalar(double scalar, Vec2 dest) {
		if (dest == null) return new Vec2(scalar*x, scalar*y);
		dest.set(scalar*x, scalar*y);
		return dest;
	}
	
	public static Vec2 add(Vec2 left, Vec2 right, Vec2 dest) {
		double sx = left.x + right.x;
		double sy = left.y + right.y;
		if (dest == null) return new Vec2(sx, sy);
		dest.set(sx, sy);
		return dest;
	}
	
	public static Vec2 sub(Vec2 left, Vec2 right, Vec2 dest) {
		double sx = left.x - right.x;
		double sy = left.y - right.y;
		if (dest == null) return new Vec2(sx, sy);
		dest.set(sx, sy);
		return dest;
	}
	
	public static double dot(Vec2 left, Vec2 right) {
		return left.x*right.x + left.y*right.y;
	}
	
	public static double cross(Vec2 left, Vec2 right) {
		return left.x*right.y - left.y*right.x;
	}
	
	public static Vec2 cross(Vec2 vector, double scalar, Vec2 dest) {
		double sx = scalar*vector.y;
		double sy = -scalar*vector.x;
		if (dest == null) return new Vec2(sx, sy);
		dest.set(sx, sy);
		return dest;
	}
	
	public static Vec2 cross(double scalar, Vec2 vector, Vec2 dest) {
		double sx = -scalar*vector.y;
		double sy = scalar*vector.x;
		if (dest == null) return new Vec2(sx, sy);
		dest.set(sx, sy);
		return dest;
	}
	
}
