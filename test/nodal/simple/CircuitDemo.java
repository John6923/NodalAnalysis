package nodal.simple;

public class CircuitDemo {

	public static void main(String[] args) {
		SimpleCircuitFacade circuit = new SimpleCircuit();
		circuit.setGround(0);
		circuit.createResistor(0, 1, 2);
		circuit.createCurrentSource(0, 1, 4);
		System.out.println(circuit);
		circuit = new SimpleCircuit();
		circuit.setGround(0);
		circuit.createCurrentSource(0, 1, 2);
		circuit.createResistor(2, 1, 3);
		circuit.createResistor(0, 2, 2);
		circuit.createResistor(3, 2, 1);
		circuit.createResistor(0, 3, 1);
		System.out.println(circuit);
		circuit = new SimpleCircuit();
		circuit.setGround(0);
		circuit.createVoltageSource(0, 1, 8);
		circuit.createResistor(2, 1, 3000);
		circuit.createResistor(0, 2, 2000);
		circuit.createResistor(3, 2, 1000);
		circuit.createResistor(0, 3, 1000);
		System.out.println(circuit);
	}

}
