package slogo.model;

abstract class SLCommand_GOTO extends TurtleStateCreator{
	private static final int LIMIT = 2;
	
	@Override
	protected double setState(TurtleState myState) {
		double prevX = myState.getX(); double prevY = myState.getY();
		myState.setX(getVal(0)); myState.setY(getVal(1));
		return Math.sqrt(Math.pow(getVal(0)-prevX,2)+Math.pow(getVal(1)-prevY,2));
	}
	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
