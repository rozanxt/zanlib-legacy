package zan.lib.math.vector;

public class Vec2D extends VecD {
	
	public Vec2D() {super(2);}
	public Vec2D(double value) {super(2, value);}
	public Vec2D(Vec2D vector) {super(vector);}
	public Vec2D(double x, double y) {
		this();
		setComponents(x, y);
	}
	
	public void setComponents(double x, double y) {setX(x);	setY(y);}
	public void setX(double x) {set(0, x);}
	public void setY(double y) {set(1, y);}
	
	public double getX() {return get(0);}
	public double getY() {return get(1);}
	
	public double addX(double value) {return add(0, value);}
	public double subX(double value) {return sub(0, value);}
	public double multX(double value) {return mult(0, value);}
	public double divX(double value) {return div(0, value);}
	
	public double addY(double value) {return add(1, value);}
	public double subY(double value) {return sub(1, value);}
	public double multY(double value) {return mult(1, value);}
	public double divY(double value) {return div(1, value);}
	
}
