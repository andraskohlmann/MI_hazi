package environment;

public class Environment {

	private static final double winPosition = 1.75 * Math.PI;
	private static final double winReward = 1;
	private static final double constReward = -0.01;
	private static final CarState winstat = new CarState(winPosition, 0);

	private static final double gravity = 0.1;
	private static final double deltaTime = 1;
	private static final double accelerationRatio = 0.02;
	
	public Environment() {
		System.out.println(winstat.getStateNum() / CarState.velocityResolution);
	}

	public Result getResult(CarState state_t, Activity a) {
		CarState state_tpp = calculateNextState(state_t, a);
		double reward = constReward;
		if (state_tpp.getStateNum() / CarState.velocityResolution == winstat.getStateNum() / CarState.velocityResolution) {
			reward = winReward;
		}
		//double reward = Math.abs(state_tpp.getPosition()
				//- winstat.getPosition()) < 0.2 ? winReward : constReward;
		return new Result(state_tpp, reward);
	}

	private CarState calculateNextState(CarState state_t, Activity a) {
		double m = -Math.sin(state_t.getPosition());
		double acc = Math.sin(Math.atan(m)) * gravity + (a.ordinal() - 1)
				* accelerationRatio;
		double vel = state_t.getVelocity() + acc * deltaTime / 2;
		double pos = state_t.getPosition() + vel * deltaTime;
		return saturate(new CarState(pos, vel));
	}

	private CarState saturate(CarState stat) {
		double vel = stat.getVelocity();
		vel = vel < CarState.minVelocity ? CarState.minVelocity
				: (vel > CarState.maxVelocity ? CarState.maxVelocity : vel);

		double pos = stat.getPosition();
		if (pos < CarState.minPosition) {
			pos = CarState.minPosition;
			vel *= -1;
		} else if (pos > CarState.maxPosition) {
			pos = CarState.maxPosition;
			vel *= -1;
		}
		// pos = pos < CarState.minPosition ? CarState.minPosition : (pos >
		// CarState.maxPosition ? CarState.maxPosition : pos);

		return new CarState(pos, vel);
	}
}
