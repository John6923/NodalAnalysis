package nodal.standard;

import nodal.framework.Node;
import nodal.util.Matrix;

public class CurrentSource extends AbstractElement {
	
	private double current;
	
	public CurrentSource(Node neg, Node pos, double current){
		super(neg,pos);
		this.current = current;
	}
	
	@Override
	public void writeIVEquationToMatrix(Matrix m, int row, int iCol,
			int vCol) {
		m.set(row, iCol, -1);
		m.set(row, m.lastCol(), current);
	}
	
	@Override
	public double getCurrent() {
		return -1*current;
	}
	
	@Override
	public String toString() {
		return String.format("%sA current source", CircuitImpl.prefixNumber(current));
	}

}
