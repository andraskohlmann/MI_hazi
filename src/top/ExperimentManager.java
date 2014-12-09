package top;

import intelligence.ADPIntelligence;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

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
			System.out.print("nyalása ama pinának hessteg " + j);
			ADPIntelligence ai = new ADPIntelligence(
					pi == null ? ADPIntelligence.startPi() : pi, startState, Activity.neutral);
			Result result = environment.getResult(startState, Activity.neutral);

			int i = 0;
			for (i = 0; i < maxIteration; i++) {
				Activity act = ai.move(result);
				result = environment.getResult(result.getState(), act);
				
				if (result.getReward() == 1) {
					//System.out.println("épp jutok ki ezen a szenthelyen: " + result.getState().getPosition());
					break;
				}
			}
			
			//Ul[j] = ai.getU();
			
			if (i < maxIteration || j > 1000) {
				if (i < maxIteration) {
					System.out.println(" kijutottam a gecibe " + i + " lépés alatt, szal akár be is kaphatod a faszom.");
					lastGoodPi = pi;
					exitnum++;
				}
				if (exitnum >= 5 || j > 1000) {
					MathLabGraph.drawUs(ai.getUs(), i);
					break;
				}
			}
			else {
				System.out.println();
			}

			pi = ai.nextPi();
		}
		
		return lastGoodPi;
	}

}
