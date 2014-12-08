package top;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import environment.Activity;
import environment.CarState;
import environment.Environment;

public class Visualizer extends JFrame implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private Timer timer;
	private GraphicPanel gfx;
	private int stepCount;
	
	private CarState start;
	
	Visualizer(Environment e, Activity[] pi, CarState s) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(100, 100);
		setSize(1280, 720);
		setTitle("Experiment Visualizer");
		setResizable(false);
		
		addMouseListener(this);
		
		gfx = new GraphicPanel(e, pi, s);
		start = s;
		add(gfx);
		
		stepCount = 0;
	}
	
	public void start() {
		setVisible(true);
		
		timer = new Timer(50, this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		stepCount++;
		if (gfx.step() == 1 || stepCount > 1000) {
			timer.stop();
			timer = null;
		}
		
		gfx.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		stepCount = 0;
		gfx.setResult(start);
		
		if (timer == null) {
			timer = new Timer(50, this);
			timer.start();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

}
