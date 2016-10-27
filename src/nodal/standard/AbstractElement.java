package nodal.standard;

import nodal.framework.Node;
import nodal.util.Matrix;

public abstract class AbstractElement implements MutableElement {
	
	private Node pos, neg; 
	private double current, voltage;
	
	public AbstractElement(Node neg, Node pos) {
		this.pos = pos;
		this.neg = neg;
	}
	
	@Override
	public Node getPositiveNode() {
		return pos;
	}

	@Override
	public Node getNegitiveNode() {
		return neg;
	}

	@Override
	public double getVoltage() {
		return voltage;
	}

	@Override
	public double getCurrent() {
		return current;
	}

	@Override
	public void getFromMatrix(Matrix m, int iRow, int vRow) {
		this.current = m.get(iRow, m.lastCol());
		this.voltage = m.get(vRow, m.lastCol());
	}
	
	@Override
	public void invalidate() {
		this.current = Double.NaN;
		this.voltage = Double.NaN;
	}
}
