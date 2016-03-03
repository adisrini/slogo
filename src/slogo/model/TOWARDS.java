package slogo.model;

public class TOWARDS implements IFunction{
	double myXHeading;
	double myYHeading;
	@Override
	public void createFunction(IParser p, IMemory m) {
		myXHeading = p.parseExpression();
		myYHeading = p.parseExpression();
	}

	@Override
	public double executeFunction(IMemory m) {
		IState myState = new State(m.readCurrExecution().getCurrState());
		double deltaX = myXHeading-myState.getX();
		double deltaY = myYHeading-myState.getY();
		double tan = Math.abs(Math.toDegrees(Math.atan(deltaY/deltaX)));
		double heading = 0;
		if(deltaX>=0&&deltaY<=0){heading = tan;}
		if(deltaX<=0&&deltaY<=0){heading = -tan;}
		if(deltaX<=0&&deltaY>=0){heading = 180+tan;}
		if(deltaX>=0&&deltaY>=0){heading = 180-tan;}
		double difference = myState.getHeading()-heading;
		myState.setHeading(heading);
		m.readCurrExecution().addState(myState);
		return difference;
	}

}
