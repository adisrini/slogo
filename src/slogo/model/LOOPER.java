package slogo.model;

import java.util.Map;

public abstract class Looper extends InnerScopedFunction{
	private static final int LIMIT = 0;

	@Override
	public double executeFunction(IMemory m, Map<String,Double> scope) {
		double finalnum = 0;
		for(double n = getStart(m); n <= getEnd(m); n+=getIncrement(m)){
			myScope.put(getVarName(m), n);
			finalnum = super.executeFunctionList(m, myScope);
		}
		return finalnum;
	}
	protected abstract double getStart(IMemory m);
	protected abstract double getEnd(IMemory m);
	protected abstract String getVarName(IMemory m);
	protected abstract double getIncrement(IMemory m);
	
	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
