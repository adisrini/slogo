package slogo.model;

import java.util.Map;

public class SLCommand_POW extends ReturnValue {
	private static final int LIMIT = 2;
	
	@Override
	public double getValue(IMemory m, Map<String, Double> scope) {
		return Math.pow(getExpression(0,m,scope),getExpression(1,m,scope));
	}
	@Override
	protected int argsNeeded() {
		// TODO Auto-generated method stub
		return LIMIT;
	}
}
