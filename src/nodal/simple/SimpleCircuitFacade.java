package nodal.simple;

/**
 * Provides an easy to use interface for {@link nodal.framework.Circuit}
 * 
 * @author John Dood
 *
 */
public interface SimpleCircuitFacade {

	/**
	 * Add a resistor to the circuit given the nodes on either end
	 * 
	 * @param nodeNeg
	 *            The node passive current flows into
	 * @param nodePos
	 *            The node passive current flows out of
	 * @param resistance
	 *            The resistance of the element
	 */
	public void createResistor(int nodeNeg, int nodePos, double resistance);

	/**
	 * Creates a new current source attached to the given nodes. Note that the
	 * current rating uses the active sign convention.
	 * 
	 * @param nodeNeg
	 *            node current should flow out of
	 * @param nodePos
	 *            node current should flow in to
	 * @param current
	 *            The current rating for the source
	 */
	public void createCurrentSource(int nodeNeg, int nodePos, double current);

	/**
	 * 
	 * 
	 * @param nodeNeg
	 *            node on the negative side of the voltage source
	 * @param nodePos
	 *            node on the positive side of the voltage source
	 * @param voltage
	 */
	public void createVoltageSource(int nodeNeg, int nodePos, double voltage);

	/**
	 * Node number to set as the ground (reference) node
	 * 
	 * @param node
	 *            node to be set as ground
	 */
	public void setGround(int node);

	/**
	 * Returns the <i>passive</i> current through the element (from positive
	 * node to negative node)
	 * 
	 * @param element
	 *            element to measure through
	 * @return The <i>passive</i> current through the element in amps
	 */
	public double getCurrent(int element);

	/**
	 * Returns the voltage between the positive and negative nodes of the
	 * element
	 * 
	 * @param element
	 *            element to measure across
	 * @return The voltage in volts
	 */
	public double getVoltage(int element);

	/**
	 * Returns the voltage between the given node and ground
	 * 
	 * @param node
	 *            node to measure w.r.t. ground
	 * @return The voltage in volts
	 */
	public double getNodalVoltage(int node);

	/**
	 * Returns the number of nodes in the circuit
	 * 
	 * @return number of nodes in the circuit
	 */
	public int getNodeCount();

	/**
	 * Returns the number of elements in the circuit
	 * 
	 * @return number of elements in the circuit
	 */
	public int getElementCount();

}
