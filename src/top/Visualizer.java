package top;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import environment.Activity;
import environment.CarState;
import environment.Environment;

public class Visualizer extends JFrame implements ActionListener,
		MouseListener, WindowListener {

	private static final long serialVersionUID = 1L;
	private Timer timer;
	private GraphicPanel gfx;
	private int stepCount;

	private CarState start;

	private JPanel panel;
	private CardLayout layout;

	private boolean started;

	Visualizer(Environment e, CarState s) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(100, 100);
		setSize(1260, 720);
		setTitle("Experiment Visualizer");
		setResizable(false);

		addMouseListener(this);
		addWindowListener(this);

		started = false;

		panel = new JPanel();

		layout = new CardLayout();
		panel.setLayout(layout);

		gfx = new GraphicPanel(e, s);
		start = s;

		panel.add(new FrontPanel(this), "front");
		panel.add(gfx, "show");

		add(panel);

		layout.show(panel, "front");

		stepCount = 0;
	}

	public void start(Activity[] p) {
		timer = new Timer(50, this);

		gfx.setPi(p);

		layout.show(panel, "show");
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand() == null) {
			stepCount++;
			if (gfx.step() == 1 || stepCount > 1000) {
				timer.stop();
				timer = null;
				gfx.setStopped();
			}

			repaint();
		} else if (arg0.getActionCommand().equals("start")) {
			if (started == false) {
				started = true;
				new WorkerThread(this).start();
			}

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

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		MathLabGraph.disconnect();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}

}
