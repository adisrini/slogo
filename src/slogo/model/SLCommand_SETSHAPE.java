package slogo.model;

public class SLCommand_SETSHAPE extends TurtleStateCreator {
	private static final int LIMIT = 1;

	@Override
	protected double setState(TurtleState myState) {
		myState.setShapeIndex((int) getVal(0));
		return myState.getShapeIndex();
	}

	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
