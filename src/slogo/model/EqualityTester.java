package slogo.model;

import java.util.Map;

abstract public class EqualityTester extends ReturnValue{
	private static final int LIMIT = 2;
	
	public double determineEquality(IMemory m, Map<String, Double> scope) {
		boolean equal = true;
		int count = 0; double prevVal = 0; double nextVal = 0;
		
		while(count<numberOfExpressions()){
			if(count==0){prevVal = getExpression(count,m, scope);}
			nextVal = getExpression(count+1,m, scope);
			if(prevVal!=nextVal){equal = false;}
			prevVal = nextVal;
			count+=2;
		}
		return equal ? 1 : 0;
	}
	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
