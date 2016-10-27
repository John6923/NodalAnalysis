package nodal.view.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import nodal.framework.Node;

public class DualHashMapNodePositionStore implements NodePositionStore {
	private Map<Position, Node> positionToNode = new HashMap<>();
	private Map<Node, Position> nodeToPosition = new HashMap<>();
	
	
	@Override
	public Node get(Position p) {
		return positionToNode.get(p);
	}

	@Override
	public boolean contains(Position p) {
		return positionToNode.containsKey(p);
	}

	@Override
	public Position get(Node n) {
		return nodeToPosition.get(n);
	}

	@Override
	public boolean contains(Node n) {
		return nodeToPosition.containsKey(n);
	}

	@Override
	public void add(Node n, Position p) {
		if(contains(n))
			throw new NodePositionInUseException("Node is already in store");
		if(contains(p))
			throw new NodePositionInUseException("Position is already in store");
		nodeToPosition.put(n, p);
		positionToNode.put(p, n);

	}

	@Override
	public Collection<Node> getNodes() {
		return nodeToPosition.keySet();
	}

	@Override
	public Collection<Position> getPositions() {
		return positionToNode.keySet();
	}

}
