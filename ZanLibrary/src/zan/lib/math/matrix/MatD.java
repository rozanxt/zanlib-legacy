package zan.lib.math.matrix;

public class MatD {
	
	private double[] data;
	private int rows, cols; 
	
	public MatD(int rows, int cols) {
		data = new double[rows*cols];
		this.rows = rows;
		this.cols = cols;
	}
	public MatD(int rows, int cols, double value) {
		this(rows, cols);
		setAll(value);
	}
	public MatD(MatD matrix) {
		this(matrix.rows(), matrix.cols());
		for (int i=0;i<size();i++) set(i, matrix.get(i));
	}
	
	public void set(int row, int col, double value) {data[rows()*col+row] = value;}
	public void set(int element, double value) {data[element] = value;}
	public void set(MatD matrix) {
		int rows = Math.min(rows(), matrix.rows());
		int cols = Math.min(cols(), matrix.cols());
		for (int i=0;i<rows;i++) {
			for (int j=0;j<cols;j++) {
				set(i, j, matrix.get(i, j));
			}
		}
	}
	public void setAll(double value) {for (int i=0;i<size();i++) set(i, value);}
	
	public double get(int row, int col) {return data[rows()*col+row];}
	public double get(int element) {return data[element];}
	public double[] getAll() {return data;}
	
	public void scalar(double value) {for (int i=0;i<size();i++) data[i] *= value;}
	
	public int rows() {return rows;}
	public int cols() {return cols;}
	public int size() {return data.length;}
	
	public void print() {
		for (int i=0;i<rows();i++) {
			for (int j=0;j<cols();j++) {
				System.out.print(get(i, j) + " ");
			}
			System.out.print("\n");
		}
	}
	
}
