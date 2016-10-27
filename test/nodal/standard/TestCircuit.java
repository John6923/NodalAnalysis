package nodal.standard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import nodal.framework.Circuit;
import nodal.framework.Element;
import nodal.framework.Node;
import nodal.framework.InvalidNodeException;

public class TestCircuit {
	private static double TEN_THOUSANDTHS_TOLERANCE = 0.0001;
	private static double ONE_MILLIONTHS_TOLERANCE = 0.000001;

	private Circuit circuit;

	@Before
	public void setUp() {
		circuit = new CircuitImpl();
	}

	@Test
	public void shouldInitiallyHaveNoElementsInElementList() {
		assertEquals("Circuit should have no elements", 0,
				circuit.getElements().size());
	}

	@Test
	public void shouldInitiallyHaveNoNodesInNodeList() {
		assertEquals("Circuit should have no nodes", 0,
				circuit.getNodes().size());
	}

	@Test
	public void shouldInitiallyHave0ElementCount() {
		assertEquals("elementCount should initially be 0", 0,
				circuit.elementCount());
	}

	@Test
	public void shouldInitiallyHave0NodeCount() {
		assertEquals("nodeCount should initially be 0", 0, circuit.nodeCount());
	}

	@Test
	public void shouldReturnNodeWhenOneIsCreated() {
		assertNotNull("Node should exist", circuit.createNode());
	}

	@Test
	public void shouldNotSaveNodeWhenOneIsCreatedWithoutAnyElements() {
		circuit.createNode();
		assertEquals("Number of nodes in list should be zero", 0,
				circuit.getNodes().size());
	}

	@Test
	public void shouldNotCountNodeWhenOneIsCreatedWithoutAnyElements() {
		circuit.createNode();
		assertEquals("Number of nodes in list should be zero", 0,
				circuit.nodeCount());
	}

	@Test
	public void shouldReturnElementWhenResistorIsCreated() {
		Node n1 = circuit.createNode();
		Node n2 = circuit.createNode();
		assertNotNull("Resistor should not be null",
				circuit.createResistor(n1, n2, 100));
	}

	@Test
	public void shouldReturnElementWhenCurrentSourceIsCreated() {
		Node n1 = circuit.createNode();
		Node n2 = circuit.createNode();
		assertNotNull("Current source should not be null",
				circuit.createCurrentSource(n1, n2, 100));
	}

	@Test
	public void shouldReturnElementWhenVoltageSourceIsCreated() {
		Node n1 = circuit.createNode();
		Node n2 = circuit.createNode();
		assertNotNull("Voltage source should not be null",
				circuit.createVoltageSource(n1, n2, 100));
	}

	@Test
	public void shouldSaveElementWhenResistorIsCreated() {
		Node n1 = circuit.createNode();
		Node n2 = circuit.createNode();
		Element resistor = circuit.createResistor(n1, n2, 100);
		List<Element> elements = circuit.getElements();
		assertEquals("Should have exactly 1 element", 1, elements.size());
		assertEquals("First element should be resistor", resistor,
				elements.get(0));
	}

	@Test
	public void shouldSaveNodesWhenResistorIsCreated() {
		Node n1 = circuit.createNode();
		Node n2 = circuit.createNode();
		circuit.createResistor(n1, n2, 100);
		List<Node> nodes = circuit.getNodes();
		assertEquals("Should have exactly 2 nodes", 2, nodes.size());
		assertTrue("Should contain first node", nodes.contains(n1));
		assertTrue("Should contain second node", nodes.contains(n2));
	}

	@Test
	public void shouldUpdateElementCount() {
		Node n1 = circuit.createNode();
		Node n2 = circuit.createNode();
		circuit.createResistor(n1, n2, 100);
		assertEquals("elementCount should be 1", 1, circuit.elementCount());
	}

	@Test
	public void shouldUpdateNodeCount() {
		Node n1 = circuit.createNode();
		Node n2 = circuit.createNode();
		circuit.createResistor(n1, n2, 100);
		assertEquals("nodeCount should be 2", 2, circuit.nodeCount());
	}

	@Test
	public void shouldNotDoubleUpNodesInNodeList() {
		Node gnd = circuit.createNode();
		Node a = circuit.createNode();
		Node b = circuit.createNode();
		circuit.createResistor(gnd, a, 100);
		circuit.createVoltageSource(gnd, b, 5);
		assertEquals("nodeCount should be 3", 3, circuit.nodeCount());
	}

	@Test(expected = InvalidNodeException.class)
	public void shouldThrowExceptionIfNegNodeIsNull() {
		Node n2 = circuit.createNode();
		circuit.createResistor(null, n2, 100);
	}

	@Test(expected = InvalidNodeException.class)
	public void shouldThrowExceptionIfPosNodeIsNull() {
		Node n1 = circuit.createNode();
		circuit.createCurrentSource(n1, null, 0.001);
	}

