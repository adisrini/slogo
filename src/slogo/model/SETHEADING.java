package slogo.model;

public class SETHEADING extends STATECREATOR{
	double myHeading;
	@Override
	public void createFunction(IParser p, IMemory m) {
		myHeading = p.parseExpression();
	}
	@Override
	public double executeFunction(IMemory m) {
		super.executeFunction(m);
		myState.setHeading(myHeading);
		m.readCurrExecution().addState(myState);
		return myHeading;
	}
}
