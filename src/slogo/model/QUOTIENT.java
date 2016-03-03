package slogo.model;

public class QUOTIENT extends RETURNVALUE {

	@Override
	public void createFunction(IParser p, IMemory m) {
		myValue = p.parseExpression()/p.parseExpression();
	}

}
