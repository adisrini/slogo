package slogo.model;

public class MINUS extends RETURNVALUE{

	@Override
	public void createFunction(IParser p, IMemory m) {
		myValue = -1*Math.abs(p.parseExpression());
	}
}
