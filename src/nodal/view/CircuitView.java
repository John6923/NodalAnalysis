package nodal.view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import nodal.framework.Circuit;
import nodal.framework.Element;
import nodal.framework.Node;
import nodal.standard.CircuitImpl;
import nodal.view.util.DualHashMapNodePositionStore;
import nodal.view.util.NodePositionStore;
import nodal.view.util.Position;

public class CircuitView {
	private Map<Element, ElementDrawer> elements = new HashMap<>();
	private NodePositionStore nodes = new DualHashMapNodePositionStore();

	private Circuit circuit = new CircuitImpl();

	private boolean drawingLine = false;
	private int placedX, placedY, hoverX, hoverY;
	private ElementBuilder builder = ResistorBuilder.INSTANCE;

	public void paint(Graphics g, int screenWidth, int screenHeight) {
		for (Element e : elements.keySet()) {
			ElementDrawer ce = elements.get(e);
			ce.draw(g, nodes);
		}
		if (drawingLine) {
			g.setColor(Color.BLACK);
			g.fillOval(placedX - 3, placedY - 3, 6, 6);
			g.fillOval(hoverX - 3, hoverY - 3, 6, 6);
			builder.draw(g, placedX, placedY, hoverX, hoverY);
		} else {
			g.setColor(Color.BLACK);
			g.drawOval(hoverX - 3, hoverY - 3, 6, 6);
		}
	}

	public void setCurrentBuilder(ElementBuilder builder) {
		this.builder = builder;
	}

	public void addEnd(int x, int y) {
		hoverX = round(x);
		hoverY = round(y);
		if (drawingLine) {
			double value = Double.NaN;
			if (builder.requiresValue()) {
				String msg = builder.getValuePrompt();
				while (!builder.validateValue(value)) {
					try {
						value = Double
								.parseDouble(JOptionPane.showInputDialog(msg));
					} catch (NumberFormatException e) {
						value = Double.NaN;
						msg = "Not a number, " + builder.getValuePrompt();
					}
				}
			}
			addElement(value);
			drawingLine = false;
		} else {
			placedX = hoverX;
			placedY = hoverY;
			drawingLine = true;
		}
	}

	private void addElement(double value) {
		Position start = new Position(placedX, placedY);
		Node neg = handleNodeCreation(start);
		Position end = new Position(hoverX, hoverY);
		Node pos = handleNodeCreation(end);
		Element e = builder.createElement(circuit, neg, pos, value);
		ElementDrawer ed = builder.createElementDrawer(e, value);
		elements.put(e, ed);
	}

	private Node handleNodeCreation(Position p) {
		if (!nodes.contains(p)) {
			nodes.add(circuit.createNode(), p);
		}
		return nodes.get(p);
	}

	public boolean hoverEnd(int x, int y) {
		x = round(x);
		y = round(y);
		if (x != hoverX || y != hoverY) {
			hoverX = round(x);
			hoverY = round(y);
			return true;
		}
		return false;

	}

	public void cancelLine() {
		drawingLine = false;
	}

	private static int round(int i) {
		return ((2 * i + 15) / 30) * 15;
	}
}
