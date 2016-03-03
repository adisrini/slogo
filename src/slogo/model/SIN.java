package slogo.model;

class SIN extends RETURNVALUE{

	@Override
	public void createFunction(IParser p, IMemory m) {
		myValue =  Math.toDegrees(Math.sin(Math.toRadians(p.parseExpression())));
	}

}
