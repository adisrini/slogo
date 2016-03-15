package slogo.model;

import java.util.Map;

public class SLCommand_PENDOWNP extends ReturnValue{
	private static final int LIMIT = 0;
	/**
     * Returns number of active turtles with their 
     * pens down.
     * @return double
     */	
	@Override
	public double getValue(IMemory m,  Map<String,Double> scope) {
		return m.getActiveMemory().getCurrentlyActiveTurtle().getPenStatus() ? 1 : 0;
	}
	
	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
