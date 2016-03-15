package slogo.model;

import java.util.Map;
/**
 * Unlimited Params: Takes the arctan of the sum of all radian values
 * @return double
 */
public class SLCommand_ATAN extends MathOperation {
	private static final int LIMIT = 1;
	
	@Override
	public double getValue(IMemory m, Map<String, Double> scope) {
		return Math.toDegrees(Math.atan(operateOnValues(ArithmeticOperator.ADDITION,scope,m)));
	}
	
	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
