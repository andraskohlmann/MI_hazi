package top;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import environment.Activity;
import environment.Environment;

public class Visualizer extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Timer timer;
	private GraphicPanel gfx;
	
	Visualizer(Environment e, Activity[] pi) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(100, 100);
		setSize(1280, 720);
		setTitle("Visualizer");
		
		gfx = new GraphicPanel(e, pi);
		add(gfx);
	}
	
	public void start() {
		setVisible(true);
		
		timer = new Timer(50, this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (gfx.step() == 1) {
			timer.stop();
		}
		
		gfx.repaint();
	}
	
	

}
