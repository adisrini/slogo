package slogo.model;

import java.util.Map;

public class SLCommand_OR extends ReturnValue{
	private static final int LIMIT = 2;
	
	@Override
	public double getValue(IMemory m, Map<String,Double> scope) {	
		int test = 0;
		
		for(int i = 0; i<numberOfExpressions(); i++){
			if(getExpression(i, m,scope)!=0){test = 1;}
		}
		
		return test;
	}

	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
