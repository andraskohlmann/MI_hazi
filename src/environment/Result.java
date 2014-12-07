package environment;

public class Result {
	
	private CarState state;
	private double award;
	
	public Result(CarState s, double a) {
		state = s;
		award = a;
	}
	
	public CarState getState() {
		return state;
	}
	
	public double getAward() {
		return award;
	}
}
