package slogo.model;

public class LEFT extends TURN{
	@Override
	public void createFunction(IParser p, IMemory m) {
		super.myRotationNum = -p.parseExpression();
	}
}
