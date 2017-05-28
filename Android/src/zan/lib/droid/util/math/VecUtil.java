package zan.lib.droid.util.math;

import android.util.Log;

public class VecUtil {
	
	public static void add(VecF left, VecF right, VecF result) {
		if (checkSize(left, right, result)) {
			for (int i=0;i<result.size();i++) result.set(i, left.get(i) + right.get(i));
		}
	}
	public static VecF add(VecF left, VecF right) {
		VecF result = new VecF(left.size());
		add(left, right, result);
		return result;
	}
	
	public static void sub(VecF left, VecF right, VecF result) {
		if (checkSize(left, right, result)) {
			for (int i=0;i<result.size();i++) result.set(i, left.get(i) - right.get(i));
		}
	}
	public static VecF sub(VecF left, VecF right) {
		VecF result = new VecF(left.size());
		sub(left, right, result);
		return result;
	}
	
	public static float dot(VecF left, VecF right) {
		if (!checkSize(left, right)) return 0f;
		float product = 0f;
		for (int i=0;i<left.size();i++) product += left.get(i) * right.get(i);
		return product;
	}
	public static float project(VecF left, VecF right) {
		return dot(left, right) / right.length();
	}
	
	public static float cross(Vec2F left, Vec2F right) {
		return left.getX() * right.getY() - left.getY() * right.getX();
	}
	public static void cross(Vec2F vector, float scalar, Vec2F result) {
		result.setComponents(vector.getY() * scalar, -vector.getX()* scalar);
	}
	public static void cross(float scalar, Vec2F vector, Vec2F result) {
		result.setComponents(-vector.getY() * scalar, vector.getX()* scalar);
	}
	public static void cross(Vec3F left, Vec3F right, Vec3F result) {
		float x = left.getY() * right.getZ() - left.getZ() * right.getY();
		float y = left.getZ() * right.getX() - left.getX() * right.getZ();
		float z = left.getX() * right.getY() - left.getY() * right.getX();
		result.setComponents(x, y, z);
	}
	public static Vec2F cross(Vec2F vector, float scalar) {
		Vec2F result = new Vec2F();
		cross(vector, scalar, result);
		return result;
	}
	public static Vec2F cross(float scalar, Vec2F vector) {
		Vec2F result = new Vec2F();
		cross(scalar, vector, result);
		return result;
	}
	public static Vec3F cross(Vec3F left, Vec3F right) {
		Vec3F result = new Vec3F();
		cross(left, right, result);
		return result;
	}
	
	private static boolean checkSize(VecF... vectors) {
		if (vectors.length < 2) return true;
		for (int i=0;i<vectors.length-1;i++) {
			if (vectors[i].size() != vectors[i+1].size()) {
				Log.e("VecUtil", "Incompatible vectors!");
				return false;
			}
		}
		return true;
	}
	
}
