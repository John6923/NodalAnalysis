package nodal.standard;

import nodal.framework.Circuit;
import nodal.framework.Node;
import nodal.standard.CircuitImpl;

public class CircuitDemo {

	public static void main(String[] args) {
		Circuit circuit = new CircuitImpl();
		Node n0 = circuit.createNode();
		Node n1 = circuit.createNode();
		circuit.setGround(n0);
		circuit.createResistor(n0, n1, 2);
		circuit.createCurrentSource(n0, n1, 4);
		System.out.println(circuit);
		circuit = new CircuitImpl();
		n0 = circuit.createNode();
		n1 = circuit.createNode();
		Node n2 = circuit.createNode();
		Node n3 = circuit.createNode();
		circuit.setGround(n0);
		circuit.createCurrentSource(n0, n1, 2);
		circuit.createResistor(n2, n1, 3);
		circuit.createResistor(n0, n2, 2);
		circuit.createResistor(n3, n2, 1);
		circuit.createResistor(n0, n3, 1);
		System.out.println(circuit);
		circuit = new CircuitImpl();
		n0 = circuit.createNode();
		n1 = circuit.createNode();
		n2 = circuit.createNode();
		n3 = circuit.createNode();
		circuit.setGround(n0);
		circuit.createVoltageSource(n0, n1, 8);
		circuit.createResistor(n2, n1, 3000);
		circuit.createResistor(n0, n2, 2000);
		circuit.createResistor(n3, n2, 1000);
		circuit.createResistor(n0, n3, 1000);
		System.out.println(circuit);
	}

}
