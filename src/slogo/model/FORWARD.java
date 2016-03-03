package slogo.model;

public class FORWARD extends MOVE{
	@Override
	public void createFunction(IParser p, IMemory m) {
		myVal = p.parseExpression();
	}
}
