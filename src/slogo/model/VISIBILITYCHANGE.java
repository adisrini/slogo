package slogo.model;

abstract class VisibilityChange extends TurtleStateCreator {
	protected boolean myStatus;
	
	@Override
	protected double setState(TurtleState myState) {
		myState.setVisibility(myStatus);
		return myStatus ? 1 : 0;
	}
	@Override
	protected int argsNeeded() {
		return 0;
	}
}
