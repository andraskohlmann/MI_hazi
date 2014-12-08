package environment;

public class CarState {

	// public static void main(String[] args) {
	// CarState s = new CarState(6.2, 1);
	// System.out.println(s.getStateNum());
	// CarState n = new CarState(s.getStateNum());
	// System.out.println(n.position);
	// System.out.println(n.velocity);
	// }

	public static final double minPosition = 0;
	public static final double maxPosition = 2 * Math.PI;

	public static final double minVelocity = -0.3;
	public static final double maxVelocity = 0.3;
	// TODO

	public static final int positionResolution = 11;
	public static final int velocityResolution = 11;

	public static int maxState() {
		return positionResolution * velocityResolution;
	}

	private double position;
	private double velocity;

	public CarState(int s) {
		setStateNum(s);
	}

	public CarState(double p, double v) {
		position = p;
		velocity = v;
	}

	public double getPosition() {
		return position;
	}

	public double getVelocity() {
		return velocity;
	}

	public int getStateNum() {
		double normalPosition = (position - minPosition)
				/ (maxPosition - minPosition);
		int discretePosition = (int) (Math.round(normalPosition
				* (positionResolution - 1)));

		double normalVelocity = (velocity - minVelocity)
				/ (maxVelocity - minVelocity);
		int discreteVelocity = (int) (Math.round(normalVelocity
				* (velocityResolution - 1)));

		// System.out.println(discretePosition);
		// System.out.println(discreteVelocity);

		return discretePosition * (velocityResolution) + discreteVelocity;
	}

	public void setStateNum(int num) {
		int discreteVelocity = num % velocityResolution;
		double normalVelocity = (double) discreteVelocity
				/ (velocityResolution - 1);
		velocity = minVelocity + normalVelocity * (maxVelocity - minVelocity);

		int discretePosition = num / velocityResolution;
		double normalPosition = (double) discretePosition
				/ (positionResolution - 1);
		position = minPosition + normalPosition * (maxPosition - minPosition);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof CarState) {
			return (getStateNum() == ((CarState) o).getStateNum());
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return getStateNum();
	}

}
