package nodal.view;

import java.awt.Graphics;

import nodal.framework.Circuit;
import nodal.framework.Element;
import nodal.framework.Node;
import nodal.view.util.DrawingUtils;

public class CurrentSourceBuilder implements ElementBuilder {

	public static ElementBuilder INSTANCE = new CurrentSourceBuilder();

	private CurrentSourceBuilder() {

	}

	@Override
	public boolean requiresValue() {
		return true;
	}

	@Override
	public boolean validateValue(double value) {
		return Double.isFinite(value);
	}

	@Override
	public String getValuePrompt() {
		return "Enter current: ";
	}

	@Override
	public Element createElement(Circuit circuit, Node neg, Node pos,
			double value) {
		return circuit.createCurrentSource(neg, pos, value);
	}

	@Override
	public ElementDrawer createElementDrawer(Element e, double value) {
		return new CurrentSourceDrawer(e, value);
	}

	@Override
	public void draw(Graphics g, int sx, int sy, int ex, int ey) {
		DrawingUtils.drawCurrentSource(g, sx, sy, ex, ey);

	}

}
