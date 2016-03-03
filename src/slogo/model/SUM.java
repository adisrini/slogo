package slogo.model;

public class SUM extends RETURNVALUE{
	@Override
	public void createFunction(IParser p, IMemory m) {
		double myVal1 = p.parseExpression();
		double myVal2 = p.parseExpression();
		myValue = myVal1+myVal2;
	}
}
