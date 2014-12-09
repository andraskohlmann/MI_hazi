package top;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
	
	private JPanel panel;
	private CardLayout layout;
	
	Visualizer(Environment e, Activity[] pi, CarState s) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(100, 100);
		setSize(1260, 720);
		setTitle("Experiment Visualizer");
		setResizable(false);
		
		addMouseListener(this);
		
		panel = new JPanel();
		
		layout = new CardLayout();
		panel.setLayout(layout);
		
		gfx = new GraphicPanel(e, pi, s);
		start = s;
		
		JButton jb = new JButton("Gomb");
		jb.setActionCommand("bla");
		jb.addActionListener(this);
		
		panel.add(jb, "button");
		panel.add(gfx, "show");
		
		add(panel);
		
		layout.show(panel, "button");
		
		stepCount = 0;
	}
	
	public void start() {
		setVisible(true);
		
		timer = new Timer(50, this);
		//timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand() != null) {
			layout.show(panel, "show");
			timer.start();
		}
		else {
			stepCount++;
			if (gfx.step() == 1 || stepCount > 1000) {
				timer.stop();
				timer = null;
				gfx.setStopped();
			}
			
			gfx.repaint();
		}
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
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	
}
