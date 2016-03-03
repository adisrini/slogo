package slogo.model;

public class AND extends RETURNVALUE{

	@Override
	public void createFunction(IParser p, IMemory m) {
		double test1 = p.parseExpression();
		double test2 = p.parseExpression();
		myValue = ((test1!=0)&&(test2!=0)) ? 1 : 0;
	}
}
