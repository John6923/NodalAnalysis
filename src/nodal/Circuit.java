package nodal;

import java.util.ArrayList;
import java.util.List;

public class Circuit {

	private List<Component> components;
	private List<List<Integer>> leavingNode;
	private List<List<Integer>> arrivingNode;

	private int groundNode = 0;

	public Circuit() {
		components = new ArrayList<>();
		leavingNode = new ArrayList<>();
		arrivingNode = new ArrayList<>();

	}

	public void setGround(int node) {
		groundNode = node;
	}

	public void addResistor(int node1, int node2, double resistance) {
		addComponent(new Resistor(resistance, node2, node1), node1, node2);
	}

	public void addCurrentSource(int node1, int node2, double current) {
		addComponent(new CurrentSource(current, node2, node1), node1, node2);
	}

	public void addVoltageSource(int node1, int node2, double voltage) {
		addComponent(new VoltageSource(voltage, node2, node1), node1, node2);
	}

	private void addComponent(Component c, int n1, int n2) {
		for (int i = nodeCount(); i <= n1 || i <= n2; i++) {
			arrivingNode.add(new ArrayList<>());
			leavingNode.add(new ArrayList<>());
		}

		leavingNode.get(n1).add(components.size());
		arrivingNode.get(n2).add(components.size());
		components.add(c);
	}

	public int componentCount() {
		return components.size();
	}

	public int nodeCount() {
		return leavingNode.size();
	}

	private Matrix calculate() {
		Matrix m = new Matrix(componentCount() * 2 + nodeCount(),
				componentCount() * 2 + nodeCount() + 1);
		for (int i = 0; i < componentCount(); i++) {
			Component component = components.get(i);
			component.writeComponentEquations(m, i);
		}
		for (int i = 0; i < nodeCount(); i++) {
			int row = componentCount() * 2 + i;
			if (i == groundNode) {
				m.set(row, row, 1);
			} else {
				for (Integer component : leavingNode.get(i)) {
					int c = component;
					m.set(row, c, 1);
				}
				for (Integer component : arrivingNode.get(i)) {
					int c = component;
					m.set(row, c, -1);
				}
			}
		}
		m.rref();
		return m;
	}

	@Override
	public String toString() {
		Matrix m = calculate();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < componentCount(); i++) {
			sb.append("Component ");
			sb.append(i);
			sb.append(":\n ");
			sb.append(components.get(i).toString());
			sb.append("\n  V= ");
			sb.append(String.format("%f", m.get(componentCount() + i,
					componentCount() * 2 + nodeCount())));
			sb.append(" I= ");
			sb.append(String.format("%f",
					m.get(i, componentCount() * 2 + nodeCount())));
			sb.append('\n');

		}
		for (int i = 0; i < nodeCount(); i++) {
			sb.append("Node ");
			sb.append(i);
			sb.append(":\n V= ");
			sb.append(String.format("%f", m.get(2 * componentCount() + i,
					componentCount() * 2 + nodeCount())));
			sb.append('\n');
		}
		return sb.toString();
	}

	private interface Component {
		void writeComponentEquations(Matrix matrix, int componentNumber);
	}

	private class Resistor implements Component {
		double value;
		int nodepos, nodeneg;

		public Resistor(double value, int nodepos, int nodeneg) {
			this.value = value;
			this.nodeneg = nodeneg;
			this.nodepos = nodepos;
		}

		@Override
		public void writeComponentEquations(Matrix matrix, int cn) {
			int cc = componentCount();
			matrix.set(2 * cn, cn, -1 * value);
			matrix.set(2 * cn, cc + cn, 1);
			matrix.set(2 * cn + 1, cc + cn, 1);
			matrix.set(2 * cn + 1, 2 * cc + nodepos, -1);
			matrix.set(2 * cn + 1, 2 * cc + nodeneg, 1);
		}

		@Override
		public String toString() {
			return String.format("%g Ohm resistor", value);
		}
	}

	private class CurrentSource implements Component {
		double value;
		int nodepos, nodeneg;

		public CurrentSource(double value, int nodepos, int nodeneg) {
			this.value = value;
			this.nodeneg = nodeneg;
			this.nodepos = nodepos;
		}

		@Override
		public void writeComponentEquations(Matrix matrix, int cn) {
			int cc = componentCount();
			int nc = nodeCount();
			matrix.set(2 * cn, cn, -1);
			matrix.set(2 * cn, 2 * cc + nc, value);
			matrix.set(2 * cn + 1, cc + cn, 1);
			matrix.set(2 * cn + 1, 2 * cc + nodepos, -1);
			matrix.set(2 * cn + 1, 2 * cc + nodeneg, 1);
		}

		@Override
		public String toString() {
			return String.format("%f Amp current source", value);
		}
	}

	private class VoltageSource implements Component {
		double value;
		int nodepos, nodeneg;

		public VoltageSource(double value, int nodepos, int nodeneg) {
			this.value = value;
			this.nodeneg = nodeneg;
			this.nodepos = nodepos;
		}

		@Override
		public void writeComponentEquations(Matrix matrix, int cn) {
			int cc = componentCount();
			int nc = nodeCount();
			matrix.set(2 * cn, cc + cn, 1);
			matrix.set(2 * cn, 2 * cc + nc, value);
			matrix.set(2 * cn + 1, cc + cn, 1);
			matrix.set(2 * cn + 1, 2 * cc + nodepos, -1);
			matrix.set(2 * cn + 1, 2 * cc + nodeneg, 1);
		}

		@Override
		public String toString() {
			return String.format("%f V voltage source", value);
		}
	}
}
