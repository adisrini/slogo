package slogo.model;

import java.util.Map;

public class SLCommand_REMAINDER extends ReturnValue {
	private static final int LIMIT = 2;
	
	@Override
	public double getValue(IMemory m, Map<String, Double> scope) {
		return getExpression(0,m,scope) % getExpression(1,m,scope);
	}
	
	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
