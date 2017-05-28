package zan.lib.droid.util.math;

public class MatF {
	
	private float[] data;
	private final int rows, cols;
	
	public MatF() {this(1);}
	public MatF(int dim) {this(dim, dim);}
	public MatF(int rows, int cols) {
		data = new float[rows*cols];
		this.rows = rows;
		this.cols = cols;
	}
	public MatF(int dim, boolean identity) {
		this(dim);
		if (identity) loadIdentity();
	}
	public MatF(int rows, int cols, boolean identity) {
		this(rows, cols);
		if (identity) loadIdentity();
	}
	public MatF(int dim, float value) {
		this(dim);
		setAll(value);
	}
	public MatF(int rows, int cols, float value) {
		this(rows, cols);
		setAll(value);
	}
	public MatF(int dim, float... entry) {
		this(dim, 0f);
		put(entry);
	}
	public MatF(int rows, int cols, float... entry) {
		this(rows, cols, 0f);
		put(entry);
	}
	public MatF(MatF matrix) {
		this(matrix.rows, matrix.cols);
		set(matrix);
	}
	public MatF(int dim, MatF matrix) {
		this(dim);
		set(matrix);
	}
	public MatF(int rows, int cols, MatF matrix) {
		this(rows, cols);
		set(matrix);
	}
	public MatF(VecF... vectors) {
		this(vectors[0].size(), vectors.length);
		for (int i=0;i<cols;i++) setCol(i, vectors[i]);
	}
	
	public void set(int element, float value) {data[element] = value;}
	public void set(int row, int col, float value) {set(rows*col+row, value);}
	public void setAll(float value) {for (int i=0;i<size();i++) set(i, value);}
	public void put(float... entry) {
		int size = Math.min(size(), entry.length);
		for (int i=0;i<size;i++) set(i, entry[i]);
	}
	public void set(MatF matrix) {
		int rows = Math.min(this.rows, matrix.rows);
		int cols = Math.min(this.cols, matrix.cols);
		for (int i=0;i<rows;i++) {
			for (int j=0;j<cols;j++) {
				set(i, j, matrix.get(i, j));
			}
		}
	}
	public void setRow(int row, VecF vector) {
		int cols = Math.min(this.cols, vector.size());
		for (int i=0;i<cols;i++) set(row, i, vector.get(i));
	}
	public void setCol(int col, VecF vector) {
		int rows = Math.min(this.rows, vector.size());
		for (int i=0;i<rows;i++) set(i, col, vector.get(i));
	}
	public void setDiag(int diag, VecF vector) {
		int diagX = Math.max(0, diag);
		int diagY = Math.max(0, -diag);
		int size = Math.min(Math.min(rows-diagY, cols-diagX), vector.size());
		for (int i=0;i<size;i++) set(diagY+i, diagX+i, vector.get(i));
	}
	
	public MatF loadIdentity() {
		for (int i=0;i<rows;i++) {
			for (int j=0;j<cols;j++) {
				if (i == j) set(i, j, 1f);
				else set(i, j, 0f);
			}
		}
		return this;
	}
	
	public float get(int element) {return data[element];}
	public float get(int row, int col) {return get(rows*col+row);}
	public VecF getRow(int row) {
		VecF result = new VecF(cols);
		for (int i=0;i<cols;i++) result.set(i, get(row, i));
		return result;
	}
	public VecF getCol(int col) {
		VecF result = new VecF(rows);
		for (int i=0;i<rows;i++) result.set(i, get(i, col));
		return result;
	}
	public VecF getDiag(int diag) {
		int diagX = Math.max(0, diag);
		int diagY = Math.max(0, -diag);
		int size = Math.min(rows-diagY, cols-diagX);
		VecF result = new VecF(size);
		for (int i=0;i<size;i++) result.set(i, get(diagY+i, diagX+i));
		return result;
	}
	public MatF getSubMatrix(int row0, int col0, int row1, int col1) {
		int rows = row1-row0+1;
		int cols = col1-col0+1;
		MatF result = new MatF(rows, cols);
		for (int i=0;i<rows;i++) {
			for (int j=0;j<cols;j++) {
				result.set(i, j, get(row0+i, col0+j));
			}
		}
		return result;
	}
	public MatF getRowCutMatrix(int row) {
		if (rows > 1) {
			MatF result = new MatF(rows-1, cols);
			int pos = 0;
			for (int j=0;j<cols;j++) {
				for (int i=0;i<rows;i++) {
					if (i != row) {
						result.set(pos, get(i, j));
						pos++;
					}
				}
			}
			return result;
		}
		return new MatF(this);
	}
	public MatF getColCutMatrix(int col) {
		if (cols > 1) {
			MatF result = new MatF(rows, cols-1);
			int pos = 0;
			for (int j=0;j<cols;j++) {
				for (int i=0;i<rows;i++) {
					if (j != col) {
						result.set(pos, get(i, j));
						pos++;
					}
				}
			}
			return result;
		}
		return new MatF(this);
	}
	public MatF getSubCutMatrix(int row, int col) {
		if (rows > 1 && cols > 1) {
			MatF result = new MatF(rows-1, cols-1);
			int pos = 0;
			for (int j=0;j<cols;j++) {
				for (int i=0;i<rows;i++) {
					if (j != col && i != row) {
						result.set(pos, get(i, j));
						pos++;
					}
				}
			}
			return result;
		}
		return new MatF(this);
	}
	
	public MatF transpose() {
		MatF result = new MatF(cols, rows);
		for (int i=0;i<rows;i++) {
			for (int j=0;j<cols;j++) {
				result.set(j, i, get(i, j));
			}
		}
		if (isSquare()) {
			set(result);
			return this;
		}
		return result;
	}
	
	public MatF scalar(float factor) {
		for (int i=0;i<size();i++) data[i] *= factor;
		return this;
	}
	
	public MatF addRow(int row, VecF vector) {
		int cols = Math.min(this.cols, vector.size());
		for (int i=0;i<cols;i++) set(row, i, get(row, i)+vector.get(i));
		return this;
	}
	public MatF multRow(int row, float factor) {
		for (int i=0;i<cols;i++) set(row, i, get(row, i)*factor);
		return this;
	}
	public MatF swapRow(int i, int j) {
		VecF temp = getRow(i);
		setRow(i, getRow(j));
		setRow(j, temp);
		return this;
	}
	
	public MatF addCol(int col, VecF vector) {
		int rows = Math.min(this.rows, vector.size());
		for (int i=0;i<rows;i++) set(i, col, get(i, col)+vector.get(i));
		return this;
	}
	public MatF multCol(int col, float factor) {
		for (int i=0;i<rows;i++) set(i, col, get(i, col)*factor);
		return this;
	}
	public MatF swapCol(int i, int j) {
		VecF temp = getCol(i);
		setCol(i, getCol(j));
		setCol(j, temp);
		return this;
	}
	
	public MatF round(int dp) {
		for (int i=0;i<size();i++) set(i, (float)(Math.round(get(i)*Math.pow(10, dp))*Math.pow(10, -dp)));
		return this;
	}
	public MatF round() {return round(0);}
	
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
