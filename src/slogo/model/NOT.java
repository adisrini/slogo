package slogo.model;

public class NOT extends RETURNVALUE{

	@Override
	public void createFunction(IParser p, IMemory m) {
		double test = p.parseExpression();
		myValue = test==0 ? 1 : 0;
	}
}
