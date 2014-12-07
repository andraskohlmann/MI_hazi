package intelligence;

import environment.Activity;
import environment.CarState;
import environment.Result;

public class ADPIntelligence {
	
	private double[][][] T;
	private double[] R;
	private double[] U;
	private double[][] Nsa;
	private double[][][] Nsas;
	
	private CarState prevoiusState;
	private Activity prevoiusActivity;
	
	public ADPIntelligence() {
		T = new double[CarState.maxState()][3][CarState.maxState()];
		R = new double[CarState.maxState()];
	}
	
	Activity move(Result result) {
		return Activity.neutral;
	}

}
