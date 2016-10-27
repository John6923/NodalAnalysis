package nodal.standard;

import nodal.Matrix;
import nodal.framework.Element;

/**
 * Element that can get and set its values based on a matrix it can also set its
 * values to be invalid
 * 
 * @author John Dood
 *
 */
public interface MutableElement extends Element {

	/**
	 * Get the values for current and voltage based on the unit
	 * 
	 * @param m
	 *            Matrix to read from
	 * @param iRow
	 *            row that represents the current
	 * @param vRow
	 *            row that represents the voltage
	 */
	public void getFromMatrix(Matrix m, int iRow, int vRow);

	/**
	 * Write an equation encapsulating the element's IV relationship to the
	 * given row in the matrix
	 * 
	 * @param m
	 *            Matrix to write to
	 * @param row
	 *            The row that the equation should be written on
	 */
	public void writeIVEquationToMatrix(Matrix m, int row, int iCol, int vCol);

	/**
	 * Set the voltage and current to be an appropriate value if the circuit is
	 * not solvable
	 */
	public void invalidate();
}
