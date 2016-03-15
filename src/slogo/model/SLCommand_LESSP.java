package slogo.model;

import java.util.Map;

public class SLCommand_LESSP extends ReturnValue{
	private static final int LIMIT = 2;

	@Override
	public double getValue(IMemory m, Map<String,Double> scope) {
		int test = 1;
		double first = getExpression(0, m,scope);
		for(int i = 1; i<numberOfExpressions(); i++){
			if(first>=getExpression(i, m,scope)){test = 0;}
		}
		return test;
	}

	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
