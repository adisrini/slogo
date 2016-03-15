package slogo.model;

public class SLCommand_STAMP extends TurtleStateCreator{
	private static final int LIMIT = 0;

	@Override
	protected double setState(TurtleState myState) {
		myState.setStamp(true);
		return 1;
	}

	@Override
	protected int argsNeeded() {
		return LIMIT;
	}

}