	@Test(expected = InvalidNodeException.class)
	public void shouldThrowExceptionIfNodesAreTheSame() {
		Node n = circuit.createNode();
		circuit.createVoltageSource(n, n, 5);
	}

	@Test
	public void shouldCreateDefensiveCopyOfNodes() {
		List<Node> l1 = circuit.getNodes();
		l1.add(null);
		assertEquals("list should have 1 node", 1, l1.size());
		List<Node> l2 = circuit.getNodes();
		assertEquals("list should have 0 nodes", 0, l2.size());

	}

	@Test
	public void shouldCreateDefensiveCopyOfElements() {
		List<Element> l1 = circuit.getElements();
		l1.add(null);
		assertEquals("list should have 1 element", 1, l1.size());
		List<Element> l2 = circuit.getElements();
		assertEquals("list should have 0 elements", 0, l2.size());
	}

	@Test
	public void shouldInitiallyHaveNullGround() {
		Node gnd = circuit.getGround();
		assertNull("Ground should initially be null", gnd);
	}

	@Test
	public void shouldStoreGroundAfterSet() {
		Node n = circuit.createNode();
		circuit.setGround(n);
		Node gnd = circuit.getGround();
		assertEquals("Ground should have been saved by set", n, gnd);
	}

	@Test(expected = InvalidNodeException.class)
	public void shouldThrowExceptionWhenAtteptingToSetGroundToNull() {
		circuit.setGround(null);
	}

	@Test
	public void shouldHaveAppropriateValueForVoltageSourceOnly() {
		Node n1 = circuit.createNode();
		Node n2 = circuit.createNode();
		circuit.setGround(n1);
		Element vse = circuit.createVoltageSource(n1, n2, 9);
		assertEquals("Voltage source should have 9V voltage", 9,
				vse.getVoltage(), TEN_THOUSANDTHS_TOLERANCE);
		assertEquals("Voltage source should have 0 current", 0,
				vse.getCurrent(), ONE_MILLIONTHS_TOLERANCE);
	}

	@Test
	public void shouldHaveAppropriateNodalValueForVoltageSourceOnly() {
		Node n1 = circuit.createNode();
		Node n2 = circuit.createNode();
		circuit.setGround(n1);
		circuit.createVoltageSource(n1, n2, 9);
		assertEquals("Node 1 should be 0V", 0, n1.getVoltage(),
				TEN_THOUSANDTHS_TOLERANCE);
		assertEquals("Node 2 should be 9V", 9, n2.getVoltage(),
				TEN_THOUSANDTHS_TOLERANCE);
	}

	@Test
	public void shouldNotBeAbleToSolveHangingCurrentSourceVoltage() {
		Node n1 = circuit.createNode();
		Node n2 = circuit.createNode();
		circuit.setGround(n1);
		Element cse = circuit.createCurrentSource(n1, n2, 0.001);
		assertEquals("Voltage across current source should be NaN", Double.NaN,
				cse.getVoltage(), 0);
	}

	@Test
	public void shouldNotBeAbleToSolveHangingCurrentSourceNodes() {
		Node n1 = circuit.createNode();
		Node n2 = circuit.createNode();
		circuit.setGround(n1);
		circuit.createCurrentSource(n1, n2, 0.001);
		assertEquals("Node 1 should be NaN", Double.NaN, n1.getVoltage(), 0);
		assertEquals("Node 2 should be NaN", Double.NaN, n2.getVoltage(), 0);
	}

	@Test
	public void shouldNotBeAbleToSolveDifferingVoltageSources() {
		Node n1 = circuit.createNode();
		Node n2 = circuit.createNode();
		circuit.setGround(n1);
		Element vs1 = circuit.createVoltageSource(n1, n2, 5);
		Element vs2 = circuit.createVoltageSource(n1, n2, 9);
		assertEquals("Current through first voltage source should be NaN",
				Double.NaN, vs1.getCurrent(), 0);
		assertEquals("Current through second voltage source should be NaN",
				Double.NaN, vs2.getCurrent(), 0);
		assertEquals("Node 1 should be NaN", Double.NaN, n1.getVoltage(), 0);
		assertEquals("Node 2 should be NaN", Double.NaN, n2.getVoltage(), 0);
	}

	@Test
	public void shouldNotBeAbleToSolveDifferingCurrentSources() {
		Node n1 = circuit.createNode();
		Node n2 = circuit.createNode();
		circuit.setGround(n1);
		Element cs1 = circuit.createCurrentSource(n1, n2, 0.001);
		Element cs2 = circuit.createCurrentSource(n2, n1, 0.002);
		assertEquals("Voltage across first current source should be NaN",
				Double.NaN, cs1.getVoltage(), 0);
		assertEquals("Voltage across second current source should be NaN",
				Double.NaN, cs2.getVoltage(), 0);
		assertEquals("Node 1 should be NaN", Double.NaN, n1.getVoltage(), 0);
		assertEquals("Node 2 should be NaN", Double.NaN, n2.getVoltage(), 0);
	}

