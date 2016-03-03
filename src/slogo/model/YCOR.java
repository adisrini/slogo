package slogo.model;

public class YCOR extends RETURNVALUE{
	@Override
	public void createFunction(IParser p, IMemory m) {
		myValue = m.readCurrExecution().getCurrState().getY();
	}
}
