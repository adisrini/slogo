package slogo.model;

public class POW extends RETURNVALUE {
	@Override
	public void createFunction(IParser p, IMemory m) {
		double base = p.parseExpression();
		double exponent = p.parseExpression();
		myValue = Math.pow(base,exponent);
	}
}
