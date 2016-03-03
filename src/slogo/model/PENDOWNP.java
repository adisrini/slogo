package slogo.model;

public class PENDOWNP extends RETURNVALUE{
	@Override
	public void createFunction(IParser p, IMemory m) {
		myValue = m.readCurrExecution().getCurrState().getPenStatus() ? 1 : 0;
	}
}
