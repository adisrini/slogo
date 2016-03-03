package slogo.model;

abstract class COORDINATESETTER extends STATECREATOR{
	protected double myX;
	protected double myY;
	@Override
	public double executeFunction(IMemory m) {
		super.executeFunction(m);
		double prevX = myState.getX(); double prevY = myState.getY();
		myState.setX(myX); myState.setY(myY);
		m.readCurrExecution().addState(myState);
		return Math.sqrt(Math.pow(myX-prevX,2)+Math.pow(myX-prevY,2));
	}
}
