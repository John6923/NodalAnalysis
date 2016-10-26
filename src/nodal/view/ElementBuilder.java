package nodal.view;

import java.awt.Graphics;

public interface ElementBuilder {
	boolean requiresValue();

	boolean validateValue(double value);

	String getValuePrompt();

	Element createElement(int sx, int sy, int ex, int ey, double value);

	void draw(Graphics g, int sx, int sy, int ex, int ey);

}
