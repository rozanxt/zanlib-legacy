package zan.lib.tool;

public class Rect {
	
	public double x0, y0, x1, y1;
	
	public Rect() {set(0.0, 0.0, 0.0, 0.0);}
	public Rect(double x0, double y0, double x1, double y1) {set(x0, y0, x1, y1);}
	
	public void set(double x0, double y0, double x1, double y1) {this.x0 = x0; this.y0 = y0; this.x1 = x1; this.y1 = y1;}
	public void setX0(double x0) {this.x0 = x0;}
	public void setY0(double y0) {this.y0 = y0;}
	public void setX1(double x1) {this.x1 = x1;}
	public void setY1(double y1) {this.y1 = y1;}
	
	public void translate(double sx, double sy) {x0 += sx; y0 += sy; x1 += sx; y1 += sy;}
	
	public double getX0() {return x0;}
	public double getY0() {return y0;}
	public double getX1() {return x1;}
	public double getY1() {return y1;}
	
	public double getMidX() {return (x0+x1)/2.0;}
	public double getMidY() {return (y0+y1)/2.0;}
	
	public double getWidth() {return Math.abs(x1-x0);}
	public double getHeight() {return Math.abs(y1-y0);}
	
}
