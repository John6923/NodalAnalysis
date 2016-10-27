package nodal.view.builder;

import java.awt.Graphics;

import nodal.framework.Circuit;
import nodal.framework.Element;
import nodal.framework.Node;
import nodal.view.drawer.ElementDrawer;
import nodal.view.drawer.VoltageSourceDrawer;
import nodal.view.util.DrawingUtils;

public class VoltageSourceBuilder implements ElementBuilder {

	public static final ElementBuilder INSTANCE = new VoltageSourceBuilder();

	private VoltageSourceBuilder() {

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
		return "Enter voltage: ";
	}

	@Override
	public Element createElement(Circuit circuit, Node neg, Node pos,
			double value) {
		return circuit.createVoltageSource(neg, pos, value);
	}

	@Override
	public ElementDrawer createElementDrawer(Element e, double value) {
		return new VoltageSourceDrawer(e, value);
	}

	@Override
	public void draw(Graphics g, int sx, int sy, int ex, int ey) {
		DrawingUtils.drawVoltageSource(g, sx, sy, ex, ey);

	}

}
