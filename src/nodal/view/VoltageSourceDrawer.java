package nodal.view;

import java.awt.Color;
import java.awt.Graphics;

import nodal.framework.Element;
import nodal.view.util.DrawingUtils;
import nodal.view.util.NodePositionStore;
import nodal.view.util.Position;

public class VoltageSourceDrawer implements ElementDrawer {
	private double voltage;
	private Element voltageSource;

	public VoltageSourceDrawer(Element voltageSource, double voltage) {
		this.voltage = voltage;
		this.voltageSource = voltageSource;
	}

	@Override
	public void draw(Graphics g, NodePositionStore nps) {
		Position start = nps.get(voltageSource.getNegitiveNode());
		Position end = nps.get(voltageSource.getPositiveNode());
		DrawingUtils.drawVoltageSource(g, start.getX(), start.getY(),
				end.getX(), end.getY());
		String v = String.format("%.2fV", voltage);
		String[] values = null;
		Color[] colors = null;
		if (Double.isFinite(voltageSource.getCurrent())) {
			String i = String.format("%f A", voltageSource.getCurrent());
			values = new String[] { v, i };
			colors = new Color[] { Color.BLACK, Color.BLUE };
		} else {
			values = new String[] { v };
			colors = new Color[] { Color.BLACK };
		}
		DrawingUtils.drawLabels(g, start.getX(), start.getY(), end.getX(),
				end.getY(), colors, values);
	}

}
