package nodal.view;

import java.awt.Graphics;

import nodal.framework.Circuit;
import nodal.framework.Element;
import nodal.framework.Node;
import nodal.view.util.DrawingUtils;

public class ResistorBuilder implements ElementBuilder {
	
	public static ElementBuilder INSTANCE = new ResistorBuilder();
	
	private ResistorBuilder() {
		
	}
	
	@Override
	public boolean requiresValue() {
		return true;
	}

	@Override
	public boolean validateValue(double value) {
		return Double.isFinite(value) && value > 0;
	}

	@Override
	public String getValuePrompt() {
		return "Enter resistance: ";
	}

	@Override
	public Element createElement(Circuit circuit, Node neg, Node pos,
			double value) {
		return circuit.createResistor(neg, pos, value);
	}

	@Override
	public ElementDrawer createElementDrawer(Element e, double value) {
		return new ResistorDrawer(e, value);
	}
	
	@Override
	public void draw(Graphics g, int sx, int sy, int ex, int ey) {
		DrawingUtils.drawResistor(g, sx, sy, ex, ey);
		
	}

}
