package nodal.view.builder;

import java.awt.Graphics;

import nodal.framework.Circuit;
import nodal.framework.Element;
import nodal.framework.Node;
import nodal.view.drawer.ElementDrawer;
import nodal.view.drawer.WireDrawer;
import nodal.view.util.DrawingUtils;

public class WireBuilder implements ElementBuilder {

	public static final ElementBuilder INSTANCE = new WireBuilder();

	private WireBuilder() {

	}

	@Override
	public boolean requiresValue() {
		return false;
	}

	@Override
	public boolean validateValue(double value) {
		return true;
	}

	@Override
	public String getValuePrompt() {
		return "";
	}

	@Override
	public Element createElement(Circuit circuit, Node neg, Node pos,
			double value) {
		return circuit.createWire(neg, pos);
	}

	@Override
	public ElementDrawer createElementDrawer(Element e, double value) {
		return new WireDrawer(e);
	}

	@Override
	public void draw(Graphics g, int sx, int sy, int ex, int ey) {
		DrawingUtils.drawWire(g, sx, sy, ex, ey);

	}

}
