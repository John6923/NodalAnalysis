package nodal.standard;

import java.util.ArrayList;
import java.util.List;

import nodal.Matrix;
import nodal.framework.Element;

public class NodeImpl implements MutableNode {
	
	private List<Element> inbound = new ArrayList<>();
	private List<Element> outbound = new ArrayList<>();
	private double voltage;
	
	@Override
	public double getVoltage() {
		return voltage;
	}

	@Override
	public List<Element> getInboundElements() {
		return new ArrayList<>(inbound);
	}

	@Override
	public List<Element> getOutboundElements() {
		return new ArrayList<>(outbound);
	}

	@Override
	public int elementCount() {
		return inbound.size()+outbound.size();
	}

	@Override
	public void getFromMatrix(Matrix m, int vRow) {
		voltage = m.get(vRow, m.lastCol());
	}

	@Override
	public void addInbound(Element e) {
		inbound.add(e);
	}

	@Override
	public void addOutbound(Element e) {
		outbound.add(e);
	}

	@Override
	public void invalidate() {
		this.voltage = Double.NaN;
	}

}