	public void validate(String name, Element e, double v, double i) {
		assertEquals("Voltage across " + name + " should be " + v + "V", v,
				e.getVoltage(), TEN_THOUSANDTHS_TOLERANCE);
		assertEquals("Current through " + name + " should be " + i + "A", i,
				e.getCurrent(), ONE_MILLIONTHS_TOLERANCE);
	}

	public void validate(String name, Node n, double v) {
		assertEquals("Voltage at " + name + " should be " + v + "V", v,
				n.getVoltage(), TEN_THOUSANDTHS_TOLERANCE);
	}

	@Test
	public void shouldCompletelySolveVoltageSourceResistorCircuit() {
		Node gnd = circuit.createNode();
		circuit.setGround(gnd);
		Node va = circuit.createNode();
		Element source = circuit.createVoltageSource(gnd, va, 3.3);
		Element r = circuit.createResistor(gnd, va, 1000);
		validate("source", source, 3.3, -0.0033);
		validate("resistor", r, 3.3, 0.0033);
		validate("ground", gnd, 0);
		validate("va", va, 3.3);
	}

	@Test
	public void shouldCompletelySolveCurrentSourceResistorCircuit() {
		Node gnd = circuit.createNode();
		circuit.setGround(gnd);
		Node va = circuit.createNode();
		Element source = circuit.createCurrentSource(gnd, va, 0.0033);
		Element r = circuit.createResistor(gnd, va, 1000);
		validate("source", source, 3.3, -0.0033);
		validate("resistor", r, 3.3, 0.0033);
		validate("ground", gnd, 0);
		validate("va", va, 3.3);
	}

	@Test
	public void shouldCompletelySolveSlightlyMoreComplexVoltageSourceCircuit() {
		Node gnd = circuit.createNode();
		circuit.setGround(gnd);
		Node n1 = circuit.createNode();
		Node n2 = circuit.createNode();
		Node n3 = circuit.createNode();
		Element source = circuit.createVoltageSource(gnd, n1, 8);
		Element r1 = circuit.createResistor(n2, n1, 3);
		Element r2 = circuit.createResistor(gnd, n2, 2);
		Element r3 = circuit.createResistor(n3, n2, 1);
		Element r4 = circuit.createResistor(gnd, n3, 1);
		validate("source", source, 8, -2);
		validate("r1", r1, 6, 2);
		validate("r2", r2, 2, 1);
		validate("r3", r3, 1, 1);
		validate("r4", r4, 1, 1);
		validate("n1", n1, 8);
		validate("n2", n2, 2);
		validate("n3", n3, 1);
	}
	
	@Test
	public void shouldCompletelySolveSlightlyMoreComplexCurrentSourceCircuit() {
		Node gnd = circuit.createNode();
		circuit.setGround(gnd);
		Node n1 = circuit.createNode();
		Node n2 = circuit.createNode();
		Node n3 = circuit.createNode();
		Element source = circuit.createCurrentSource(gnd, n1, 2);
		Element r1 = circuit.createResistor(n2, n1, 3);
		Element r2 = circuit.createResistor(gnd, n2, 2);
		Element r3 = circuit.createResistor(n3, n2, 1);
		Element r4 = circuit.createResistor(gnd, n3, 1);
		validate("source", source, 8, -2);
		validate("r1", r1, 6, 2);
		validate("r2", r2, 2, 1);
		validate("r3", r3, 1, 1);
		validate("r4", r4, 1, 1);
		validate("n1", n1, 8);
		validate("n2", n2, 2);
		validate("n3", n3, 1);
	}
	
	@Test
	public void shouldSolveSimpleTwoCurrentSourceCircuit() {
		Node gnd = circuit.createNode();
		circuit.setGround(gnd);
		Node va = circuit.createNode();
		circuit.createCurrentSource(gnd, va, 0.025);
		circuit.createCurrentSource(gnd, va, 0.015);
		Element r = circuit.createResistor(gnd, va, 500);
		validate("500 Ohm resistor", r, 20, 0.04);
	}
	
	@Test
	public void shouldSolveSimpleTwoVoltageSourceCircuit() {
		Node n1 = circuit.createNode();
		Node n2 = circuit.createNode();
		Node n3 = circuit.createNode();
		circuit.setGround(n1);
		circuit.createVoltageSource(n1, n2, 5);
		circuit.createVoltageSource(n2, n3, 9);
		Element r = circuit.createResistor(n1, n3, 10000);
		validate("10 kOhm resistor", r, 14, 0.0014);
		
	}
	
	
	
}
