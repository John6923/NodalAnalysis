package nodal.view.drawer;

import static nodal.util.UnitFormater.formatVolt;
import static nodal.util.UnitFormater.formatOhm;
import static nodal.util.UnitFormater.formatAmp;

import java.awt.Color;
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

		DrawingUtils.drawResistor(g, start.getX(), start.getY(), end.getX(),
				end.getY());

		String r = formatOhm(resistance);
		String[] values = null;
		Color[] colors = null;
		if (Double.isFinite(resistor.getCurrent())
				&& Double.isFinite(resistor.getVoltage())) {
			String v = formatVolt(resistor.getVoltage());
			String i = formatAmp(resistor.getCurrent());
			values = new String[] { r, v, i };
			colors = new Color[] { Color.BLACK, Color.RED, Color.BLUE };
		} else {
			values = new String[] { r };
			colors = new Color[] { Color.BLACK };
		}

		DrawingUtils.drawLabels(g, start.getX(), start.getY(), end.getX(),
				end.getY(), colors, values);
	}

}
