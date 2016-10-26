package nodal.view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class CircuitView {
	private List<Element> elements = new ArrayList<>();

	private boolean drawingLine = false;
	private int placedX, placedY, hoverX, hoverY;
	private ElementBuilder builder = ResistorBuilder.INSTANCE;

	public void paint(Graphics g, int screenWidth, int screenHeight) {
		for (Element ce : elements) {
			ce.draw(g);
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
			double value = -1;
			String msg = "Enter value: ";
			while (value < 0) {
				try {
					value = Double
							.parseDouble(JOptionPane.showInputDialog(msg));
					msg = "Enter a value greater than zero: ";
				} catch (NumberFormatException e) {
					msg = "Not a number, enter value: ";
				}
			}
			elements.add(builder.createElement(placedX, placedY, hoverX, hoverY,
					value));
			drawingLine = false;
		} else {
			placedX = hoverX;
			placedY = hoverY;
			drawingLine = true;
		}
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
