package slogo.model;

class RANDOM extends RETURNVALUE{
	@Override
	public void createFunction(IParser p, IMemory m) {
		myValue = (Math.random() * p.parseExpression());
	}
}
