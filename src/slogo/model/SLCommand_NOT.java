package slogo.model;

import java.util.Map;

public class SLCommand_NOT extends ReturnValue{
	private static final int LIMIT = 1;
	
	@Override
	public double getValue(IMemory m, Map<String, Double> scope) {
		return getExpression(0,m,scope)==0 ? 1 : 0;
	}
	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
