package slogo.model;

abstract class PENCHANGE extends STATECREATOR{
	protected boolean myStatus;
	@Override
	public double executeFunction(IMemory m) {
		super.executeFunction(m);
		myState.setPenStatus(myStatus);
		m.readCurrExecution().addState(myState);
		return myStatus ? 1 : 0;
	}

}
