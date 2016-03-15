package slogo.model;

abstract class PenChange extends TurtleStateCreator{
	private boolean myStatus;
	private static final int LIMIT = 0;
	
	@Override
	protected double setState(TurtleState myState) {
		myState.setPenStatus(myStatus);
		return myStatus ? 1 : 0;
	}
	protected void setMyStatus(boolean status){
		myStatus = status;
	}
	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
