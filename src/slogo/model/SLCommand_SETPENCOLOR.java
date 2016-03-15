package slogo.model;

public class SLCommand_SETPENCOLOR extends TurtleStateCreator {
	private static final int LIMIT = 1;

	@Override
	protected double setState(TurtleState myState) {
		myState.setPenColorIndex((int) getVal(0));
		return myState.getPenColorIndex();
	}

	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
