package slogo.model;

public class ATAN extends RETURNVALUE {
	@Override
	public void createFunction(IParser p, IMemory m) {
		double radians = Math.toRadians(p.parseExpression());
		myValue = Math.toDegrees(Math.atan(radians));
	}
}
