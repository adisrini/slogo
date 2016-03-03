package slogo.model;

abstract class VISIBILITYCHANGE extends STATECREATOR {
	protected boolean myStatus;
	@Override
	public double executeFunction(IMemory m) {
		super.executeFunction(m);
		myState.setVisibility(myStatus);
		m.readCurrExecution().addState(myState);
		return myStatus ? 1 : 0;
	}
}
