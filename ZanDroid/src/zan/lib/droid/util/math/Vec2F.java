package zan.lib.droid.util.math;

public class Vec2F extends VecF {
	
	public Vec2F() {super(2);}
	public Vec2F(float value) {super(2, value);}
	public Vec2F(float x, float y) {super(x, y);}
	public Vec2F(VecF vector) {super(2, vector);}
	
	public void setComponents(float x, float y) {setX(x); setY(y);}
	public void setX(float x) {set(0, x);}
	public void setY(float y) {set(1, y);}
	
	public float getX() {return get(0);}
	public float getY() {return get(1);}
	
	public float addX(float value) {return add(0, value);}
	public float subX(float value) {return sub(0, value);}
	public float mulX(float value) {return mul(0, value);}
	public float divX(float value) {return div(0, value);}
	
	public float addY(float value) {return add(1, value);}
	public float subY(float value) {return sub(1, value);}
	public float mulY(float value) {return mul(1, value);}
	public float divY(float value) {return div(1, value);}
	
}
