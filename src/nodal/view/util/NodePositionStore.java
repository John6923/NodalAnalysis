package nodal.view.util;

import java.util.Collection;

import nodal.framework.Node;

/**
 * Associates nodes and positions such that they can be looked up in either
 * direction
 * 
 * @author John Dood
 *
 */
public interface NodePositionStore {

	/**
	 * Looks up which node goes with the provided position
	 * 
	 * @param p
	 *            Position on graph
	 * @return The node associated with the position or null if the position is
	 *         not in the store
	 */
	public Node get(Position p);

	/**
	 * Looks for an entry containing the provided position
	 * 
	 * @param p
	 *            Position to look for
	 * @return true if the store contains the node, false otherwise
	 */
	public boolean contains(Position p);

	/**
	 * Looks up which position goes with the provided node
	 * 
	 * @param n
	 *            Node in circuit
	 * @return The position associated with the node or null if the node is not
	 *         in the store
	 */
	public Position get(Node n);

	/**
	 * Looks for an entry containing the provided node
	 * 
	 * @param n
	 *            Node to look for
	 * @return true if the store contains the node, false otherwise
	 */
	public boolean contains(Node n);

	/**
	 * Associates the node and the position in the store
	 * 
	 * @param n Node to add
	 * @param p Position to add
	 * @throws NodePositionInUseException if either the Node or the Position is in the store
	 */
	public void add(Node n, Position p);

	/**
	 * Gets a collection of all of the nodes in the store
	 * 
	 * @return collection of all nodes
	 */
	public Collection<Node> getNodes();

	/**
	 * Gets a collection of all the positions in the store
	 * 
	 * @return collection of all positions
	 */
	public Collection<Position> getPositions();
}
