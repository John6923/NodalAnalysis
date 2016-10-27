package nodal.view;

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
		DrawingUtils.drawVoltageSource(g, start.getX(), start.getY(), end.getX(), end.getY());
		String s = String.format("%.2fV", voltage);
		DrawingUtils.drawLabel(g, start.getX(), start.getY(), end.getX(), end.getY(), s);
	}

}
