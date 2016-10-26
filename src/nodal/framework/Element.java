package nodal.framework;

/**
 * <p>
 * Represents an element in a circuit
 * </p>
 * Responsibilities:
 * <ul>
 * <li>Know connected nodes</li>
 * <li>Know current</li>
 * <li>Know voltage</li>
 * </ul>
 * 
 * @author John Dood
 * 
 */
public interface Element {
	/**
	 * The node attached to the positive terminal of this element
	 * 
	 * @return positive node
	 */
	public Node getPositiveNode();

	/**
	 * The node attached to the negative terminal of this element
	 * 
	 * @return negative node
	 */
	public Node getNegitiveNode();

	/**
	 * Returns the voltage difference between the positive and negative nodes.
	 * If the value cannot be calculated, returns NaN.
	 * 
	 * @return the voltage difference between the positive and negative nodes
	 */
	public double getVoltage();

	/**
	 * Returns the current flowing from the positive node to the negative node
	 * i.e. the passive current. If the value cannot be calculated, returns NaN.
	 * 
	 * @return The passive current or NaN
	 */
	public double getCurrent();
}
