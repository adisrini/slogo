package slogo.model;

abstract class STATECREATOR implements IFunction{
	protected IState myState;
	@Override
	public double executeFunction(IMemory m) {
		if(m.readCurrExecution().hasCurrState()){
			myState = new State(m.readCurrExecution().getCurrState());}
		else{myState = new State(m.readBuilderState());}
		return 0;
	}

}
