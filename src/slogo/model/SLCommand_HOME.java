package slogo.model;

public class SLCommand_HOME extends SLCommand_GOTO{
	private static final int LIMIT = 0;

	protected double setState(TurtleState myState) {
		double prevX = myState.getX(); double prevY = myState.getY();
		myState.setX(0); myState.setY(0);
		return Math.sqrt(Math.pow(0-prevX,2)+Math.pow(0-prevY,2));
	}
	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
