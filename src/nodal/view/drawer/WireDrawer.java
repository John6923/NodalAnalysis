package nodal.view.drawer;

import java.awt.Graphics;

import nodal.framework.Element;
import nodal.view.util.DrawingUtils;
import nodal.view.util.NodePositionStore;
import nodal.view.util.Position;

public class WireDrawer implements ElementDrawer {

	private Element wire;

	public WireDrawer(Element wire) {
		this.wire = wire;
	}

	@Override
	public void draw(Graphics g, NodePositionStore nps) {
		Position start = nps.get(wire.getNegitiveNode());
		Position end = nps.get(wire.getPositiveNode());
		DrawingUtils.drawWire(g, start.getX(), start.getY(), end.getX(),
				end.getY());
	}

}
