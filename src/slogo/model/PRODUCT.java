package slogo.model;

public class PRODUCT extends RETURNVALUE {

	@Override
	public void createFunction(IParser p, IMemory m) {
		myValue = p.parseExpression()*p.parseExpression();
	}

}
