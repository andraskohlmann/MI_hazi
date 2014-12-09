package top;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class LoadingPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private double percent;
	
	public LoadingPanel() {
		percent = 0;
	}
	
	public void setPercent(double p) {
		percent = p;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(new Color(255, 255, 255));
		g.fillRect(100, 0, 1060, 75);
		
		g.setColor(new Color(0, 255, 0));
		g.fillRect(100, 0, (int) (1060 * percent), 75);
	}
	
}
