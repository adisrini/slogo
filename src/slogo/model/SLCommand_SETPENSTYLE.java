package slogo.model;

public class SLCommand_SETPENSTYLE extends TurtleStateCreator {
	private static final int LIMIT = 1;

	@Override
	protected double setState(TurtleState myState) {
		myState.setPenStyle((int) super.getVal(0));
		return myState.getPenStyle();
	}

	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
