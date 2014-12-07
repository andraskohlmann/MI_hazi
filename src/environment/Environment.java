package environment;

public class Environment {

	private static final double winPosition = 1.75 * Math.PI;
	private static final double winReward = 1;
	private static final double constReward = -0.01;

	private static final double gravity = 9.81;
	private static final double deltaTime = 0.01;
	private static final double accelerationRatio = 5;

	public Result getResult(CarState state_t, Activity a) {
		CarState state_tpp = calculateNextState(state_t, a);
		double reward = state_tpp.getPosition() == winPosition ? winReward
				: constReward;
		return new Result(state_tpp, reward);
	}

	private CarState calculateNextState(CarState state_t, Activity a) {
		double m = -Math.sin(state_t.getPosition());
		double acc = Math.sin(Math.atan(m)) * gravity + (a.ordinal()-1) * accelerationRatio;
		double vel = state_t.getVelocity() + acc * deltaTime / 2;
		return new CarState(state_t.getPosition() + vel * deltaTime, vel);
	}
}
