package nodal.standard;

import nodal.framework.Element;
import nodal.framework.Node;
import nodal.util.Matrix;

/**
 * Node that can get its values from a matrix and add inbound and outbound
 * elements
 * 
 * @author John Dood
 *
 */
public interface MutableNode extends Node {

	/**
	 * Get the value for voltage based on the unit
	 * 
	 * @param m
	 *            Matrix to read from
	 * @param vRow
	 *            row that represents the voltage
	 */
	public void getFromMatrix(Matrix m, int vRow);

	/**
	 * Add an element as one with passive current flowing into the node
	 * 
	 * @param e
	 *            Element with inbound current
	 */
	public void addInbound(Element e);

	/**
	 * Add an element as one with passive current flowing out of the node
	 * 
	 * @param e
	 *            Element with outbound current
	 */
	public void addOutbound(Element e);
	
	/**
	 * Set the voltage to be NaN if the circuit is not solvable
	 */
	public void invalidate();

}
