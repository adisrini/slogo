package slogo.model;

public class HEADING extends RETURNVALUE{

	@Override
	public void createFunction(IParser p, IMemory m) {
		myValue = m.readCurrExecution().getCurrState().getHeading();
	}
}
