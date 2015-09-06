package zan.lib.droid.util.math;

public class Vec3F extends VecF {
	
	public Vec3F() {super(3);}
	public Vec3F(float value) {super(3, value);}
	public Vec3F(float x, float y, float z) {super(x, y, z);}
	public Vec3F(VecF vector) {super(3, vector);}
	
	public void setComponents(float x, float y, float z) {setX(x); setY(y); setZ(z);}
	public void setX(float x) {set(0, x);}
	public void setY(float y) {set(1, y);}
	public void setZ(float z) {set(2, z);}
	
	public float getX() {return get(0);}
	public float getY() {return get(1);}
	public float getZ() {return get(2);}
	
	public float addX(float value) {return add(0, value);}
	public float subX(float value) {return sub(0, value);}
	public float mulX(float value) {return mul(0, value);}
	public float divX(float value) {return div(0, value);}
	
	public float addY(float value) {return add(1, value);}
	public float subY(float value) {return sub(1, value);}
	public float mulY(float value) {return mul(1, value);}
	public float divY(float value) {return div(1, value);}
	
	public float addZ(float value) {return add(2, value);}
	public float subZ(float value) {return sub(2, value);}
	public float mulZ(float value) {return mul(2, value);}
	public float divZ(float value) {return div(2, value);}
	
}
