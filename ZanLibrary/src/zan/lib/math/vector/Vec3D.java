package zan.lib.math.vector;

public class Vec3D extends VecD {
	
	public Vec3D() {super(3);}
	public Vec3D(double value) {super(3, value);}
	public Vec3D(Vec3D vector) {super(vector);}
	public Vec3D(double x, double y, double z) {
		this();
		setComponents(x, y, z);
	}
	
	public void setComponents(double x, double y, double z) {setX(x); setY(y); setZ(z);}
	public void setX(double x) {set(0, x);}
	public void setY(double y) {set(1, y);}
	public void setZ(double z) {set(2, z);}
	
	public double getX() {return get(0);}
	public double getY() {return get(1);}
	public double getZ() {return get(2);}
	
	public double addX(double value) {return add(0, value);}
	public double subX(double value) {return sub(0, value);}
	public double multX(double value) {return mult(0, value);}
	public double divX(double value) {return div(0, value);}
	
	public double addY(double value) {return add(1, value);}
	public double subY(double value) {return sub(1, value);}
	public double multY(double value) {return mult(1, value);}
	public double divY(double value) {return div(1, value);}
	
	public double addZ(double value) {return add(2, value);}
	public double subZ(double value) {return sub(2, value);}
	public double multZ(double value) {return mult(2, value);}
	public double divZ(double value) {return div(2, value);}
	
}
