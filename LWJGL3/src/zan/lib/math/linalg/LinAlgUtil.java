package zan.lib.math.linalg;

public class LinAlgUtil {

	public static double[] createZeroD(int size) {
		double[] data = new double[size];
		for (int i=0;i<size;i++) data[i] = 0.0;
		return data;
	}
	public static double[] createOneD(int size) {
		double[] data = new double[size];
		for (int i=0;i<size;i++) data[i] = 1.0;
		return data;
	}
	public static double[] createUnitD(int size, int unit) {
		double[] data = new double[size];
		for (int i=0;i<size;i++) {
			if (i == unit) data[i] = 1.0;
			else data[i] = 0.0;
		}
		return data;
	}
	public static double[] createIdD(int dim) {
		double[] data = new double[dim * dim];
		for (int i=0;i<dim;i++) {
			for (int j=0;j<dim;j++) {
				if (i == j) data[dim*i+j] = 1.0;
				else data[dim*i+j] = 0.0;
			}
		}
		return data;
	}

	public static final Vec2D zeroVec2D = new Vec2D(0.0, 0.0);
	public static final Vec3D zeroVec3D = new Vec3D(0.0, 0.0, 0.0);
	public static final Vec4D zeroVec4D = new Vec4D(0.0, 0.0, 0.0, 0.0);
	public static VecD zeroVecD(int size) {return new VecD(createZeroD(size));}

	public static final Vec2D oneVec2D = new Vec2D(1.0, 1.0);
	public static final Vec3D oneVec3D = new Vec3D(1.0, 1.0, 1.0);
	public static final Vec4D oneVec4D = new Vec4D(1.0, 1.0, 1.0, 1.0);
	public static VecD oneVecD(int size) {return new VecD(createOneD(size));}

	public static final Vec2D unitXVec2D = new Vec2D(1.0, 0.0);
	public static final Vec2D unitYVec2D = new Vec2D(0.0, 1.0);
	public static final Vec3D unitXVec3D = new Vec3D(1.0, 0.0, 0.0);
	public static final Vec3D unitYVec3D = new Vec3D(0.0, 1.0, 0.0);
	public static final Vec3D unitZVec3D = new Vec3D(0.0, 0.0, 1.0);
	public static final Vec4D unitXVec4D = new Vec4D(1.0, 0.0, 0.0, 0.0);
	public static final Vec4D unitYVec4D = new Vec4D(0.0, 1.0, 0.0, 0.0);
	public static final Vec4D unitZVec4D = new Vec4D(0.0, 0.0, 1.0, 0.0);
	public static final Vec4D unitWVec4D = new Vec4D(0.0, 0.0, 0.0, 1.0);
	public static VecD unitVecD(int size, int unit) {return new VecD(createUnitD(size, unit));}

