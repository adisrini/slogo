package slogo.model;

public class SLCommand_TOWARDS extends TurtleStateCreator{
	public static final int LIMIT = 2;

	@Override
	protected double setState(TurtleState myState) {
		double myHeading = 0;
		double deltaX = getVal(0)-myState.getX();
		double deltaY = getVal(1)-myState.getY();
		double tan = Math.abs(Math.toDegrees(Math.atan(deltaY/deltaX)));
		if(deltaX>=0&&deltaY<=0){myHeading = tan;}
		if(deltaX<=0&&deltaY<=0){myHeading = -tan;}
		if(deltaX<=0&&deltaY>=0){myHeading = 180+tan;}
		if(deltaX>=0&&deltaY>=0){myHeading = 180-tan;}
		double difference = myState.getHeading()-myHeading;
		myState.setHeading(myHeading);
		return difference;

	}
	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
