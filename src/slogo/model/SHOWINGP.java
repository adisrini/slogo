package slogo.model;

public class SHOWINGP extends RETURNVALUE{
	@Override
	public void createFunction(IParser p, IMemory m) {
		myValue = m.readCurrExecution().getCurrState().getVisibility() ? 1 : 0;
	}
}
