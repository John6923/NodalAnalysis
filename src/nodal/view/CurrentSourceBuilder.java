package nodal.view;

import java.awt.Graphics;

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
		return true;
	}

	@Override
	public String getValuePrompt() {
		return "Enter current: ";
	}

	@Override
	public void draw(Graphics g, int sx, int sy, int ex, int ey) {
		DrawingUtils.drawCurrentSource(g, sx, sy, ex, ey);

	}

	@Override
	public Element createElement(int sx, int sy, int ex, int ey, double value) {
		return new CurrentSourceElement(sx, sy, ex, ey, value);
	}

}
