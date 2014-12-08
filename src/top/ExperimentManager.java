package top;

import intelligence.ADPIntelligence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import environment.Activity;
import environment.CarState;
import environment.Environment;
import environment.Result;

public class ExperimentManager {

	public static int maxIteration = 1000;

	public static void main(String[] args) throws FileNotFoundException {
		Environment environment = new Environment();
		Activity[] pi = null;

		for (int j = 0; j < 50; j++) {
			System.out.println("nyal�sa ama pin�nak hessteg " + j);
			ADPIntelligence ai = new ADPIntelligence(
					pi == null ? ADPIntelligence.startPi() : pi, new CarState(
							3 * Math.PI / 4, 0), Activity.neutral);
			Result result = environment.getResult(new CarState(3 * Math.PI / 4,
					0), Activity.neutral);

			int i = 0;
			for (i = 0; i < maxIteration; i++) {
				Activity act = ai.move(result);
				result = environment.getResult(result.getState(), act);
				
				if (result.getReward() == 1) {
					break;
				}
			}

			pi = ai.nextPi();
			
			System.out.println("kijutottam a gecibe " + i + " l�p�s alatt, szal ak�r be is kaphatod a faszom.");
		}
		
		PrintWriter pw = new PrintWriter(new File("fasz2.txt"));
		for (int i = 0; i < CarState.maxState(); i++) {
			pw.println(pi[i]);
		}
		pw.close();
	}

}
