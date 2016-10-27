package nodal.util;

import java.util.ArrayList;
import java.util.List;

public class Matrix {
	private List<Row> rows;
	private int lastCol;

	public Matrix(int height, int width) {
		this.lastCol = width-1;
		rows = new ArrayList<>(height);
		for (int i = 0; i < height; i++) {
			rows.add(new Row(width));
		}
	}
	
	public int lastCol() {
		return lastCol;
	}

	public double get(int row, int col) {
		return rows.get(row).get(col);
	}

	public void set(int row, int col, double value) {
		rows.get(row).set(col, value);
	}

	public void rref() {
		for (int pivot = 0; pivot < rows.size(); pivot++) {
			int swapcount = 0;
			while (Math.abs(get(pivot, pivot)) < 0.0001
					&& (swapcount < (rows.size() - 1 - pivot))) {
				shiftRows(pivot);
				swapcount++;
			}
			if (Math.abs(get(pivot, pivot)) < 0.0001) {
				throw new ArithmeticException("Matrix is not invertable");
			}
			rows.get(pivot).divideBy(get(pivot, pivot));
			for (int i = pivot + 1; i < rows.size(); i++) {
				Row toElim = rows.get(i);
				toElim.addScalarMultiple(toElim.get(pivot) * -1,
						rows.get(pivot));
			}
		}
		for (int pivot = rows.size() - 1; pivot >= 0; pivot--) {
			for (int i = pivot - 1; i >= 0; i--) {
				Row toElim = rows.get(i);
				toElim.addScalarMultiple(toElim.get(pivot) * -1,
						rows.get(pivot));
			}
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Row row : rows) {
			sb.append(row.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
	
	private void shiftRows(int row) {
		for(int i = row; i < rows.size()-1; i++) {
			swapRows(i, i+1);
		}
	}

	private void swapRows(int row1, int row2) {
		Row tmp = rows.get(row1);
		rows.set(row1, rows.get(row2));
		rows.set(row2, tmp);
	}
	
	private static class Row {
		private List<Double> values;

		public Row(int width) {
			values = new ArrayList<>(width);
			for (int i = 0; i < width; i++) {
				values.add(0.0);
			}
		}

		public double get(int col) {
			return values.get(col);
		}

		public void set(int col, double value) {
			values.set(col, value);
		}

		public void divideBy(double divisor) {
			for (int i = 0; i < values.size(); i++) {
				set(i, get(i) / divisor);
			}
		}

		public void addScalarMultiple(double scalar, Row other) {
			for (int i = 0; i < values.size(); i++) {
				set(i, get(i) + scalar * other.get(i));
			}
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for(Double value : values) {
				sb.append(String.format("%6g ", value.doubleValue()));
			}
			return sb.toString();
		}
	}

}
