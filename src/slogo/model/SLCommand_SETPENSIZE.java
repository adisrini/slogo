package slogo.model;

public class SLCommand_SETPENSIZE extends TurtleStateCreator {
	private static final int LIMIT = 1;

	@Override
	protected double setState(TurtleState myState) {
		myState.setPenSize(super.getVal(0));
		return myState.getPenSize();
	}

	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
