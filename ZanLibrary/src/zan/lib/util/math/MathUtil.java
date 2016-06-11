package zan.lib.util.math;

public final class MathUtil {

	public static double[] createZeroD(int size) {
		double[] data = new double[size];
		for (int i=0;i<size;i++) data[i] = 0.0;
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
	public static final VecD zeroVecD(int size) {return new VecD(createZeroD(size));}

	public static final Vec2D unitXVec2D = new Vec2D(1.0, 0.0);
	public static final Vec2D unitYVec2D = new Vec2D(0.0, 1.0);
	public static final Vec3D unitXVec3D = new Vec3D(1.0, 0.0, 0.0);
	public static final Vec3D unitYVec3D = new Vec3D(0.0, 1.0, 0.0);
	public static final Vec3D unitZVec3D = new Vec3D(0.0, 1.0, 0.0);
	public static final Vec4D unitXVec4D = new Vec4D(1.0, 0.0, 0.0, 0.0);
	public static final Vec4D unitYVec4D = new Vec4D(0.0, 1.0, 0.0, 0.0);
	public static final Vec4D unitZVec4D = new Vec4D(0.0, 0.0, 1.0, 0.0);
	public static final Vec4D unitWVec4D = new Vec4D(0.0, 0.0, 0.0, 1.0);
	public static final VecD unitVecD(int size, int unit) {return new VecD(createUnitD(size, unit));}

	public static final MatD zeroMat22D = zeroMatD(2, 2);
	public static final MatD zeroMat33D = zeroMatD(3, 3);
	public static final MatD zeroMat44D = zeroMatD(4, 4);
	public static final MatD zeroMatD(int rows, int cols) {return new MatD(rows, cols, createZeroD(rows * cols));}

	public static final MatD idMat22D = idMatD(2);
	public static final MatD idMat33D = idMatD(3);
	public static final MatD idMat44D = idMatD(4);
	public static final MatD idMatD(int dim) {return new MatD(dim, dim, createIdD(dim));}

	public static MatD translationMat22D(double x) {
		double[] result = createIdD(2);
		result[2*0+1] = x;
		return new MatD(2, 2, result);
	}
	public static MatD translationMat33D(double x, double y) {
		double[] result = createIdD(3);
		result[3*0+2] = x;
		result[3*1+2] = y;
		return new MatD(3, 3, result);
	}
	public static MatD translationMat44D(double x, double y, double z) {
		double[] result = createIdD(4);
		result[4*0+3] = x;
		result[4*1+3] = y;
		result[4*2+3] = z;
		return new MatD(4, 4, result);
	}

	public static MatD scaleMat22D(double x) {
		double[] result = createIdD(2);
		result[2*0+0] = x;
		return new MatD(2, 2, result);
	}
	public static MatD scaleMat33D(double x, double y) {
		double[] result = createIdD(3);
		result[3*0+0] = x;
		result[3*1+1] = y;
		return new MatD(3, 3, result);
	}
	public static MatD scaleMat44D(double x, double y, double z) {
		double[] result = createIdD(4);
		result[4*0+0] = x;
		result[4*1+1] = y;
		result[4*2+2] = z;
		return new MatD(4, 4, result);
	}

	public static MatD rotationMat33D(double angle) {
		double rad = Math.toRadians(angle);
		double sin = Math.sin(rad);
		double cos = Math.cos(rad);
		double[] result = createIdD(3);
		result[3*0+0] = cos;
		result[3*0+1] = -sin;
		result[3*1+0] = sin;
		result[3*1+1] = cos;
		return new MatD(3, 3, result);
	}
	public static MatD rotationMat44D(double angle, double x, double y, double z) {
		Vec3D axis = new Vec3D(x, y, z).normalize();
		double a = axis.x;
		double b = axis.y;
		double c = axis.z;
		double rad = Math.toRadians(angle);
		double sin = Math.sin(rad);
		double cos = Math.cos(rad);
		double[] result = createIdD(4);
		result[4*0+0] = a*a*(1-cos)+cos;
		result[4*0+1] = a*b*(1-cos)-c*sin;
		result[4*0+2] = a*c*(1-cos)+b*sin;
		result[4*1+0] = b*a*(1-cos)+c*sin;
		result[4*1+1] = b*b*(1-cos)+cos;
		result[4*1+2] = b*c*(1-cos)-a*sin;
		result[4*2+0] = c*a*(1-cos)-b*sin;
		result[4*2+1] = c*b*(1-cos)+a*sin;
		result[4*2+2] = c*c*(1-cos)+cos;
		return new MatD(4, 4, result);
	}

	public static MatD orthoProjectionMat44D(double left, double right, double bottom, double top, double near, double far) {
		double sx = 2.0/(right-left);
		double sy = 2.0/(top-bottom);
		double sz = -2.0/(far-near);
		double tx = (right+left)/(right-left);
		double ty = (top+bottom)/(top-bottom);
		double tz = (far+near)/(far-near);
		double[] result = createIdD(4);
		result[4*0+0] = sx;
		result[4*1+1] = sy;
		result[4*2+2] = sz;
		result[4*0+3] = -tx;
		result[4*1+3] = -ty;
		result[4*2+3] = -tz;
		return new MatD(4, 4, result);
	}
	public static MatD perspectiveProjectionMat44D(double fovy, double ratio, double near, double far) {
		double f = 1.0/Math.tan(fovy/2.0);
		double sx = f/ratio;
		double sy = f;
		double sz = (near+far)/(near-far);
		double tz = (2*near*far)/(near-far);

		double[] result = createIdD(4);
		result[4*0+0] = sx;
		result[4*1+1] = sy;
		result[4*2+2] = sz;
		result[4*2+3] = tz;
		result[4*3+2] = -1.0;
		result[4*3+3] = 0.0;
		return new MatD(4, 4, result);
	}

	public static <V extends IVecSpaceD<V>> boolean is(V left, V right) {return left.is(right);}
	public static <V extends IVecSpaceD<V>> V add(V left, V right) {return left.add(right);}
	public static <V extends IVecSpaceD<V>> V sub(V left, V right) {return left.sub(right);}
	public static <V extends IVecSpaceD<V>> V scalar(double scalar, V vector) {return vector.scalar(scalar);}
	public static <V extends IVecD<V>> double dot(V left, V right) {return left.dot(right);}
	public static <M extends IMatD<M>> M mult(M left, M right) {return left.mult(right);}

}
