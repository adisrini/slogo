package slogo.model;

public abstract class Turn extends TurtleStateCreator{
	private static final int LIMIT = 1;

	public double setState(TurtleState myState){
		double newHeading = setVal(getVal(0)) + myState.getHeading();
		int div = (int)(newHeading/360);
		newHeading = newHeading -360*(div);
		myState.setHeading(newHeading);
		return getVal(0);
	}
	
	protected abstract double setVal(double val);
	
	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
	
}
