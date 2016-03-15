package slogo.model;

import java.util.Map;

public class SLCommand_PRODUCT extends MathOperation {
	private static final int LIMIT = 2;
	
	@Override
	public double getValue(IMemory m, Map<String, Double> scope) {
		return operateOnValues(ArithmeticOperator.MULTIPLICATION,scope,m);
	}
	
	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
