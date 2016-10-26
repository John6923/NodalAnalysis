package nodal.view;

import java.awt.Graphics;

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
		return value <= 0;
	}

	@Override
	public String getValuePrompt() {
		return "Enter resistance: ";
	}

	@Override
	public void draw(Graphics g, int sx, int sy, int ex, int ey) {
		DrawingUtils.drawResistor(g, sx, sy, ex, ey);
		
	}

	@Override
	public Element createElement(int sx, int sy, int ex, int ey, double value) {
		return new ResistorElement(sx, sy, ex, ey, value);
	}
}
