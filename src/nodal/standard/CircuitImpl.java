package nodal.standard;

import java.util.ArrayList;
import java.util.List;

import nodal.Matrix;
import nodal.framework.Circuit;
import nodal.framework.Element;
import nodal.framework.Node;
import nodal.framework.InvalidNodeException;

public class CircuitImpl implements Circuit {
	private List<MutableElement> elements = new ArrayList<>();
	private List<MutableNode> nodes = new ArrayList<>();
	private Node ground = null;

	@Override
	public List<Element> getElements() {
		return new ArrayList<Element>(elements);
	}

	@Override
	public int elementCount() {
		return elements.size();
	}

	@Override
	public List<Node> getNodes() {
		return new ArrayList<>(nodes);
	}

	@Override
	public int nodeCount() {
		return nodes.size();
	}

	@Override
	public Node createNode() {
		return new NodeImpl();
	}

	private void updateValues() {
		// The first elementCount() items are element currents
		// The second elementCount() items are element voltages
		// The next nodeCount() items are nodal voltages
		// The final column are for offsets
		Matrix m = new Matrix(elementCount() * 2 + nodeCount(),
				elementCount() * 2 + nodeCount() + 1);

		// Go through all the elements
		for (int i = 0; i < elementCount(); i++) {
			MutableElement element = elements.get(i);
			// Write the IV Equations (r: -IR+V=0, cs: I = ?, vs: V = ?
			element.writeIVEquationToMatrix(m, 2 * i, i, elementCount() + i);

			// Write the voltage difference equations vi - vp + vn = 0
			writeVoltageDifferenceEquationToMatrix(m, element, 2 * i + 1,
					elementCount() + i, 2 * elementCount());
		}

		// Go through all the nodes
		for (int i = 0; i < nodeCount(); i++) {
			Node n = nodes.get(i);
			int row = elementCount() * 2 + i;
			if (n.equals(ground)) { // If this is the ground node, set the
									// ground voltage to zero
				m.set(row, row, 1);
			} else { // Write the KCL equation at this node
				for (Element element : n.getOutboundElements()) {
					int c = elements.indexOf(element);
					m.set(row, c, 1);
				}
				for (Element element : n.getInboundElements()) {
					int c = elements.indexOf(element);
					m.set(row, c, -1);
				}
			}
		}
		try {
			m.rref();

			// Go through all the elements
			for (int elementIndex = 0; elementIndex < elementCount(); elementIndex++) {
				MutableElement element = elements.get(elementIndex);
				int iRow = elementIndex;
				int vRow = elementIndex + elementCount();
				element.getFromMatrix(m, iRow, vRow);
			}

			// Go through all the nodes
			for (int nodeIndex = 0; nodeIndex < nodeCount(); nodeIndex++) {
				MutableNode node = nodes.get(nodeIndex);
				int vRow = elementCount() * 2 + nodeIndex;
				node.getFromMatrix(m, vRow);

			}
		} catch (ArithmeticException e) {
			for (MutableElement element : elements) {
				element.invalidate();
			}
			for (MutableNode node : nodes) {
				node.invalidate();
			}
		}
	}

	private void writeVoltageDifferenceEquationToMatrix(Matrix m, Element e,
			int row, int voltageIndex, int nodeStart) {
		m.set(row, voltageIndex, 1);
		int posIndex = nodes.indexOf(e.getPositiveNode());
		m.set(row, nodeStart + posIndex, -1);
		int negIndex = nodes.indexOf(e.getNegitiveNode());
		m.set(row, nodeStart + negIndex, 1);

	}

	private void checkElementArguments(Node neg, Node pos) {
		if (neg == null)
			throw new InvalidNodeException("Negative node cannot be null");
		if (!(neg instanceof MutableNode))
			throw new InvalidNodeException(
					"Negative node was not created by circuit");
		if (pos == null)
			throw new InvalidNodeException("Positive node cannot be null");
		if (!(pos instanceof MutableNode))
			throw new InvalidNodeException(
					"Positive node was not created by circuit");
		if (neg.equals(pos))
			throw new InvalidNodeException(
					"Positive and negative nodes are the same");
	}

	private void addGenericElement(Node neg, Node pos, MutableElement e) {
		elements.add(e);
		MutableNode n = (MutableNode) neg;
		MutableNode p = (MutableNode) pos;
		n.addInbound(e);
		p.addOutbound(e);
		if (!nodes.contains(n))
			nodes.add(n);
		if (!nodes.contains(p))
			nodes.add(p);
		updateValues();
	}

	@Override
	public Element createResistor(Node neg, Node pos, double resistance) {
		checkElementArguments(neg, pos);
		MutableElement e = new Resistor(neg, pos, resistance);
		addGenericElement(neg, pos, e);
		return e;
	}

	@Override
	public Element createCurrentSource(Node neg, Node pos, double current) {
		checkElementArguments(neg, pos);
		MutableElement e = new CurrentSource(neg, pos, current);
		addGenericElement(neg, pos, e);
		return e;
	}

	@Override
	public Element createVoltageSource(Node neg, Node pos, double voltage) {
		checkElementArguments(neg, pos);
		MutableElement e = new VoltageSource(neg, pos, voltage);
		addGenericElement(neg, pos, e);
		return e;
	}

	@Override
	public void setGround(Node gnd) {
		if (gnd == null)
			throw new InvalidNodeException("Ground cannot be null");
		this.ground = gnd;
	}

	@Override
	public Node getGround() {
		return ground;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < elementCount(); i++) {
			sb.append("Element ");
			sb.append(i);
			sb.append(":\n ");
			sb.append(elements.get(i).toString());
			sb.append("\n  V = ");
			sb.append(String.format("%sV",
					prefixNumber(elements.get(i).getVoltage())));
			sb.append("\n  I = ");
			sb.append(String.format("%sA",
					prefixNumber(elements.get(i).getCurrent())));
			sb.append('\n');

		}
		for (int i = 0; i < nodeCount(); i++) {
			sb.append("Node ");
			sb.append(i);
			sb.append(":\n V = ");
			sb.append(String.format("%sV", prefixNumber(nodes.get(i).getVoltage())));
			sb.append('\n');
		}
		return sb.toString();
	}

	private static String format(String prefix, double scalar, double number) {
		return String.format("%.3f %s", scalar * number, prefix);
	}

	public static String prefixNumber(double number) {
		if (number >= 1000000) {
			return format("M", 0.000001, number);
		} else if (number >= 1000) {
			return format("k", 0.001, number);
		} else if (number >= 1) {
			return format("", 1, number);
		} else if (number >= 0.001) {
			return format("m", 1000, number);
		} else if(number >= 0.000001){
			return format("u", 1000000, number);
		} else {
			return "0 ";
		}
	}
}
