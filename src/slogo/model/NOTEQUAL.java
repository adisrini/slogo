package slogo.model;

public class NOTEQUAL extends RETURNVALUE{

	@Override
	public void createFunction(IParser p, IMemory m) {
		double expr1 = p.parseExpression();
		double expr2 = p.parseExpression();
		myValue = expr1 != expr2 ? 1 : 0;
	}
}
