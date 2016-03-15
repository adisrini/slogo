package slogo.model;

import java.util.Map;

class SLCommand_RANDOM extends ReturnValue{
	private static final int LIMIT = 1;	
	
	@Override
	public double getValue(IMemory m, Map<String, Double> scope) {
		return (Math.random() * getExpression(0,m,scope));
	}
	
	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
