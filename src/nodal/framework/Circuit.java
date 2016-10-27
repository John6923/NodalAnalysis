package nodal.framework;

import java.util.List;

/**
 * <p>
 * Focal point for manipulation of a circuit
 * </p>
 * Responsibilities:
 * <ul>
 * <li>Know elements in circuit</li>
 * <li>Know nodes with at least one element</li>
 * <li>Create new nodes</li>
 * <li>Create new elements
 * <ul>
 * <li>Resistors</li>
 * <li>Current sources</li>
 * <li>Voltage sources</li>
 * </ul>
 * </li>
 * <li>Calculate and update voltages and currents through each element when
 * appropriate</li>
 * </ul>
 * 
 * @author John Dood
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
	 * least one element connected to them. Note: if the ground node is not
	 * connected to any other element it will not be in this list
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
	 * @throws NullPointerException
	 *             if either {@code pos} or {@code neg} node is null
	 * @throws InvalidNodeException
	 *             if positive and negative nodes are the same
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
	 * @throws NullPointerException
	 *             if either {@code pos} or {@code neg} node is null
	 * @throws InvalidNodeException
	 *             if positive and negative nodes are the same
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
	 * @throws NullPointerException
	 *             if either {@code pos} or {@code neg} node is null
	 * @throws InvalidNodeException
	 *             if positive and negative nodes are the same
	 */
	public Element createVoltageSource(Node neg, Node pos, double voltage);
	
	/**
	 * Sets the provided node as the ground node for the entire circuit
	 * 
	 * @param gnd
	 *            Node to be the ground node
	 * @throws NullPointerException
	 *             if {@code gnd} is null    
	 */
	public void setGround(Node gnd);
	
	/**
	 * Returns the ground node for the circuit
	 * 
	 * @return ground node or null if it has not yet been set
	 */
	public Node getGround();

}
