package slogo.model;

public class SHOWTURTLE extends VISIBILITYCHANGE {

	@Override
	public void createFunction(IParser p, IMemory m) {
		super.myStatus = true;
	}

}
