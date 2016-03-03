package slogo.model;

public class TAN extends RETURNVALUE {
	@Override
	public void createFunction(IParser p, IMemory m) {
		double radians = Math.toRadians(p.parseExpression());
		myValue = Math.cos(radians)==0 ? 0 : Math.toDegrees(Math.tan(radians));
	}
}
