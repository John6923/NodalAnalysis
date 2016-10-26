package nodal.framework;

import java.util.List;

/**
 * <p>
 * Represents a node in a circuit
 * </p>
 * Responsibilities:
 * <ul>
 * <li>Know nodal voltage</li>
 * <li>Know connected elements</li>
 * </ul>
 * 
 * @author John Dood
 *
 */
public interface Node {
	/**
	 * Returns nodal voltage. If the value cannot be calculated, returns NaN.
	 * 
	 * @return nodal voltage or NaN
	 */
	public double getVoltage();

	/**
	 * Returns a list of elements whose negative terminal is attached to this
	 * node
	 * 
	 * @return list of elements
	 */
	public List<Element> getInboundElements();

	/**
	 * Returns a list of elements whose positive terminal is attached to this
	 * node
	 * 
	 * @return list of elements
	 */
	public List<Element> getOutboundElements();

	/**
	 * Counts the number of elements connected to the node
	 * 
	 * @return number of elements connected to the node
	 */
	public int elementCount();

}
