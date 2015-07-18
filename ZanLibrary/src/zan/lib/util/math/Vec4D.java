package zan.lib.util.math;

public class Vec4D extends VecD {
	
	public Vec4D() {super(4);}
	public Vec4D(double value) {super(4, value);}
	public Vec4D(double x, double y, double z, double w) {super(x, y, z, w);}
	public Vec4D(VecD vector) {super(4, vector);}
	
	public void setComponents(double x, double y, double z, double w) {setX(x); setY(y); setZ(z); setW(w);}
	public void setX(double x) {set(0, x);}
	public void setY(double y) {set(1, y);}
	public void setZ(double z) {set(2, z);}
	public void setW(double w) {set(3, w);}
	
	public double getX() {return get(0);}
	public double getY() {return get(1);}
	public double getZ() {return get(2);}
	public double getW() {return get(3);}
	
	public double addX(double value) {return add(0, value);}
	public double subX(double value) {return sub(0, value);}
	public double mulX(double value) {return mul(0, value);}
	public double divX(double value) {return div(0, value);}
	
	public double addY(double value) {return add(1, value);}
	public double subY(double value) {return sub(1, value);}
	public double mulY(double value) {return mul(1, value);}
	public double divY(double value) {return div(1, value);}
	
	public double addZ(double value) {return add(2, value);}
	public double subZ(double value) {return sub(2, value);}
	public double mulZ(double value) {return mul(2, value);}
	public double divZ(double value) {return div(2, value);}
	
	public double addW(double value) {return add(3, value);}
	public double subW(double value) {return sub(3, value);}
	public double mulW(double value) {return mul(3, value);}
	public double divW(double value) {return div(3, value);}
	
}
