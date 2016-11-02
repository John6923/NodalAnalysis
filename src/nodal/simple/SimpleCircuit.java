package nodal.simple;

import java.util.ArrayList;
import java.util.List;

import nodal.framework.Circuit;
import nodal.framework.Element;
import nodal.framework.InvalidNodeException;
import nodal.framework.Node;
import nodal.standard.CircuitImpl;

public class SimpleCircuit implements SimpleCircuitFacade {

	private List<Element> elements;
	private List<Node> nodes;
	private Circuit circuit;

	public SimpleCircuit() {
		elements = new ArrayList<>();
		nodes = new ArrayList<>();
		circuit = new CircuitImpl();
	}

	private Node getNode(int node) {
		if (node < 0) {
			throw new InvalidNodeException("Node number cannot be negative");
		}
		for (int i = nodes.size(); i <= node; i++) {
			nodes.add(circuit.createNode());
		}
		return nodes.get(node);

	}
	
	@Override
	public int getNodeCount() {
		return nodes.size();
	}

	@Override
	public void createResistor(int nodeNeg, int nodePos, double resistance) {
		elements.add(circuit.createResistor(getNode(nodeNeg), getNode(nodePos),
				resistance));
	}

	@Override
	public void createCurrentSource(int nodeNeg, int nodePos, double current) {
		elements.add(circuit.createCurrentSource(getNode(nodeNeg),
				getNode(nodePos), current));
	}

	@Override
	public void createVoltageSource(int nodeNeg, int nodePos, double voltage) {
		elements.add(circuit.createVoltageSource(getNode(nodeNeg),
				getNode(nodePos), voltage));
	}

	@Override
	public void setGround(int node) {
		circuit.setGround(getNode(node));

	}

	@Override
	public double getNodalVoltage(int node) {
		if(node >= nodes.size()) {
			throw new InvalidNodeException("Node has not been defined yet");
		}
		return getNode(node).getVoltage();
	}

	private Element getElement(int element) {
		if (element < 0) {
			throw new NullPointerException("Element index should not be negative");
		}
		if(element >= elements.size()) {
			throw new NullPointerException("Element has not been defined yet");
		}
		return elements.get(element);
	}
	
	@Override
	public int getElementCount() {
		return elements.size();
	}

	@Override
	public double getCurrent(int element) {
		return getElement(element).getCurrent();
	}

	@Override
	public double getVoltage(int element) {
		return getElement(element).getVoltage();
	}
	
	@Override
	public String toString() {
		return circuit.toString();
	}

}
