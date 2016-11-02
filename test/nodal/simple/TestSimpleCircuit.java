package nodal.simple;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import nodal.framework.InvalidNodeException;

public class TestSimpleCircuit {
	private static double TEN_THOUSANDTHS_TOLERANCE = 0.0001;
	private static double ONE_MILLIONTHS_TOLERANCE = 0.000001;

	private SimpleCircuitFacade circuit;

	@Before
	public void setUp() {
		circuit = new SimpleCircuit();
	}

	@Test
	public void shouldInitiallyHave0ElementCount() {
		assertEquals("elementCount should initially be 0", 0,
				circuit.getElementCount());
	}

	@Test
	public void shouldInitiallyHave0NodeCount() {
		assertEquals("nodeCount should initially be 0", 0,
				circuit.getNodeCount());
	}

	@Test
	public void shouldSaveElementWhenResistorIsCreated() {
		circuit.createResistor(0, 1, 100);
		assertEquals("Should have exactly 1 element", 1, circuit.getElementCount());
	}
	
	@Test
	public void shouldSaveElementWhenCurrentSourceIsCreated() {
		circuit.createCurrentSource(0, 1, 0.1);
		assertEquals("Should have exactly 1 element", 1, circuit.getElementCount());
	}
	
	@Test
	public void shouldSaveElementWhenVoltageSourceIsCreated() {
		circuit.createVoltageSource(0, 1, 10);
		assertEquals("Should have exactly 1 element", 1, circuit.getElementCount());
	}

	@Test
	public void shouldSaveNodesWhenResistorIsCreated() {
		circuit.createResistor(0, 1, 100);
		assertEquals("Should have exactly 2 nodes", 2, circuit.getNodeCount());
	}

	@Test
	public void shouldNotDoubleUpNodesInNodeList() {
		circuit.createResistor(0, 1, 100);
		circuit.createVoltageSource(0, 2, 5);
		assertEquals("nodeCount should be 3", 3, circuit.getNodeCount());
	}

	@Test(expected = InvalidNodeException.class)
	public void shouldThrowExceptionIfNegNodeIsNegative() {
		circuit.createResistor(-1, 0, 100);
	}

	@Test(expected = InvalidNodeException.class)
	public void shouldThrowExceptionIfPosNodeIsNegative() {
		circuit.createCurrentSource(0, -1, 0.001);
	}

	@Test(expected = InvalidNodeException.class)
	public void shouldThrowExceptionIfNodesAreTheSame() {
		circuit.createVoltageSource(0, 0, 5);
	}

	@Test(expected = InvalidNodeException.class)
	public void shouldThrowExceptionWhenAtteptingToSetGroundToNegative() {
		circuit.setGround(-1);
	}
	
	@Test(expected = InvalidNodeException.class)
	public void shouldThrowExceptionWhenAtteptingToReadVoltageOfUndefinedNode() {
		circuit.setGround(0);
		circuit.createVoltageSource(0, 1, 9);
		circuit.getNodalVoltage(2);
	}
	
	@Test(expected = NullPointerException.class)
	public void shouldThrowExceptionWhenAtteptingToReadVoltageOfUndefinedElement() {
		circuit.setGround(0);
		circuit.createVoltageSource(0, 1, 9);
		circuit.getVoltage(1);
	}
	
	@Test(expected = NullPointerException.class)
	public void shouldThrowExceptionWhenAtteptingToReadCurrentOfUndefinedElement() {
		circuit.setGround(0);
		circuit.createVoltageSource(0, 1, 9);
		circuit.getCurrent(1);
	}

	@Test
	public void shouldHaveAppropriateValueForVoltageSourceOnly() {
		circuit.setGround(0);
		circuit.createVoltageSource(0, 1, 9);
		assertEquals("Voltage source should have 9V voltage", 9,
				circuit.getVoltage(0), TEN_THOUSANDTHS_TOLERANCE);
		assertEquals("Voltage source should have 0 current", 0,
				circuit.getCurrent(0), ONE_MILLIONTHS_TOLERANCE);
	}

	@Test
	public void shouldHaveAppropriateNodalValueForVoltageSourceOnly() {
		circuit.setGround(0);
		circuit.createVoltageSource(0, 1, 9);
		assertEquals("Node 1 should be 0V", 0, circuit.getNodalVoltage(0),
				TEN_THOUSANDTHS_TOLERANCE);
		assertEquals("Node 2 should be 9V", 9, circuit.getNodalVoltage(1),
				TEN_THOUSANDTHS_TOLERANCE);
	}

	@Test
	public void shouldNotBeAbleToSolveHangingCurrentSourceVoltage() {
		circuit.setGround(0);
		circuit.createCurrentSource(0, 1, 0.001);
		assertEquals("Voltage across current source should be NaN", Double.NaN,
				circuit.getVoltage(0), 0);
	}

	@Test
	public void shouldNotBeAbleToSolveHangingCurrentSourceNodes() {
		circuit.setGround(0);
		circuit.createCurrentSource(0, 1, 0.001);
		assertEquals("Node 1 should be NaN", Double.NaN, circuit.getNodalVoltage(0), 0);
		assertEquals("Node 2 should be NaN", Double.NaN, circuit.getNodalVoltage(1), 0);
	}

