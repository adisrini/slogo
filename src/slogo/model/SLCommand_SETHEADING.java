package slogo.model;

public class SLCommand_SETHEADING extends TurtleStateCreator{
	private static final int LIMIT = 1;
	
	@Override
	protected double setState(TurtleState myState) {
		myState.setHeading(getVal(0));
		return myState.getHeading();
	}
	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
