package nodal.view.util;

import java.awt.Color;
import java.awt.Graphics;

public class DrawingUtils {
	
	public static void drawResistor(Graphics g, int sx, int sy, int ex,
			int ey) {
		int mx = (sx + ex) / 2, my = (sy + ey) / 2;
		int fx = sx, fy = sy, lx = ex, ly = ey;
		double mag = Math.sqrt((ex - sx) * (ex - sx) + (ey - sy) * (ey - sy));
		if (mag >= 30) {
			fx = mx - (int) Math.round(((ex - sx) / mag) * 15);
			fy = my - (int) Math.round(((ey - sy) / mag) * 15);
			lx = mx + (int) Math.round(((ex - sx) / mag) * 15);
			ly = my + (int) Math.round(((ey - sy) / mag) * 15);
		}
		mag = Math.sqrt((lx - fx) * (lx - fx) + (ly - fy) * (ly - fy));
		int nx = (int) Math.round(((ly - fy) / mag) * -10);
		int ny = (int) Math.round(((lx - fx) / mag) * 10);
		// System.out.printf("(%d, %d)\n", nx, ny);
		g.setColor(Color.BLACK);
		g.drawLine(sx, sy, fx, fy);
		g.drawLine(lx, ly, ex, ey);

		int p1x, p1y, p2x, p2y;
		p1x = fx;
		p1y = fy;
		p2x = fx + (2 * (lx - fx) * 1 + 10) / (2 * 10) + nx;
		p2y = fy + (2 * (ly - fy) * 1 + 10) / (2 * 10) + ny;
		g.drawLine(p1x, p1y, p2x, p2y);
		for (int i = 3; i < 10; i += 2) {
			p1x = p2x;
			p1y = p2y;
			p2x = fx + (2 * (lx - fx) * i + 10) / (2 * 10) - nx;
			p2y = fy + (2 * (ly - fy) * i + 10) / (2 * 10) - ny;
			// System.out.printf("(%d, %d)---(%d, %d)\n", p1x, p1y, p2x, p2y);
			g.drawLine(p1x, p1y, p2x, p2y);
			nx = -nx;
			ny = -ny;
		}
		p1x = p2x;
		p1y = p2y;
		p2x = lx;
		p2y = ly;
		g.drawLine(p1x, p1y, p2x, p2y);
	}

	public static void drawCurrentSource(Graphics g, int sx, int sy, int ex,
			int ey) {
		g.setColor(Color.BLACK);
		g.drawLine(sx, sy, ex, ey);

		int midX = (ex + sx) / 2, midY = (ey + sy) / 2;
		g.setColor(Color.WHITE);
		g.fillOval(midX - 15, midY - 15, 30, 30);
		g.setColor(Color.BLACK);
		g.drawOval(midX - 15, midY - 15, 30, 30);
		int px = midX, py = midY - 11, mx = midX, my = midY + 11;
		int dx = 0, dy = -4;
		if (!((sx == ex) && (sy == ey))) {
			double mag = Math
					.sqrt((ex - sx) * (ex - sx) + (ey - sy) * (ey - sy));
			px = midX + (int) Math.round(((ex - sx) / mag) * 11);
			py = midY + (int) Math.round(((ey - sy) / mag) * 11);
			mx = midX + (int) Math.round(((ex - sx) / mag) * -11);
			my = midY + (int) Math.round(((ey - sy) / mag) * -11);
			dx = (int) Math.round(((ex - sx) / mag) * 4);
			dy = (int) Math.round(((ey - sy) / mag) * 4);

		}
		g.drawLine(mx, my, px, py);
		g.drawLine(px, py, px - dx - dy, py - dy + dx);
		g.drawLine(px, py, px - dx + dy, py - dy - dx);
	}

	public static void drawVoltageSource(Graphics g, int sx, int sy, int ex,
			int ey) {
		g.setColor(Color.BLACK);
		g.drawLine(sx, sy, ex, ey);

		int midX = (ex + sx) / 2, midY = (ey + sy) / 2;
		g.setColor(Color.WHITE);
		g.fillOval(midX - 15, midY - 15, 30, 30);
		g.setColor(Color.BLACK);
		g.drawOval(midX - 15, midY - 15, 30, 30);
		int px = midX, py = midY - 9, mx = midX, my = midY + 9;
		int dxX = 2, dxY = 0, dyX = 0, dyY = 2;
		if (!((sx == ex) && (sy == ey))) {
			double mag = Math
					.sqrt((ex - sx) * (ex - sx) + (ey - sy) * (ey - sy));
			px = midX + (int) Math.round(((ex - sx) / mag) * 9);
			py = midY + (int) Math.round(((ey - sy) / mag) * 9);
			mx = midX + (int) Math.round(((ex - sx) / mag) * -9);
			my = midY + (int) Math.round(((ey - sy) / mag) * -9);
			dxX = (int) Math.round(((ey - sy) / mag) * 3);
			dxY = (int) Math.round(((ex - sx) / mag) * 3);
			dyX = (int) Math.round(((ex - sx) / mag) * 3);
			dyY = (int) Math.round(((ey - sy) / mag) * 3);

		}
		g.drawLine(px - dxX, py + dxY, px + dxX, py - dxY);
		g.drawLine(px - dyX, py - dyY, px + dyX, py + dyY);
		g.drawLine(mx - dxX, my + dxY, mx + dxX, my - dxY);
	}
	
	public static void drawLabel(Graphics g, int sx, int sy, int ex, int ey,
			String value) {
		int mx = (ex + sx) / 2;
		int my = (ey + sy) / 2;
		if (sx == ex) { // Vertical
			mx += 15;
		} else if (sy == ey) { // Horizontal
			my -= 15;
			mx += 5;
		} else if (sx < ex) { // Right
			if (sy > ey) { // Up
				mx += 20;
				my += 15;
			} else { // Down
				mx += 15;
				my -= 5;
			}
		} else { // Left
			if (sy > ey) { // Up
				mx += 20;
			} else { // Down
				mx += 15;
				my += 10;
			}
		}
		g.drawString(value, mx, my);
	}
}
