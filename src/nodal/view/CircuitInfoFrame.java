package nodal.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class CircuitInfoFrame {
	private JTextArea textArea;
	
	
	private JFrame frame;
	
	public CircuitInfoFrame() {
		frame = new JFrame("Circuit Information");
		frame.setLayout(new BorderLayout());
		textArea = new JTextArea(20, 30);
		textArea.setEditable(false);
		frame.add(textArea, BorderLayout.CENTER);
		frame.pack();
		
	}
	
	public void updateContent(String content) {
		textArea.setText(content);
	}
	
	public void showHide() {
		frame.setVisible(!frame.isVisible());
	}
}
