package slogo.model;

import java.util.Map;

public class SLCommand_YCOR extends ReturnValue{
	private static final int LIMIT = 0;

	/**
     * Returns the average ycor of all active turtles
     * @return double
     */
	@Override
	public double getValue(IMemory m,  Map<String,Double> scope) {
		return m.getActiveMemory().getCurrentlyActiveTurtle().getY();
	}
	
	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
