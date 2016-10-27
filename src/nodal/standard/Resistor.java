package nodal.standard;

import static nodal.util.UnitFormater.prefixNumber;

import nodal.framework.Node;
import nodal.util.Matrix;

public class Resistor extends AbstractElement {
	
	private double resistance;
	
	public Resistor(Node neg, Node pos, double resistance) {
		super(neg,pos);
		this.resistance = resistance;
	}
	
	@Override
	public void writeIVEquationToMatrix(Matrix m, int row, int iCol,
			int vCol) {
		m.set(row, iCol, -1*resistance);
		m.set(row, vCol, 1);
	}
	
	@Override
	public String toString() {
		return String.format("%sOhm resistor", prefixNumber(resistance));
	}

}
