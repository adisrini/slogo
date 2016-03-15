package slogo.model;

import java.util.Map;

public class SLCommand_TAN extends MathOperation{
	private static final int LIMIT = 1;
	
	@Override
	public double getValue(IMemory m, Map<String, Double> scope) {
		double myValue = operateOnValues(ArithmeticOperator.ADDITION,scope,m);
		return Math.toDegrees(Math.tan(Math.toRadians(myValue)));
	}
	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
