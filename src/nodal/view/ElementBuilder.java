package nodal.view;

import java.awt.Graphics;

import nodal.framework.Circuit;
import nodal.framework.Element;
import nodal.framework.Node;

public interface ElementBuilder {
	public boolean requiresValue();

	public boolean validateValue(double value);

	public String getValuePrompt();

	public Element createElement(Circuit circuit, Node neg, Node pos, double value);
	
	public ElementDrawer createElementDrawer(Element e, double value);

	public void draw(Graphics g, int sx, int sy, int ex, int ey);

}
