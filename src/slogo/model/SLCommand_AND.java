package slogo.model;

import java.util.Map;

/**
 * The Class SLCommand_AND.
 */
public class SLCommand_AND extends ReturnValue{
	
	private static final int LIMIT = 2;
	
	/**
	 * Returns all values ANDed
	 */
	@Override
	public double getValue(IMemory m, Map<String,Double> scope) {	
		int test = 1;
		for(int i = 0; i<numberOfExpressions(); i++){
			if(getExpression(i, m,scope)==0){test = 0;}
		}
		return test;
	}
	
	/**
	 * returns minimum args needed
	 */
	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
