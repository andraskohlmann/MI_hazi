package top;

import intelligence.ADPIntelligence;

import java.io.IOException;

import environment.Activity;
import environment.CarState;
import environment.Environment;
import environment.Result;

public class ExperimentManager {

	public static int maxIteration = 1000;

	private static CarState startState = new CarState(3 * Math.PI / 4, 0);
	private static Environment environment = new Environment();

	public static void main(String[] args) throws IOException {
		MathLabGraph.connect();

		Visualizer v = new Visualizer(environment, startState);
		v.setVisible(true);
	}

	public static Activity[] experiment() {
		Activity[] pi = null;
		Activity[] lastGoodPi = null;

		CarState startState = new CarState(3 * Math.PI / 4, 0);

		int exitnum = 0;

		for (int j = 0;; j++) {
			ADPIntelligence ai = new ADPIntelligence(
					pi == null ? ADPIntelligence.startPi() : pi, startState,
					Activity.neutral);
			Result result = environment.getResult(startState, Activity.neutral);

			int i = 0;
			for (i = 0; i < maxIteration; i++) {
				Activity act = ai.move(result);
				result = environment.getResult(result.getState(), act);

				if (result.getReward() == 1) {
					break;
				}
			}

			// Ul[j] = ai.getU();

			if (i < maxIteration || j > 1000) {
				if (i < maxIteration) {
					lastGoodPi = pi;
					exitnum++;
				}
				if (exitnum >= 5 || j > 1000) {
					MathLabGraph.drawUs(ai.getUs(), i);
					break;
				}
			}

			pi = ai.nextPi();
		}

		return lastGoodPi;
	}

}
