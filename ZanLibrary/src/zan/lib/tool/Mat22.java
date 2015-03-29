package zan.lib.tool;

public class Mat22 {
	
	public double m00, m01;
	public double m10, m11;
	
	public Mat22() {set(0.0, 0.0, 0.0, 0.0);}
	public Mat22(double s00, double s01, double s10, double s11) {set(s00, s01, s10, s11);}
	
	public void set(double s00, double s01, double s10, double s11) {m00 = s00; m01 = s01; m10 = s10; m11 = s11;}
	
	public Mat22 scalar(double scalar) {
		set(scalar*m00, scalar*m01, scalar*m10, scalar*m11);
		return this;
	}
	public Mat22 scalar(double scalar, Mat22 dest) {
		double s00 = scalar*m00; double s01 = scalar*m01;
		double s10 = scalar*m10; double s11 = scalar*m11;
		if (dest == null) return new Mat22(s00, s01, s10, s11);
		dest.set(s00, s01, s10, s11);
		return dest;
	}
	
	public Mat22 transpose() {
		set(m00, m10, m01, m11);
		return this;
	}
	public Mat22 transpose(Mat22 dest) {
		if (dest == null) return new Mat22(m00, m10, m01, m11);
		dest.set(m00, m10, m01, m11);
		return dest;
	}
	
	public Mat22 inverse() {
		double idet = 1.0/(m00*m11-m01*m10);
		double s00 = m11; double s01 = -m01;
		double s10 = -m10; double s11 = m00;
		set(s00, s01, s10, s11);
		scalar(idet);
		return this;
	}
	public Mat22 inverse(Mat22 dest) {
		double idet = 1.0/(m00*m11-m01*m10);
		double s00 = m11; double s01 = -m01;
		double s10 = -m10; double s11 = m00;
		if (dest == null) {
			Mat22 m = new Mat22(s00, s01, s10, s11);
			m.scalar(idet);
			return m;
		}
		dest.set(s00, s01, s10, s11);
		dest.scalar(idet);
		return dest;
	}
	
	public double get(int sy, int sx) {
		if (sy == 0) {
			if (sx == 0) return m00;
			else if (sx == 1) return m01;
		} else if (sy == 1) {
			if (sx == 0) return m10;
			else if (sx == 1) return m11;
		}
		return 0.0;
	}
	
	public Vec2 getRow(int row) {
		if (row == 0) return new Vec2(m00, m01);
		else if (row == 1) return new Vec2(m10, m11);
		return null;
	}
	public Vec2 getCol(int col) {
		if (col == 0) return new Vec2(m00, m10);
		else if (col == 1) return new Vec2(m01, m11);
		return null;
	}
	
	public static Mat22 add(Mat22 left, Mat22 right, Mat22 dest) {
		double s00 = left.m00 + right.m00;
		double s01 = left.m01 + right.m01;
		double s10 = left.m10 + right.m10;
		double s11 = left.m11 + right.m11;
		if (dest == null) return new Mat22(s00, s01, s10, s11);
		dest.set(s00, s01, s10, s11);
		return dest;
	}
	public static Mat22 sub(Mat22 left, Mat22 right, Mat22 dest) {
		double s00 = left.m00 - right.m00;
		double s01 = left.m01 - right.m01;
		double s10 = left.m10 - right.m10;
		double s11 = left.m11 - right.m11;
		if (dest == null) return new Mat22(s00, s01, s10, s11);
		dest.set(s00, s01, s10, s11);
		return dest;
	}
	
	public static Mat22 multiply(Mat22 left, Mat22 right, Mat22 dest) {
		double s00 = left.m00*right.m00+left.m01*right.m10;
		double s01 = left.m00*right.m01+left.m01*right.m11;
		double s10 = left.m10*right.m00+left.m11*right.m10;
		double s11 = left.m10*right.m01+left.m11*right.m11;
		if (dest == null) return new Mat22(s00, s01, s10, s11);
		dest.set(s00, s01, s10, s11);
		return dest;
	}
	
}
