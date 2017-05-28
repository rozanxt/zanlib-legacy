package zan.lib.droid.util.math;

public class Vec4F extends VecF {
	
	public Vec4F() {super(4);}
	public Vec4F(float value) {super(4, value);}
	public Vec4F(float x, float y, float z, float w) {super(x, y, z, w);}
	public Vec4F(VecF vector) {super(4, vector);}
	
	public void setComponents(float x, float y, float z, float w) {setX(x); setY(y); setZ(z); setW(w);}
	public void setX(float x) {set(0, x);}
	public void setY(float y) {set(1, y);}
	public void setZ(float z) {set(2, z);}
	public void setW(float w) {set(3, w);}
	
	public float getX() {return get(0);}
	public float getY() {return get(1);}
	public float getZ() {return get(2);}
	public float getW() {return get(3);}
	
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
	
	public float addW(float value) {return add(3, value);}
	public float subW(float value) {return sub(3, value);}
	public float mulW(float value) {return mul(3, value);}
	public float divW(float value) {return div(3, value);}
	
}
