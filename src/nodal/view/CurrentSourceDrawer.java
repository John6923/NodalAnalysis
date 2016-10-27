package nodal.view;

import java.awt.Graphics;

import nodal.framework.Element;
import nodal.view.util.DrawingUtils;
import nodal.view.util.NodePositionStore;
import nodal.view.util.Position;

public class CurrentSourceDrawer implements ElementDrawer {
	private double current;
	private Element currentSource;

	public CurrentSourceDrawer(Element currentSource, double current) {
		this.currentSource = currentSource;
		this.current = current;
	}
	
	@Override
	public void draw(Graphics g, NodePositionStore nps) {
		Position start = nps.get(currentSource.getNegitiveNode());
		Position end = nps.get(currentSource.getPositiveNode());
		DrawingUtils.drawCurrentSource(g, start.getX(), start.getY(), end.getX(), end.getY());
		String s = String.format("%.1fA", current);
		DrawingUtils.drawLabel(g, start.getX(), start.getY(), end.getX(), end.getY(), s);
	}

}
