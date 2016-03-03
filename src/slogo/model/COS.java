package slogo.model;

public class COS extends RETURNVALUE {
	@Override
	public void createFunction(IParser p, IMemory m) {
		myValue =  Math.toDegrees(Math.cos(Math.toRadians(p.parseExpression())));
	}
}
