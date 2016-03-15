package slogo.model;

import java.util.Map;

public class SLCommand_MINUS extends MathOperation{
	private static final int LIMIT = 1;
	
	@Override
	public double getValue(IMemory m, Map<String, Double> scope) {
		return -1*operateOnValues(ArithmeticOperator.ADDITION,scope,m);
	}
	
	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
	
}
