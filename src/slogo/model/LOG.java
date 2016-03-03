package slogo.model;

public class LOG extends RETURNVALUE {
	@Override
	public void createFunction(IParser p, IMemory m) {
		myValue = Math.log(p.parseExpression());
	}
}
