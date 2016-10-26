package nodal.framework;

import java.util.List;

/**
 * Focal point for manipulation of a circuit
 * 
 * 
 * 
 * @author John6
 *
 */
public interface Circuit {
	/**
	 * Returns all the elements in the circuit
	 * 
	 * @return list of elements
	 */
	public List<Element> getElements();

	/**
	 * Returns the number of elements in this circuit
	 * 
	 * @return number of elements in circuit
	 */
	public int elementCount();

	/**
	 * Goes through all the nodes in the circuit and returns the ones with at
	 * least one element connected to them
	 * 
	 * @return list of nodes
	 */
	public List<Node> getNodes();

	/**
	 * Returns the number of nodes connected to at least one element
	 * 
	 * @return number of nodes connected to at least one element
	 */
	public int nodeCount();

	/**
	 * Creates a new node with nothing attached to it
	 * 
	 * @return created node
	 */
	public Node createNode();

	/**
	 * Creates a new resistor given the nodes on the negative and positive
	 * terminals and the resistance
	 * 
	 * @param neg
	 *            Node attached to negative terminal
	 * @param pos
	 *            Node attacked to positive terminal
	 * @param resistance
	 *            Resistance of the resistor in Ohms
	 * @return Element representing the created resistor
	 */
	public Element createResistor(Node neg, Node pos, double resistance);

	/**
	 * Creates a new current source given the nodes on the negative and positive
	 * terminals and the <i>active</i> current. Note that this number is
	 * opposite in sign to the current returned by {@code getCurrent()}.
	 * 
	 * @param neg
	 *            Node attached to negative terminal
	 * @param pos
	 *            Node attacked to positive terminal
	 * @param current
	 *            Current produced by the source
	 * @return Element representing the created current source
	 */
	public Element createCurrentSource(Node neg, Node pos, double current);

	/**
	 * Creates a new voltage source given the nodes on the negative and positive
	 * terminals and the voltage setting.
	 * 
	 * @param neg
	 *            Node attached to negative terminal
	 * @param pos
	 *            Node attacked to positive terminal
	 * @param voltage
	 *            Voltage produced by the source
	 * @return Element representing the created voltage source
	 */
	public Element createVoltageSource(Node neg, Node pos, double voltage);
	
	
}
