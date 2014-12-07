package intelligence;

import java.util.Random;

import environment.Activity;
import environment.CarState;
import environment.Result;

public class ADPIntelligence {

	private static int kmax = 10;

	private Activity[] pi;

	private static double[][][] T = new double[CarState.maxState()][3][CarState
			.maxState()];
	private double[] R = new double[CarState.maxState()];
	private static int[][] Nsa = new int[CarState.maxState()][3];
	private static int[][][] Nsas = new int[CarState.maxState()][3][CarState
			.maxState()];

	private double[] U;

	private CarState previousState;
	private Activity previousActivity;

	public ADPIntelligence(Activity[] p, CarState startState,
			Activity startActivity) {
		pi = p;

		U = new double[CarState.maxState()];

		previousActivity = startActivity;
		previousState = startState;
	}

	public Activity move(Result result) {
		int stateNum = result.getState().getStateNum();

		if (R[stateNum] == 0) {
			U[stateNum] = result.getReward();
			R[stateNum] = result.getReward();
		}

		Nsa[previousState.getStateNum()][previousActivity.ordinal()]++;
		Nsas[previousState.getStateNum()][previousActivity.ordinal()][stateNum]++;

		for (int i = 0; i < CarState.maxState(); i++) {
			T[previousState.getStateNum()][previousActivity.ordinal()][i] = (double) Nsas[previousState
					.getStateNum()][previousActivity.ordinal()][i]
					/ Nsa[previousState.getStateNum()][previousActivity
							.ordinal()];
		}

		for (int k = 0; k < kmax; k++) {
			double[] Unext = new double[CarState.maxState()];
			for (int i = 0; i < CarState.maxState(); i++) {
				double tmp = 0;
				for (int j = 0; j < CarState.maxState(); j++) {
					tmp += T[i][pi[i].ordinal()][j] * U[j];
				}
				Unext[i] = R[i] + tmp;
			}
			U = Unext;
		}

		previousState = result.getState();
		previousActivity = pi[stateNum];

		return pi[stateNum];
	}
	
	public static Activity int2Activity(int a) {
		return a == 0 ? Activity.reverse : (a == 1 ? Activity.neutral
				: Activity.forward);
	}

	public Activity[] nextPi() {
		Activity[] nextPi = new Activity[CarState.maxState()];

		for (int i = 0; i < CarState.maxState(); i++) {
			double[] tmp = new double[3];
			for (int j = 0; j < 3; j++) {
				tmp[j] = 0;
				for (int k = 0; k < CarState.maxState(); k++) {
					tmp[j] += T[i][j][k] * U[k];
				}
			}

			double max = tmp[0];
			int a = 0;
			for (int j = 1; j < 3; j++) {
				if (max < tmp[j]) {
					max = tmp[j];
					a = j;
				}
			}

			nextPi[i] = int2Activity(a);
		}

		return nextPi;
	}
	
	public static Activity[] startPi() {
		Activity[] pi = new Activity[CarState.maxState()];
		
		Random r = new Random();
		for (int i = 0; i < CarState.maxState(); i++) {
			pi[i] = int2Activity(r.nextInt(3));
		}
		
		return pi;
	}

}
