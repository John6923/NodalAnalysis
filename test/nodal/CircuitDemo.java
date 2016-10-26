package nodal;

public class CircuitDemo {

	public static void main(String[] args) {
		Circuit circuit = new Circuit();
		circuit.setGround(0);
		circuit.addResistor(0, 1, 2);
		circuit.addCurrentSource(0, 1, 4);
		System.out.println(circuit);
		circuit = new Circuit();
		circuit.setGround(0);
		circuit.addCurrentSource(0, 1, 2);
		circuit.addResistor(2, 1, 3);
		circuit.addResistor(0, 2, 2);
		circuit.addResistor(3, 2, 1);
		circuit.addResistor(0, 3, 1);
		System.out.println(circuit);
		circuit = new Circuit();
		circuit.setGround(0);
		circuit.addVoltageSource(0, 1, 8);
		circuit.addResistor(2, 1, 3);
		circuit.addResistor(0, 2, 2);
		circuit.addResistor(3, 2, 1);
		circuit.addResistor(0, 3, 1);
		System.out.println(circuit);
	}

}
