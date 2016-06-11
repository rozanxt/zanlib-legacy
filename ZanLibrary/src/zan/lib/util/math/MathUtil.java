package zan.lib.util.math;

public final class MathUtil {

	public static double[] zeroD(int size) {
		double[] zero = new double[size];
		for (int i=0;i<size;i++) zero[i] = 0.0;
		return zero;
	}

	public static double[] unitD(int size, int unit) {
		double[] zero = new double[size];
		for (int i=0;i<size;i++) {
			if (i == unit) zero[i] = 1.0;
			else zero[i] = 0.0;
		}
		return zero;
	}

	public static double[] idD(int dim) {
		double[] id = new double[dim * dim];
		for (int i=0;i<dim;i++) {
			for (int j=0;j<dim;j++) {
				if (i == j) id[dim*i+j] = 1.0;
				else id[dim*i+j] = 0.0;
			}
		}
		return id;
	}

	public static final Vec2D zeroVec2D = new Vec2D(0.0, 0.0);
	public static final Vec3D zeroVec3D = new Vec3D(0.0, 0.0, 0.0);
	public static final Vec4D zeroVec4D = new Vec4D(0.0, 0.0, 0.0, 0.0);
	public static final VecD zeroVecD(int size) {return new VecD(zeroD(size));}

	public static final Mat22D zeroMat22D = new Mat22D(0.0, 0.0, 0.0, 0.0);
	public static final Mat33D zeroMat33D = new Mat33D(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
	public static final Mat44D zeroMat44D = new Mat44D(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
	public static final MatD zeroMatD(int rows, int cols) {return new MatD(rows, cols, zeroD(rows *cols));}

	public static final Mat22D idMat22D = new Mat22D(1.0, 0.0, 0.0, 1.0);
	public static final Mat33D idMat33D = new Mat33D(1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0);
	public static final Mat44D idMat44D = new Mat44D(1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0);
	public static final MatD idMatD(int dim) {return new MatD(dim, dim, idD(dim));}

	public static Mat22D translationMat22D(double x) {
		double[] result = idD(2);
		result[2*0+1] = x;
		return new Mat22D(result);
	}
	public static Mat33D translationMat33D(double x, double y) {
		double[] result = idD(3);
		result[3*0+2] = x;
		result[3*1+2] = y;
		return new Mat33D(result);
	}
	public static Mat44D translationMat44D(double x, double y, double z) {
		double[] result = idD(4);
		result[4*0+3] = x;
		result[4*1+3] = y;
		result[4*2+3] = z;
		return new Mat44D(result);
	}

	public static Mat22D scaleMat22D(double x) {
		double[] result = idD(2);
		result[2*0+0] = x;
		return new Mat22D(result);
	}
	public static Mat33D scaleMat33D(double x, double y) {
		double[] result = idD(3);
		result[3*0+0] = x;
		result[3*1+1] = y;
		return new Mat33D(result);
	}
	public static Mat44D scaleMat44D(double x, double y, double z) {
		double[] result = idD(4);
		result[4*0+0] = x;
		result[4*1+1] = y;
		result[4*2+2] = z;
		return new Mat44D(result);
	}

	public static Mat33D rotationMat33D(double angle) {
		double rad = Math.toRadians(angle);
		double sin = Math.sin(rad);
		double cos = Math.cos(rad);
		double[] result = idD(3);
		result[3*0+0] = cos;
		result[3*0+1] = -sin;
		result[3*1+0] = sin;
		result[3*1+1] = cos;
		return new Mat33D(result);
	}
	public static Mat44D rotationMat44D(double angle, double x, double y, double z) {
		Vec3D axis = new Vec3D(x, y, z).normalize();
		double a = axis.x;
		double b = axis.y;
		double c = axis.z;
		double rad = Math.toRadians(angle);
		double sin = Math.sin(rad);
		double cos = Math.cos(rad);
		double[] result = idD(4);
		result[4*0+0] = a*a*(1-cos)+cos;
		result[4*0+1] = a*b*(1-cos)-c*sin;
		result[4*0+2] = a*c*(1-cos)+b*sin;
		result[4*1+0] = b*a*(1-cos)+c*sin;
		result[4*1+1] = b*b*(1-cos)+cos;
		result[4*1+2] = b*c*(1-cos)-a*sin;
		result[4*2+0] = c*a*(1-cos)-b*sin;
		result[4*2+1] = c*b*(1-cos)+a*sin;
		result[4*2+2] = c*c*(1-cos)+cos;
		return new Mat44D(result);
	}

	public static Mat44D orthoProjectionMatrix(double left, double right, double bottom, double top, double near, double far) {
		double sx = 2.0/(right-left);
		double sy = 2.0/(top-bottom);
		double sz = -2.0/(far-near);
		double tx = (right+left)/(right-left);
		double ty = (top+bottom)/(top-bottom);
		double tz = (far+near)/(far-near);
		double[] result = idD(4);
		result[4*0+0] = sx;
		result[4*1+1] = sy;
		result[4*2+2] = sz;
		result[4*0+3] = -tx;
		result[4*1+3] = -ty;
		result[4*2+3] = -tz;
		return new Mat44D(result);
	}

	public static Mat44D perspectiveProjectionMatrix(double fovy, double aspect, double near, double far) {
		double f = 1.0/Math.tan(fovy/2.0);
		double sx = f/aspect;
		double sy = f;
		double sz = (near+far)/(near-far);
		double tz = (2*near*far)/(near-far);

		double[] result = idD(4);
		result[4*0+0] = sx;
		result[4*1+1] = sy;
		result[4*2+2] = sz;
		result[4*2+3] = tz;
		result[4*3+2] = -1.0;
		result[4*3+3] = 0.0;
		return new Mat44D(result);
	}

}
