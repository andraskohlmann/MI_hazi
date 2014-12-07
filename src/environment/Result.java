package environment;

public class Result {
	
	private CarState state;
	private double reward;
	
	public Result(CarState s, double a) {
		state = s;
		reward = a;
	}
	
	public CarState getState() {
		return state;
	}
	
	public double getAward() {
		return reward;
	}
}
