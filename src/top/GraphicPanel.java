package top;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import environment.Activity;
import environment.CarState;
import environment.Environment;
import environment.Result;

public class GraphicPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final int resolution = 200;

	private Environment environment;
	// private CarState state;
	private Result result;
	private int stepCount;
	private Activity[] pi;
	
	private boolean isStopped;
	
	private int[] xs;
	private int[] ys;

	public GraphicPanel(Environment e, Activity[] p, CarState s) {
		environment = e;
		pi = p;

		stepCount = 0;
		isStopped = false;

		result = environment.getResult(s, Activity.neutral);

		// state = result.getState();
		
		xs = new int[resolution];
		ys = new int[resolution];
		for (int i = 0; i < resolution; i++) {
			double x = 2 * Math.PI / resolution * i;
			xs[i] = xCoord(x);
			ys[i] = yCoord(x);
		}
	}

	private static int xCoord(double x) {
		return (int) (x * 200) + 2;
	}

	private static int yCoord(double x) {
		return (int) (-Math.cos(x + Math.PI / 4) * 200) + 400;
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.clearRect(0, 0, 1260, 720);
		
		Graphics2D g2d = (Graphics2D) g;
		
		BasicStroke bs = new BasicStroke(2);
		g2d.setStroke(bs);
		g2d.drawPolyline(xs, ys, resolution);

		double carx = result.getState().getPosition();
		g2d.fillRect(xCoord(carx) - 10, yCoord(carx) - 10, 20, 10);
		g2d.setColor(new Color(255, 0, 0));
		g2d.drawLine(xCoord(carx), yCoord(carx) - 10, xCoord(carx) + (pi[result.getState().getStateNum()].ordinal() - 1) * 20, yCoord(carx) - 10);
		
		g2d.setColor(new Color(0, 0, 0));
		g2d.drawString(Integer.toString(stepCount), 50, 50);
		
		if (isStopped) {
			g2d.drawString("Click to restart animation", 560, 300);
		}
	}
	
	public double step() {
		result = environment.getResult(result.getState(), pi[result.getState().getStateNum()]);
		stepCount++;
		
		return result.getReward();
	}
	
	public void setResult(CarState s) {
		result =  environment.getResult(s, Activity.neutral);
		stepCount = 0;
		isStopped = false;
	}
	
	public void setStopped() {
		isStopped = true;
	}

}
