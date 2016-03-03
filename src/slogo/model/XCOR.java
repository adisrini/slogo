package slogo.model;

public class XCOR extends RETURNVALUE{
	@Override
	public void createFunction(IParser p, IMemory m) {
		myValue = m.readCurrExecution().getCurrState().getX();
	}
}
