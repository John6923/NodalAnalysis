package nodal.view;

import java.awt.Graphics;

import nodal.framework.Element;
import nodal.view.util.DrawingUtils;
import nodal.view.util.NodePositionStore;
import nodal.view.util.Position;

public class ResistorDrawer implements ElementDrawer {
	private double resistance;
	private Element resistor;
	
	public ResistorDrawer(Element resistor, double resistance) {
		this.resistor = resistor;
		this.resistance = resistance;
	}

	@Override
	public void draw(Graphics g, NodePositionStore nps) {
		Position start = nps.get(resistor.getNegitiveNode());
		Position end = nps.get(resistor.getPositiveNode());
		
		DrawingUtils.drawResistor(g, start.getX(), start.getY(), end.getX(), end.getY());
		String s = String.format("%.1f Ohm", resistance);
		DrawingUtils.drawLabel(g, start.getX(), start.getY(), end.getX(), end.getY(), s);
	}

}
