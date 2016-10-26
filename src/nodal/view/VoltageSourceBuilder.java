package nodal.view;

import java.awt.Graphics;

public class VoltageSourceBuilder implements ElementBuilder {
	
	public static ElementBuilder INSTANCE = new VoltageSourceBuilder();
	
	private VoltageSourceBuilder() {
		
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
		return "Enter voltage: ";
	}

	@Override
	public Element createElement(int sx, int sy, int ex, int ey, double value) {
		return new VoltageSourceElement(sx, sy, ex, ey, value);
	}

	@Override
	public void draw(Graphics g, int sx, int sy, int ex, int ey) {
		DrawingUtils.drawVoltageSource(g, sx, sy, ex, ey);
		
	}

}
