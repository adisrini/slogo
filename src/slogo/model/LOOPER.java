package slogo.model;

import java.util.Map;

/**
 * The Class Looper.
 * 
 * Extended by looping commands like Dotimes, For, Repeat, etc.
 */
public abstract class Looper extends InnerScopedFunction{

	private static final int LIMIT = 0;

	/**
	 * Executes the function  by looping through all commands 
	 * for the specified start, end, and increment.
	 */
	@Override
	public double executeFunction(IMemory m, Map<String,Double> scope) {
		double finalnum = 0;
		for(double n = getStart(m); n <= getEnd(m); n+=getIncrement(m)){
			myScope.put(getVarName(m), n);
			finalnum = super.executeFunctionList(m, myScope);
		}
		return finalnum;
	}
	
	/**
	 * Gets the start.
	 *
	 * @param m the m
	 * @return the start
	 */
	protected abstract double getStart(IMemory m);
	
	/**
	 * Gets the end.
	 *
	 * @param m the m
	 * @return the end
	 */
	protected abstract double getEnd(IMemory m);
	
	/**
	 * Gets the var name.
	 *
	 * @param m the m
	 * @return the var name
	 */
	protected abstract String getVarName(IMemory m);
	
	/**
	 * Gets the increment.
	 *
	 * @param m the m
	 * @return the increment
	 */
	protected abstract double getIncrement(IMemory m);
	
	/**
	 * gets the minimum number of arguments needed
	 */
	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
