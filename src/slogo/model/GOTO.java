package slogo.model;

public class GOTO extends COORDINATESETTER{
	@Override
	public void createFunction(IParser p, IMemory m) {
		myX = p.parseExpression();
		myY = p.parseExpression();
	}
}
