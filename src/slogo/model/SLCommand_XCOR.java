package slogo.model;

import java.util.Map;

public class SLCommand_XCOR extends ReturnValue{
	private static final int LIMIT = 0;

	/**
     * Returns the average xcor of all active turtles
     * @return double
     */
	@Override
	public double getValue(IMemory m,  Map<String,Double> scope) {
		return m.getActiveMemory().getCurrentlyActiveTurtle().getX();
	}

	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
