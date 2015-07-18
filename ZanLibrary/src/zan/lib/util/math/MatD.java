package zan.lib.util.math;

public class MatD {
	
	private double[] data;
	private final int rows, cols;
	
	public MatD() {this(1);}
	public MatD(int dim) {this(dim, dim);}
	public MatD(int rows, int cols) {
		data = new double[rows*cols];
		this.rows = rows;
		this.cols = cols;
	}
	public MatD(int dim, boolean identity) {
		this(dim);
		if (identity) loadIdentity();
	}
	public MatD(int rows, int cols, boolean identity) {
		this(rows, cols);
		if (identity) loadIdentity();
	}
	public MatD(int dim, double value) {
		this(dim);
		setAll(value);
	}
	public MatD(int rows, int cols, double value) {
		this(rows, cols);
		setAll(value);
	}
	public MatD(int dim, double... entry) {
		this(dim, 0.0);
		put(entry);
	}
	public MatD(int rows, int cols, double... entry) {
		this(rows, cols, 0.0);
		put(entry);
	}
	public MatD(MatD matrix) {
		this(matrix.rows, matrix.cols);
		set(matrix);
	}
	public MatD(int dim, MatD matrix) {
		this(dim);
		set(matrix);
	}
	public MatD(int rows, int cols, MatD matrix) {
		this(rows, cols);
		set(matrix);
	}
	public MatD(VecD... vectors) {
		this(vectors[0].size(), vectors.length);
		for (int i=0;i<cols;i++) setCol(i, vectors[i]);
	}
	
	public void set(int row, int col, double value) {data[rows*col+row] = value;}
	public void set(int element, double value) {data[element] = value;}
	public void setAll(double value) {for (int i=0;i<size();i++) set(i, value);}
	public void put(double... entry) {
		int size = Math.min(size(), entry.length);
		for (int i=0;i<size;i++) set(i, entry[i]);
	}
	public void set(MatD matrix) {
		int rows = Math.min(this.rows, matrix.rows);
		int cols = Math.min(this.cols, matrix.cols);
		for (int i=0;i<rows;i++) {
			for (int j=0;j<cols;j++) {
				set(i, j, matrix.get(i, j));
			}
		}
	}
	public void setDiag(int diag, VecD vector) {
		int diagX = Math.max(0, diag);
		int diagY = Math.max(0, -diag);
		int size = Math.min(Math.min(rows-diagY, cols-diagX), vector.size());
		for (int i=0;i<size;i++) set(diagY+i, diagX+i, vector.get(i));
	}
	public void setRow(int row, VecD vector) {
		int cols = Math.min(this.cols, vector.size());
		for (int i=0;i<cols;i++) set(row, i, vector.get(i));
	}
	public void setCol(int col, VecD vector) {
		int rows = Math.min(this.rows, vector.size());
		for (int i=0;i<rows;i++) set(i, col, vector.get(i));
	}
	
	public MatD loadIdentity() {
		for (int i=0;i<rows;i++) {
			for (int j=0;j<cols;j++) {
				if (i == j) set(i, j, 1.0);
				else set(i, j, 0.0);
			}
		}
		return this;
	}
	
	public double get(int row, int col) {return data[rows*col+row];}
	public double get(int element) {return data[element];}
	public VecD getDiag(int diag) {
		int diagX = Math.max(0, diag);
		int diagY = Math.max(0, -diag);
		int size = Math.min(rows-diagY, cols-diagX);
		VecD result = new VecD(size);
		for (int i=0;i<size;i++) result.set(i, get(diagY+i, diagX+i));
		return result;
	}
	public VecD getRow(int row) {
		VecD result = new VecD(cols);
		for (int i=0;i<cols;i++) result.set(i, get(row, i));
		return result;
	}
	public VecD getCol(int col) {
		VecD result = new VecD(rows);
		for (int i=0;i<rows;i++) result.set(i, get(i, col));
		return result;
	}
	public MatD getSubMatrix(int row0, int col0, int row1, int col1) {
		int rows = row1-row0+1;
		int cols = col1-col0+1;
		MatD result = new MatD(rows, cols);
		for (int i=0;i<rows;i++) {
			for (int j=0;j<cols;j++) {
				result.set(i, j, get(row0+i, col0+j));
			}
		}
		return result;
	}
	public MatD getSubCutMatrix(int i, int j) {
		if (rows > 1 && cols > 1) {
			MatD result = new MatD(rows-1, cols-1);
			int pos = 0;
			for (int k=0;k<cols;k++) {
				for (int l=0;l<rows;l++) {
					if (k != j && l != i) {
						result.set(pos, get(l, k));
						pos++;
					}
				}
			}
			return result;
		}
		return new MatD(this);
	}
	
	public MatD transpose() {
		if (isSquare()) {
			MatD temp = new MatD(cols, rows);
			for (int i=0;i<rows;i++) {
				for (int j=0;j<cols;j++) {
					temp.set(j, i, get(i, j));
				}
			}
			set(temp);
		} else {
			return getTranspose();
		}
		return this;
	}
	public MatD getTranspose() {
		MatD result = new MatD(cols, rows);
		for (int i=0;i<rows;i++) {
			for (int j=0;j<cols;j++) {
				result.set(j, i, get(i, j));
			}
		}
		return result;
	}
	
	public MatD scalar(double factor) {
		for (int i=0;i<size();i++) data[i] *= factor;
		return this;
	}
	public MatD getScalar(double factor) {
		MatD result = new MatD(this);
		return result.scalar(factor);
	}
	
	public int rows() {return rows;}
	public int cols() {return cols;}
	public int dim() {return Math.min(rows, cols);}
	public int size() {return data.length;}
	
	public boolean isSquare() {return (rows == cols);}
	
	@Override
	public String toString() {
		String str = "";
		for (int i=0;i<rows;i++) {
			for (int j=0;j<cols;j++) {
				str += get(i, j);
				if (j < cols-1) str += " ";
			}
			if (i < rows-1) str += "\n";
		}
		return str;
	}
	
}
