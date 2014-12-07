package top;

import intelligence.ADPIntelligence;
import environment.Activity;
import environment.CarState;
import environment.Environment;
import environment.Result;

public class ExperimentManager {

	public static int maxIteration = 1000;

	public static void main(String[] args) {
		Environment environment = new Environment();
		Activity[] pi = null;

		for (int j = 0; j < 10; j++) {
			ADPIntelligence ai = new ADPIntelligence(
					pi == null ? ADPIntelligence.startPi() : pi, new CarState(
							3 * Math.PI / 4, 0), Activity.neutral);
			Result result = environment.getResult(new CarState(3 * Math.PI / 4,
					0), Activity.neutral);

			for (int i = 0; i < maxIteration; i++) {
				Activity act = ai.move(result);
				result = environment.getResult(result.getState(), act);
			}

			pi = ai.nextPi();
		}
	}

}
