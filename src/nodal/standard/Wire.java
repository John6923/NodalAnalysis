package nodal.standard;

import nodal.framework.Node;
import nodal.util.Matrix;

public class Wire extends AbstractElement {

	public Wire(Node neg, Node pos) {
		super(neg, pos);
	}

	@Override
	public void writeIVEquationToMatrix(Matrix m, int row, int iCol, int vCol) {
		m.set(row, vCol, 1);
	}
	
	@Override
	public String toString() {
		return "wire";
	}

}
