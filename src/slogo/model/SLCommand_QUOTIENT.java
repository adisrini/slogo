package slogo.model;

import java.util.Map;

public class SLCommand_QUOTIENT extends MathOperation {
	private static final int LIMIT = 2;
	
	@Override
	public double getValue(IMemory m, Map<String, Double> scope) {
		return operateOnValues(ArithmeticOperator.DIVISION,scope,m);
	}
	
	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
