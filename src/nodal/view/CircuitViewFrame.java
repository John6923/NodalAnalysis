package nodal.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import nodal.view.builder.CurrentSourceBuilder;
import nodal.view.builder.ResistorBuilder;
import nodal.view.builder.VoltageSourceBuilder;
import nodal.view.builder.WireBuilder;

public class CircuitViewFrame {
	public static void main(String args[]) {
		CircuitView circuitView = new CircuitView();

		JFrame frame = new JFrame("Circuit View ('/' for help)");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new CircuitArea(circuitView), BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);

	}

	private static class CircuitArea extends JPanel {
		private static final long serialVersionUID = 3025582048785217779L;

		private CircuitView cv;
		private CircuitInfoFrame cif;

		public CircuitArea(CircuitView cv) {
			this.cv = cv;
			cif = new CircuitInfoFrame();
			setBackground(Color.WHITE);
			setMinimumSize(new Dimension(480, 320));
			setPreferredSize(new Dimension(480, 320));
			setFocusable(true);
			requestFocusInWindow();
			this.addKeyListener(new KeyListener() {
				@Override
				public void keyReleased(KeyEvent arg0) {
				}

				@Override
				public void keyTyped(KeyEvent arg0) {
				}

				@Override
				public void keyPressed(KeyEvent ke) {
					switch (ke.getKeyCode()) {
					case KeyEvent.VK_R:
						cv.setCurrentBuilder(ResistorBuilder.INSTANCE);
						break;
					case KeyEvent.VK_I:
						cv.setCurrentBuilder(CurrentSourceBuilder.INSTANCE);
						break;
					case KeyEvent.VK_V:
						cv.setCurrentBuilder(VoltageSourceBuilder.INSTANCE);
						break;
					case KeyEvent.VK_W:
						cv.setCurrentBuilder(WireBuilder.INSTANCE);
						break;
					case KeyEvent.VK_ESCAPE:
						cv.cancelLine();
						break;
					case KeyEvent.VK_SLASH:
						System.out.println("R: Draw resistor");
						System.out.println("V: Draw voltage source");
						System.out.println("I: Draw current source");
						System.out.println("W: Draw wire");
						System.out.println("ESC: Quit drawing current line");
						System.out.println("/: Print this message");
						break;
					case KeyEvent.VK_P: 
						cif.showHide();
						break;
					default:
						return;
					}
					repaint();
				}
			});
			this.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					cv.addEnd(e.getX(), e.getY());
					repaint();
				}
			});
			this.addMouseMotionListener(new MouseMotionListener() {
				@Override
				public void mouseDragged(MouseEvent e) {
				}

				@Override
				public void mouseMoved(MouseEvent e) {
					if (hasFocus()) {
						if (cv.hoverEnd(e.getX(), e.getY()))
							repaint();
					}
				}
			});
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			int width = this.getWidth();
			int height = this.getHeight();
			cv.paint(g, width, height, hasFocus());
			cif.updateContent(cv.getCircuitString());
		}

	}
}
