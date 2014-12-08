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

	public static void main(String[] args) throws IOException {
		MathLabGraph.connect();
		
		Environment environment = new Environment();
		Activity[] pi = null;
		
		CarState startState = new CarState(3 * Math.PI / 4, 0);
		
		int exitnum = 0;
		
		double[][] Ul = new double[103][CarState.maxState()];

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
			
			Ul[j] = ai.getU();
			
			if (i < maxIteration || j > 100) {
				System.out.println(" kijutottam a gecibe " + i + " lépés alatt, szal akár be is kaphatod a faszom.");
				exitnum++;
				if (exitnum >= 5 || j > 100) {
					MathLabGraph.drawUs(Ul, j);
					break;
				}
			}
			else {
				System.out.println();
			}

			pi = ai.nextPi();
		}
		
		MathLabGraph.disconnect();
		
		PrintWriter pw = new PrintWriter(new File("fasz2.txt"));
		for (int i = 0; i < CarState.maxState(); i++) {
			pw.print(pi[i] + "\t");
			if((i+1) % CarState.velocityResolution == 0) {
				pw.println();
			}
		}
		pw.close();
		
		Visualizer v = new Visualizer(environment, pi, startState);
		v.start();
		
		Result result = environment.getResult(startState, Activity.neutral);
		
		CarState[] stats = new CarState[1001];
		//double[] poss = new double[1001];
		//double[] vels = new double[1001];
		stats[0] = result.getState();
		//poss[0] = result.getState().getPosition();
		//vels[0] = result.getState().getVelocity();
		int i;
		for (i = 0; i < 1000; i++) {
			result = environment.getResult(result.getState(), pi[result.getState().getStateNum()]);
			stats[i + 1] = result.getState();
			//poss[i + 1] = result.getState().getPosition();
			//vels[i + 1] = result.getState().getVelocity();
			
			if (result.getReward() == 1) {
				break;
			}
		}
		
		if (i < 1000) {
			System.out.println("kabbe " + i);
//			for (int j = 0; j <= i + 1; j++) {
//				System.out.println("itt is vótam: " + stats[j].getPosition() + " és ennyivel mentem: " + stats[j].getVelocity() + "és ezt míveltem: " + pi[stats[j].getStateNum()]);
//			}
		}
		else {
			System.out.println("retardált maradtam.");
		}
	}

}
