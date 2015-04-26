package zan.lib.math.vector;

public class VecUtil {
	
	// Addition
	
	public static void add(VecD left, VecD right, VecD result) {
		if (!checkSize(left, right, result)) return;
		for (int i=0;i<result.size();i++) result.set(i, left.get(i) + right.get(i));
	}
	
	// Subtraction
	
	public static void sub(VecD left, VecD right, VecD result) {
		if (!checkSize(left, right, result)) return;
		for (int i=0;i<result.size();i++) result.set(i, left.get(i) - right.get(i));
	}
	
	// Dot product
	
	public static double dot(VecD left, VecD right) {
		if (!checkSize(left, right)) return 0.0;
		double product = 0.0;
		for (int i=0;i<left.size();i++) product += left.get(i) * right.get(i);
		return product;
	}
	
	// Cross product
	
	public static double cross(Vec2D left, Vec2D right) {
		return left.getX() * right.getY() - left.getY() * right.getX();
	}
	public static void cross(Vec2D origin, double scalar, Vec2D result) {
		result.setComponents(origin.getY() * scalar, -origin.getX()* scalar);
	}
	public static void cross(double scalar, Vec2D origin, Vec2D result) {
		result.setComponents(-origin.getY() * scalar, origin.getX()* scalar);
	}
	public static void cross(Vec3D left, Vec3D right, Vec3D result) {
		double x = left.getY() * right.getZ() - left.getZ() * right.getY();
		double y = left.getZ() * right.getX() - left.getX() * right.getZ();
		double z = left.getX() * right.getY() - left.getY() * right.getX();
		result.setComponents(x, y, z);
	}
	
	// Utilities
	
	private static boolean checkSize(VecD... vectors) {
		for (int i=0;i<vectors.length-1;i++) {
			if (vectors[i].size() != vectors[i+1].size()) {
				System.err.println("Incompatible vectors!");
				return false;
			}
		}
		return true;
	}
	
}
