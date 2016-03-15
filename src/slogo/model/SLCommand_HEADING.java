package slogo.model;

import java.util.Map;

public class SLCommand_HEADING extends ReturnValue{
	private static final int LIMIT = 0;
	/**
     * Returns the average heading of all active turtles
     * @return double
     */
	@Override
	public double getValue(IMemory m, Map<String, Double> scope) {
		return m.getActiveMemory().getCurrentlyActiveTurtle().getHeading();
	}

	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
