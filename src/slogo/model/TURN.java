package slogo.model;

public abstract class TURN extends STATECREATOR{
	protected double myRotationNum;
	@Override
	public abstract void createFunction(IParser p, IMemory m);
	@Override
	public double executeFunction(IMemory m) {
		super.executeFunction(m);
		double newHeading = myRotationNum + myState.getHeading();
		int div = (int)(newHeading/360);
		newHeading = newHeading -360*(div);
		myState.setHeading(newHeading);
		m.readCurrExecution().addState(myState);
		return myRotationNum;
	}
}
