package slogo.model;

public abstract class MOVE extends STATECREATOR{
	protected double myVal;
	@Override
	public abstract void createFunction(IParser p, IMemory m);

	@Override
	public double executeFunction(IMemory m) {
		super.executeFunction(m);
		//now calculate new x and y positions 
		double radians = Math.toRadians(myState.getHeading() - 90);
		double deltax = myVal*Math.cos(radians);
		double deltay = myVal*Math.sin(radians);
		myState.setX(myState.getX()+deltax);
		myState.setY(myState.getY()+deltay);
		
		//add state to memory
		m.readCurrExecution().addState(myState);
		return myVal;
	}
}