	public static final Mat22D zeroMat22D = new Mat22D(0.0, 0.0, 0.0, 0.0);
	public static final Mat33D zeroMat33D = new Mat33D(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
	public static final Mat44D zeroMat44D = new Mat44D(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
	public static MatD zeroMatD(int rows, int cols) {return new MatD(rows, cols, createZeroD(rows * cols));}

	public static final Mat22D idMat22D = new Mat22D(1.0, 0.0, 0.0, 1.0);
	public static final Mat33D idMat33D = new Mat33D(1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0);
	public static final Mat44D idMat44D = new Mat44D(1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0);
	public static MatD idMatD(int dim) {return new MatD(dim, dim, createIdD(dim));}

	public static Mat22D translationMat22D(double x) {
		return new Mat22D(1.0, x,
						  0.0, 1.0);
	}
	public static Mat33D translationMat33D(double x, double y) {
		return new Mat33D(1.0, 0.0, x,
						  0.0, 1.0, y,
						  0.0, 0.0, 1.0);
	}
	public static Mat44D translationMat44D(double x, double y, double z) {
		return new Mat44D(1.0, 0.0, 0.0, x,
						  0.0, 1.0, 0.0, y,
						  0.0, 0.0, 1.0, z,
						  0.0, 0.0, 0.0, 1.0);
	}

	public static Mat22D scaleMat22D(double x) {
		return new Mat22D(  x, 0.0,
						  0.0, 1.0);
	}
	public static Mat22D scaleMat22D(double x, double y) {
		return new Mat22D(  x, 0.0,
						  0.0,   y);
	}
	public static Mat33D scaleMat33D(double x, double y) {
		return new Mat33D(  x, 0.0, 0.0,
						  0.0,   y, 0.0,
						  0.0, 0.0, 1.0);
	}
	public static Mat33D scaleMat33D(double x, double y, double z) {
		return new Mat33D(  x, 0.0, 0.0,
						  0.0,   y, 0.0,
						  0.0, 0.0,   z);
	}
	public static Mat44D scaleMat44D(double x, double y, double z) {
		return new Mat44D(  x, 0.0, 0.0, 0.0,
						  0.0,   y, 0.0, 0.0,
						  0.0, 0.0,   z, 0.0,
						  0.0, 0.0, 0.0, 1.0);
	}

	public static Mat22D rotationMat22D(double angle) {
		double rad = Math.toRadians(angle);
		double sin = Math.sin(rad);
		double cos = Math.cos(rad);
		return new Mat22D(cos, -sin,
						  sin, cos);
	}
	public static Mat33D rotationXMat33D(double angle) {
		double rad = Math.toRadians(angle);
		double sin = Math.sin(rad);
		double cos = Math.cos(rad);
		return new Mat33D(1.0, 0.0, 0.0,
						  0.0, cos, -sin,
						  0.0, sin, cos);
	}
	public static Mat33D rotationYMat33D(double angle) {
		double rad = Math.toRadians(angle);
		double sin = Math.sin(rad);
		double cos = Math.cos(rad);
		return new Mat33D(cos, 0.0, -sin,
						  0.0, 1.0, 0.0,
						  sin, 0.0, cos);
	}
	public static Mat33D rotationZMat33D(double angle) {
		double rad = Math.toRadians(angle);
		double sin = Math.sin(rad);
		double cos = Math.cos(rad);
		return new Mat33D(cos, -sin, 0.0,
						  sin, cos, 0.0,
						  0.0, 0.0, 1.0);
	}
	public static Mat33D rotationMat33D(double angle, double x, double y, double z) {
		Vec3D axis = new Vec3D(x, y, z).normalize();
		double a = axis.x;
		double b = axis.y;
		double c = axis.z;
		double rad = Math.toRadians(angle);
		double sin = Math.sin(rad);
		double cos = Math.cos(rad);
		return new Mat33D(a*a*(1-cos)+cos, a*b*(1-cos)-c*sin, a*c*(1-cos)+b*sin,
						  b*a*(1-cos)+c*sin, b*b*(1-cos)+cos, b*c*(1-cos)-a*sin,
						  c*a*(1-cos)-b*sin, c*b*(1-cos)+a*sin, c*c*(1-cos)+cos);
	}
	public static Mat44D rotationMat44D(double angle, double x, double y, double z) {
		Vec3D axis = new Vec3D(x, y, z).normalize();
		double a = axis.x;
		double b = axis.y;
		double c = axis.z;
		double rad = Math.toRadians(angle);
		double sin = Math.sin(rad);
		double cos = Math.cos(rad);
		return new Mat44D(a*a*(1-cos)+cos, a*b*(1-cos)-c*sin, a*c*(1-cos)+b*sin, 0.0,
						  b*a*(1-cos)+c*sin, b*b*(1-cos)+cos, b*c*(1-cos)-a*sin, 0.0,
						  c*a*(1-cos)-b*sin, c*b*(1-cos)+a*sin, c*c*(1-cos)+cos, 0.0,
						  0.0, 0.0, 0.0, 1.0);
	}
	public static Mat33D rotationMat33D(double angle, Vec3D axis) {return rotationMat33D(angle, axis.x, axis.y, axis.z);}
	public static Mat44D rotationMat44D(double angle, Vec3D axis) {return rotationMat44D(angle, axis.x, axis.y, axis.z);}

	public static Mat44D orientationMat44D(Vec3D front, Vec3D up) {
		Vec3D c = front.normalize();
		Vec3D a = up.cross(c).normalize();
		Vec3D b = c.cross(a);
		return new Mat44D(a.x, b.x, c.x, 0.0,
				  a.y, b.y, c.y, 0.0,
				  a.z, b.z, c.z, 0.0,
				  0.0, 0.0, 0.0, 1.0);
	}

	public static Mat44D lookAtViewMat44D(Vec3D pos, Vec3D lookAt, Vec3D up) {
		Vec3D c = pos.sub(lookAt).normalize();
		Vec3D a = up.cross(c).normalize();
		Vec3D b = c.cross(a);
		return new Mat44D(a.x, b.x, c.x, 0.0,
						  a.y, b.y, c.y, 0.0,
						  a.z, b.z, c.z, 0.0,
						  0.0, 0.0, 0.0, 1.0).mult(translationMat44D(-pos.x, -pos.y, -pos.z));
	}
	public static Mat44D lookAtViewMat44D(double posX, double posY, double posZ, double lookAtX, double lookAtY, double lookAtZ, double upX, double upY, double upZ) {
		return lookAtViewMat44D(new Vec3D(posX, posY, posZ), new Vec3D(lookAtX, lookAtY, lookAtZ), new Vec3D(upX, upY, upZ));
	}

	public static Mat44D orthoProjectionMat44D(double left, double right, double bottom, double top, double near, double far) {
		double sx = 2.0/(right-left);
		double sy = 2.0/(top-bottom);
		double sz = -2.0/(far-near);
		double tx = (right+left)/(right-left);
		double ty = (top+bottom)/(top-bottom);
		double tz = (far+near)/(far-near);
		return new Mat44D(sx, 0.0, 0.0, -tx,
						  0.0, sy, 0.0, -ty,
						  0.0, 0.0, sz, -tz,
						  0.0, 0.0, 0.0, 1.0);
	}
	public static Mat44D perspectiveProjectionMat44D(double fovy, double ratio, double near, double far) {
		double f = 1.0/Math.tan(fovy/2.0);
		double sx = f/ratio;
		double sy = f;
		double sz = (near+far)/(near-far);
		double tz = (2*near*far)/(near-far);
		return new Mat44D(sx, 0.0, 0.0, 0.0,
						  0.0, sy, 0.0, 0.0,
						  0.0, 0.0, sz,  tz,
						  0.0, 0.0, -1.0, 0.0);
	}

	public static Vec2D map(Mat22D mat, Vec2D vec) {
		return new Vec2D(mat.a11 * vec.x + mat.a12 * vec.y,
						 mat.a21 * vec.x + mat.a22 * vec.y);
	}
	public static Vec3D map(Mat33D mat, Vec3D vec) {
		return new Vec3D(mat.a11 * vec.x + mat.a12 * vec.y + mat.a13 * vec.z,
						 mat.a21 * vec.x + mat.a22 * vec.y + mat.a23 * vec.z,
						 mat.a31 * vec.x + mat.a32 * vec.y + mat.a33 * vec.z);
	}
	public static Vec4D map(Mat44D mat, Vec4D vec) {
		return new Vec4D(mat.a11 * vec.x + mat.a12 * vec.y + mat.a13 * vec.z + mat.a14 * vec.w,
						 mat.a21 * vec.x + mat.a22 * vec.y + mat.a23 * vec.z + mat.a24 * vec.w,
						 mat.a31 * vec.x + mat.a32 * vec.y + mat.a33 * vec.z + mat.a34 * vec.w,
						 mat.a41 * vec.x + mat.a42 * vec.y + mat.a43 * vec.z + mat.a44 * vec.w);
	}

	public static <T extends IVecSpaceD<T>> boolean is(T left, T right) {return left.is(right);}
	public static <T extends IVecSpaceD<T>> T add(T left, T right) {return left.add(right);}
	public static <T extends IVecSpaceD<T>> T sub(T left, T right) {return left.sub(right);}
	public static <T extends IVecSpaceD<T>> T scalar(double scalar, T vector) {return vector.scalar(scalar);}
	public static <T extends IMatD<T>> T mult(T left, T right) {return left.mult(right);}
	public static <T extends IVecD<T>> double dot(T left, T right) {return left.dot(right);}
	public static double cross(Vec2D left, Vec2D right) {return left.cross(right);}
	public static Vec3D cross(Vec3D left, Vec3D right) {return left.cross(right);}

}
