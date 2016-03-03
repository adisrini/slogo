package slogo.model;

public class PI extends RETURNVALUE{

	@Override
	public void createFunction(IParser p, IMemory m) {
		myValue = Math.PI;
	}

}
