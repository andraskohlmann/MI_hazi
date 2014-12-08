package top;

import java.awt.Color;
import java.awt.Graphics;

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
	//private int stepCount;
	private Activity[] pi;
	
	private int[] xs;
	private int[] ys;

	public GraphicPanel(Environment e, Activity[] p) {
		environment = e;
		pi = p;

//		stepCount = 0;

		result = environment.getResult(new CarState(3 * Math.PI / 4, 0),
				Activity.neutral);

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
		return (int) (x * 200);
	}

	private static int yCoord(double x) {
		return (int) (-Math.cos(x + Math.PI / 4) * 200) + 400;
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.clearRect(0, 0, 1280, 720);
		
		g.drawPolyline(xs, ys, resolution);

		double carx = result.getState().getPosition();
		g.fillRect(xCoord(carx), yCoord(carx), 10, 5);
		g.setColor(new Color(255, 0, 0));
		g.drawLine(xCoord(carx), yCoord(carx), xCoord(carx) + (pi[result.getState().getStateNum()].ordinal() - 1) * 10, yCoord(carx));
	}
	
	public double step() {
		result = environment.getResult(result.getState(), pi[result.getState().getStateNum()]);
		
		return result.getReward();
	}

}
