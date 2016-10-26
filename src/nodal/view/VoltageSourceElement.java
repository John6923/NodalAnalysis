package nodal.view;

import java.awt.Graphics;

public class VoltageSourceElement implements Element {
	private double value;
	private int sx, sy, ex, ey;

	public VoltageSourceElement(int sx, int sy, int ex, int ey, double value) {
		this.sx = sx;
		this.sy = sy;
		this.ex = ex;
		this.ey = ey;
		this.value = value;
	}

	@Override
	public void draw(Graphics g) {
		DrawingUtils.drawVoltageSource(g, sx, sy, ex, ey);
		String s = String.format("%.2fV", value);
		DrawingUtils.drawLabel(g, sx, sy, ex, ey, s);
	}

}
