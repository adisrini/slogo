package slogo.model;

public class RIGHT extends TURN{
	@Override
	public void createFunction(IParser p, IMemory m) {
		// TODO Auto-generated method stub
		super.myRotationNum = p.parseExpression();
	}
}