	@Test
	public void shouldNotBeAbleToSolveDifferingVoltageSources() {
		circuit.setGround(0);
		circuit.createVoltageSource(0, 1, 5);
		circuit.createVoltageSource(0, 1, 9);
		assertEquals("Current through first voltage source should be NaN",
				Double.NaN, circuit.getCurrent(0), 0);
		assertEquals("Current through second voltage source should be NaN",
				Double.NaN, circuit.getCurrent(1), 0);
		assertEquals("Node 1 should be NaN", Double.NaN, circuit.getNodalVoltage(0), 0);
		assertEquals("Node 2 should be NaN", Double.NaN, circuit.getNodalVoltage(1), 0);
	}

	@Test
	public void shouldNotBeAbleToSolveDifferingCurrentSources() {
		circuit.setGround(0);
		circuit.createCurrentSource(0, 1, 0.001);
		circuit.createCurrentSource(1, 0, 0.002);
		assertEquals("Voltage across first current source should be NaN",
				Double.NaN, circuit.getVoltage(0), 0);
		assertEquals("Voltage across second current source should be NaN",
				Double.NaN, circuit.getVoltage(1), 0);
		assertEquals("Node 1 should be NaN", Double.NaN, circuit.getNodalVoltage(0), 0);
		assertEquals("Node 2 should be NaN", Double.NaN, circuit.getNodalVoltage(1), 0);
	}

	public void validate(String name, int element, double v, double i) {
		assertEquals("Voltage across " + name + " should be " + v + "V", v,
				circuit.getVoltage(element), TEN_THOUSANDTHS_TOLERANCE);
		assertEquals("Current through " + name + " should be " + i + "A", i,
				circuit.getCurrent(element), ONE_MILLIONTHS_TOLERANCE);
	}

	public void validate(String name, int node, double v) {
		assertEquals("Voltage at " + name + " should be " + v + "V", v,
				circuit.getNodalVoltage(node), TEN_THOUSANDTHS_TOLERANCE);
	}

	@Test
	public void shouldCompletelySolveVoltageSourceResistorCircuit() {
		circuit.setGround(0);
		circuit.createVoltageSource(0, 1, 3.3);
		circuit.createResistor(0, 1, 1000);
		validate("source", 0, 3.3, -0.0033);
		validate("resistor", 1, 3.3, 0.0033);
		validate("ground", 0, 0);
		validate("va", 1, 3.3);
	}

	@Test
	public void shouldCompletelySolveCurrentSourceResistorCircuit() {
		circuit.setGround(0);
		circuit.createCurrentSource(0, 1, 0.0033);
		circuit.createResistor(0, 1, 1000);
		validate("source", 0, 3.3, -0.0033);
		validate("resistor", 1, 3.3, 0.0033);
		validate("ground", 0, 0);
		validate("va", 1, 3.3);
	}

	@Test
	public void shouldCompletelySolveSlightlyMoreComplexVoltageSourceCircuit() {
		circuit.setGround(0);
		circuit.createVoltageSource(0, 1, 8);
		circuit.createResistor(2, 1, 3);
		circuit.createResistor(0, 2, 2);
		circuit.createResistor(3, 2, 1);
		circuit.createResistor(0, 3, 1);
		validate("source", 0, 8, -2);
		validate("r1", 1, 6, 2);
		validate("r2", 2, 2, 1);
		validate("r3", 3, 1, 1);
		validate("r4", 4, 1, 1);
		validate("n1", 1, 8);
		validate("n2", 2, 2);
		validate("n3", 3, 1);
	}

	@Test
	public void shouldCompletelySolveSlightlyMoreComplexCurrentSourceCircuit() {
		circuit.setGround(0);
		circuit.createCurrentSource(0, 1, 2);
		circuit.createResistor(2, 1, 3);
		circuit.createResistor(0, 2, 2);
		circuit.createResistor(3, 2, 1);
		circuit.createResistor(0, 3, 1);
		validate("source", 0, 8, -2);
		validate("r1", 1, 6, 2);
		validate("r2", 2, 2, 1);
		validate("r3", 3, 1, 1);
		validate("r4", 4, 1, 1);
		validate("n1", 1, 8);
		validate("n2", 2, 2);
		validate("n3", 3, 1);
	}

	@Test
	public void shouldSolveSimpleTwoCurrentSourceCircuit() {
		circuit.setGround(0);
		circuit.createCurrentSource(0, 1, 0.025);
		circuit.createCurrentSource(0, 1, 0.015);
		circuit.createResistor(0, 1, 500);
		validate("500 Ohm resistor", 2, 20, 0.04);
	}

	@Test
	public void shouldSolveSimpleTwoVoltageSourceCircuit() {
		circuit.setGround(0);
		circuit.createVoltageSource(0, 1, 5);
		circuit.createVoltageSource(1, 2, 9);
		circuit.createResistor(0, 2, 10000);
		validate("10 kOhm resistor", 2, 14, 0.0014);

	}

}
