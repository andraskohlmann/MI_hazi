package environment;

public class CarState {
	
//	public static void main(String[] args) {
//		CarState s = new CarState(5, 1);
//		System.out.println(s.getStateNum());
//	}
	
	private static final double minPosition = 0;
	private static final double maxPosition = 2 * Math.PI;
	private static final double winPosition = 1.75 * Math.PI;
	
	private static final double minVelocity = -1;
	private static final double maxVelocity = 1;
	//TODO
	
	private static final int positionResolution = 10;
	private static final int velocityResolution = 10;
	
	private double position;
	private double velocity;
	
	CarState(double p, double v) {
		position = p;
		velocity = v;
	}
	
	int getStateNum() {
		double normalPosition = (position - minPosition) / (maxPosition - minPosition);
		int discretePosition = (int) (Math.round(normalPosition * (positionResolution - 1)));
		
		double normalVelocity = (velocity - minVelocity) / (maxVelocity - minVelocity);
		int discreteVelocity = (int) (Math.round(normalVelocity * (velocityResolution - 1)));
		
//		System.out.println(discretePosition);
//		System.out.println(discreteVelocity);
		
		return discretePosition * (velocityResolution) + discreteVelocity;		
	}
	
	void setStateNum(int num) {
		
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof CarState) {
			return (getStateNum() == ((CarState) o).getStateNum());
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return getStateNum();
	}
	
}
