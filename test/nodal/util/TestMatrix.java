package nodal.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nodal.util.Matrix;

public class TestMatrix {
	
	@Test
	public void shouldDoSimple2x3Matrix() {
		Matrix m = new Matrix(2, 3);
		m.set(0, 0, -2);
		m.set(0, 1, 1);
		m.set(1, 0, 1);
		m.set(1, 2, 4);
		assertEquals(1, m.get(0, 1), 0.01);
		m.rref();
		assertEquals(1, m.get(0, 0), 0.01);
		assertEquals(0, m.get(1, 0), 0.01);
		assertEquals(1, m.get(1, 1), 0.01);
		assertEquals(0, m.get(0, 1), 0.01);
		assertEquals(4, m.get(0, 2), 0.01);
		assertEquals(8, m.get(1, 2), 0.01);
	}
	
	@Test
	public void shouldDo2x3MatrixWith0Pivot() {
		Matrix m = new Matrix(2, 3);
		m.set(0, 1, 1);
		m.set(0, 2, 4);
		m.set(1, 0, 1);
		m.set(1, 1, -2);
		assertEquals(1, m.get(0, 1), 0.01);
		m.rref();
		assertEquals(1, m.get(0, 0), 0.01);
		assertEquals(0, m.get(1, 0), 0.01);
		assertEquals(1, m.get(1, 1), 0.01);
		assertEquals(0, m.get(0, 1), 0.01);
		assertEquals(8, m.get(0, 2), 0.01);
		assertEquals(4, m.get(1, 2), 0.01);
	}
}
