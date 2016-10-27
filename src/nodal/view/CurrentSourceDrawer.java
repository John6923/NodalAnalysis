package nodal.view;

import java.awt.Color;
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
		DrawingUtils.drawCurrentSource(g, start.getX(), start.getY(),
				end.getX(), end.getY());
		String i = String.format("%.1fA", current);

		String[] values = null;
		Color[] colors = null;
		if (Double.isFinite(currentSource.getVoltage())) {
			String v = String.format("%f V", currentSource.getVoltage());
			values = new String[] { i, v };
			colors = new Color[] { Color.BLACK, Color.RED};
		} else {
			values = new String[] { i };
			colors = new Color[] { Color.BLACK };
		}

		DrawingUtils.drawLabels(g, start.getX(), start.getY(), end.getX(),
				end.getY(), colors, values);
	}

}
