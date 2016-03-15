package slogo.model;

import java.util.Map;

class SLCommand_SIN extends MathOperation{
	private static final int LIMIT = 1;
	
	@Override
	public double getValue(IMemory m, Map<String, Double> scope) {
		double myValue = operateOnValues(ArithmeticOperator.ADDITION,scope,m);
		return Math.toDegrees(Math.sin(Math.toRadians(myValue)));
	}
	@Override
    protected int argsNeeded () {
		return LIMIT;
	}
}
