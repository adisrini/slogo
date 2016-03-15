package slogo.model;

public abstract class SLCommand_MOVE extends TurtleStateCreator{
	private static final int LIMIT = 1;

	@Override
	protected double setState(TurtleState myState) {
		double radians = Math.toRadians(myState.getHeading() - 90);
		double deltax = setVal(getVal(0))*Math.cos(radians);
		double deltay = setVal(getVal(0))*Math.sin(radians);
		myState.setX(myState.getX()+deltax);
		myState.setY(myState.getY()+deltay);
		return Math.sqrt(Math.pow(deltax, 2) + Math.pow(deltay, 2));
	}
	protected abstract double setVal(double val);

	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
