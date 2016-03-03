package slogo.model;

public class BACKWARD extends MOVE{
	@Override
	public void createFunction(IParser p, IMemory m) {
		myVal = -p.parseExpression();
	}
}
