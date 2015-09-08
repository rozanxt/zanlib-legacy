package zan.lib.util.math;

public class VecUtil {

	public static void add(VecD left, VecD right, VecD result) {
		if (checkSize(left, right, result)) {
			for (int i=0;i<result.size();i++) result.set(i, left.get(i) + right.get(i));
		}
	}
	public static VecD add(VecD left, VecD right) {
		VecD result = new VecD(left.size());
		add(left, right, result);
		return result;
	}

	public static void sub(VecD left, VecD right, VecD result) {
		if (checkSize(left, right, result)) {
			for (int i=0;i<result.size();i++) result.set(i, left.get(i) - right.get(i));
		}
	}
	public static VecD sub(VecD left, VecD right) {
		VecD result = new VecD(left.size());
		sub(left, right, result);
		return result;
	}

	public static double dot(VecD left, VecD right) {
		if (!checkSize(left, right)) return 0.0;
		double product = 0.0;
		for (int i=0;i<left.size();i++) product += left.get(i) * right.get(i);
		return product;
	}
	public static double project(VecD left, VecD right) {
		return dot(left, right) / right.length();
	}

	public static double cross(Vec2D left, Vec2D right) {
		return left.getX() * right.getY() - left.getY() * right.getX();
	}
	public static void cross(Vec2D vector, double scalar, Vec2D result) {
		result.setComponents(vector.getY() * scalar, -vector.getX()* scalar);
	}
	public static void cross(double scalar, Vec2D vector, Vec2D result) {
		result.setComponents(-vector.getY() * scalar, vector.getX()* scalar);
	}
	public static void cross(Vec3D left, Vec3D right, Vec3D result) {
		double x = left.getY() * right.getZ() - left.getZ() * right.getY();
		double y = left.getZ() * right.getX() - left.getX() * right.getZ();
		double z = left.getX() * right.getY() - left.getY() * right.getX();
		result.setComponents(x, y, z);
	}
	public static Vec2D cross(Vec2D vector, double scalar) {
		Vec2D result = new Vec2D();
		cross(vector, scalar, result);
		return result;
	}
	public static Vec2D cross(double scalar, Vec2D vector) {
		Vec2D result = new Vec2D();
		cross(scalar, vector, result);
		return result;
	}
	public static Vec3D cross(Vec3D left, Vec3D right) {
		Vec3D result = new Vec3D();
		cross(left, right, result);
		return result;
	}

	private static boolean checkSize(VecD... vectors) {
		if (vectors.length < 2) return true;
		for (int i=0;i<vectors.length-1;i++) {
			if (vectors[i].size() != vectors[i+1].size()) {
				System.err.println("Incompatible vectors!");
				return false;
			}
		}
		return true;
	}

}
