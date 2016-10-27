package nodal.standard;

import nodal.framework.Node;
import nodal.util.Matrix;

public class VoltageSource extends AbstractElement {
	private double voltage;

	public VoltageSource(Node neg, Node pos, double voltage) {
		super(neg, pos);
		this.voltage = voltage;
	}

	@Override
	public void writeIVEquationToMatrix(Matrix m, int row, int iCol, int vCol) {
		m.set(row, vCol, 1);
		m.set(row, m.lastCol(), voltage);

	}
	
	@Override
	public double getVoltage() {
		return voltage;
	}
	
	@Override
	public String toString() {
		return String.format("%sV voltage source", CircuitImpl.prefixNumber(voltage));
	}

}
