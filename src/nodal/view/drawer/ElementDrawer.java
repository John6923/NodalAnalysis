package nodal.view.drawer;

import java.awt.Graphics;

import nodal.view.util.NodePositionStore;

public interface ElementDrawer {
	
	public void draw(Graphics g, NodePositionStore nps);
	
}
